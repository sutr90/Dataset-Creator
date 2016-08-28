package sample.controller;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.ScrollEvent;
import sample.model.Box;
import sample.model.Dataset;
import sample.model.Image;
import sample.view.BoxView;
import sample.view.ImageView;

import java.util.stream.Collectors;

public class Controller {
    private final StringProperty titleProperty;
    private final Dataset dataset;

    private final ObservableList<BoxView> observableList = FXCollections.observableArrayList();
    private ListProperty<BoxView> boxesProperty = new SimpleListProperty<>(observableList);

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
        double zoomFactor = event.isControlDown() ? 1.05 : 1.2;
        double deltaY = event.getDeltaY();
        if (deltaY < 0) {
            zoomFactor = 2.0 - zoomFactor;
        }
        double finalZoomFactor = zoomFactor;
        observableList.forEach(bv -> bv.setZoom(finalZoomFactor));
    }
}
