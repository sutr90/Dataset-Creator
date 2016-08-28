package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.view.ImageView;

public class Controller {
    private final StringProperty titleProperty;

    public Controller(String datasetPath) {
        titleProperty = new SimpleStringProperty("");
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public ImageView next() {
        //TODO: add method body
        return null;
    }
}
