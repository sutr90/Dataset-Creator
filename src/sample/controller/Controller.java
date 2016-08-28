package sample.controller;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.ScrollEvent;
import sample.model.Box;
import sample.model.Dataset;
import sample.model.Image;
import sample.view.BoxView;
import sample.view.ImageView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Controller {
    private final StringProperty titleProperty;
    private final Dataset dataset;

    private final ObservableList<BoxView> observableList = FXCollections.observableArrayList();
    private ListProperty<BoxView> boxesProperty = new SimpleListProperty<>(observableList);
    private boolean dragging;

    public Controller(String datasetPath) {
        titleProperty = new SimpleStringProperty("");
        dataset = new Dataset(datasetPath);
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public ImageView next() {
        Image next = dataset.next();
        updateTitle();
        updateBoxes();
        return ImageView.get(next);
    }

    private void updateTitle() {
        titleProperty.set(dataset.getTitle());
    }

    public boolean hasNext() {
        return dataset.hasNext();
    }

    public boolean hasPrevious() {
        return dataset.hasPrevious();
    }

    public ImageView previous() {
        Image next = dataset.previous();
        updateTitle();
        updateBoxes();
        return ImageView.get(next);
    }

    public void createBox(double x, double y) {
        dataset.createBox(x, y);
        updateBoxes();
    }

    private void updateBoxes() {
        observableList.clear();
        observableList.addAll(dataset.getBoxes().stream().map(BoxView::new).collect(Collectors.toList()));
    }

    public void removeBox(Box box) {
        dataset.removeBox(box);
        updateBoxes();
    }

    public ListProperty<BoxView> getBoxesProperty() {
        return boxesProperty;
    }

    public void doZoom(ScrollEvent event) {
        observableList.forEach(bv -> bv.setZoom(event.getDeltaY(), event.isControlDown()));
    }

    public void saveDataset() {
        String datasetName = dataset.getDatasetName();
        String filename = datasetName.replaceAll("\\W+", "_");
        Path path = Paths.get(dataset.getPath(), filename);
        try (PrintWriter out = new PrintWriter(path.toString() + ".xml")) {
            out.println(dataset.toXmlString());
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Dataset saved to " + path.toString() + ".xml",
                    ButtonType.OK);
            a.setHeaderText("");
            a.showAndWait();
        } catch (FileNotFoundException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Error saving dataset to " + path.toString() + ".xml\n" + e
                    .getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    public int getNumberOfImages() {
        return dataset.getSize();
    }

    public void setDragging(Boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isDragging() {
        return dragging;
    }
}
