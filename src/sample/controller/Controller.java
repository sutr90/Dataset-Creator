package sample.controller;

import javafx.beans.value.ObservableValue;

public class Controller {
    private ObservableValue<? extends String> titleProperty;

    public ObservableValue<? extends String> getTitleProperty() {
        return titleProperty;
    }
}
