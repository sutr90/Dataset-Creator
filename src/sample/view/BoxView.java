package sample.view;

import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import sample.model.Box;

public class BoxView extends Group {
    private Box box;

    public BoxView(Box box) {
        super();

        this.box = box;
        HBox hbox = new HBox();
        hbox.setMinSize(box.getWidth(), box.getHeight());
        getChildren().add(hbox);
        hbox.setBlendMode(BlendMode.DIFFERENCE);
        hbox.setStyle("-fx-background-color: white;");
        hbox.setTranslateX(box.getX());
        hbox.setTranslateY(box.getY());
        hbox.scaleXProperty().bind(box.getZoom());
        hbox.scaleYProperty().bind(box.getZoom());
        makeDraggable(hbox);

        setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && event.getButton().equals(MouseButton.SECONDARY)) {
                View.getController().removeBox(box);
            }
        });

        setOnScroll(event -> {
            setZoom(event.getDeltaY(), event.isControlDown());
            event.consume();
        });
    }

    private void makeDraggable(HBox hbox) {
        final DragContext dragContext = new DragContext();
        addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                dragContext.mouseAnchorX = mouseEvent.getSceneX();
                dragContext.mouseAnchorY = mouseEvent.getSceneY();
                dragContext.initialTranslateX = hbox.getTranslateX();
                dragContext.initialTranslateY = hbox.getTranslateY();
            }
        });

        addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                hbox.setTranslateX(dragContext.initialTranslateX + mouseEvent.getSceneX() - dragContext.mouseAnchorX);
                hbox.setTranslateY(dragContext.initialTranslateY + mouseEvent.getSceneY() - dragContext.mouseAnchorY);
            }
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                box.setX(hbox.getTranslateX());
                box.setY(hbox.getTranslateY());
            }
        });
    }

    public void setZoom(double deltaY, boolean slow) {
        double zoomFactor = slow ? 1.05 : 1.2;
        if (deltaY < 0) {
            zoomFactor = 2.0 - zoomFactor;
        }

        double newZoom = Math.max(box.getZoom().get() * zoomFactor, 1.0);
        box.getZoom().set(newZoom);
    }

    private final class DragContext {
        double mouseAnchorX;
        double mouseAnchorY;
        double initialTranslateX;
        double initialTranslateY;
    }
}
