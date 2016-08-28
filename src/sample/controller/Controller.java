package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.model.Dataset;
import sample.model.Image;
import sample.view.BoxView;
import sample.view.ImageView;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private final StringProperty titleProperty;
    private final Dataset dataset;

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
        return ImageView.get(next);
    }

    public void createBox(double x, double y) {
        dataset.createBox(x,y);
    }

    public List<BoxView> getBoxes() {
        return dataset.getBoxes().stream().map(BoxView::new).collect(Collectors.toList());
    }
}
