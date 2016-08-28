package sample.controller;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import sample.model.Box;
import sample.model.Dataset;
import sample.model.Image;
import sample.view.BoxView;
import sample.view.ImageView;

import java.util.stream.Collectors;

public class Controller {
    private final StringProperty titleProperty;
    private final Dataset dataset;

    private ObservableList<Node> observableList = FXCollections.observableArrayList();
    private ListProperty<Node> boxesProperty = new SimpleListProperty<>(observableList);

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
        observableList.addAll(dataset.getBoxes().stream().map(BoxView::new).map(this::makeDraggable).collect
                (Collectors.toList()));
    }

    private Node makeDraggable(BoxView node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);
        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            dragContext.mouseAnchorX = mouseEvent.getX();
            dragContext.mouseAnchorY = mouseEvent.getY();
            dragContext.initialTranslateX = node.getTranslateX();
            dragContext.initialTranslateY = node.getTranslateY();
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            node.setTranslateX(dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX);
            node.setTranslateY(dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY);
        });

        return wrapGroup;
    }

    private final class DragContext {
        double mouseAnchorX;
        double mouseAnchorY;
        double initialTranslateX;
        double initialTranslateY;
    }

    public void removeBox(Box box) {
        dataset.removeBox(box);
        updateBoxes();
    }

    public ListProperty<Node> getBoxesProperty() {
        return boxesProperty;
    }
}
