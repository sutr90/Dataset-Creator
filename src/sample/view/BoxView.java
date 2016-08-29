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

        hbox.minWidthProperty().bind(box.getWidthProperty());
        hbox.minHeightProperty().bind(box.getHeightProperty());

        getChildren().add(hbox);
        hbox.setBlendMode(BlendMode.DIFFERENCE);
        hbox.setStyle("-fx-background-color: white;");
        hbox.translateXProperty().bind(box.getXProperty().add(View.getController().getImageXOffsetProperty()));
        hbox.translateYProperty().bind(box.getYProperty().add(View.getController().getImageYOffsetProperty()));
        makeDraggable();

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

    private void makeDraggable() {
        final DragContext dragContext = new DragContext();
        addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                dragContext.mouseAnchorX = mouseEvent.getSceneX();
                dragContext.mouseAnchorY = mouseEvent.getSceneY();
                dragContext.initialX = box.getXProperty().get();
                dragContext.initialY = box.getYProperty().get();
            }
        });

        addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                View.getController().setDragging(true);
                double changeX = mouseEvent.getSceneX() - dragContext.mouseAnchorX;
                double changeY = mouseEvent.getSceneY() - dragContext.mouseAnchorY;
                box.getXProperty().set(dragContext.initialX + changeX);
                box.getYProperty().set(dragContext.initialY + changeY);
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
        double initialX;
        double initialY;
    }
}
