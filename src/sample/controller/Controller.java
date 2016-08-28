package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.model.Dataset;
import sample.model.Image;
import sample.view.ImageView;

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
}
