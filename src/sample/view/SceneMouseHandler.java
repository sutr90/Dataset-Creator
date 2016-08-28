package sample.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sample.controller.Controller;

class SceneMouseHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        if (!event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            return;
        }

        if (event.getClickCount() == 1 && event.getButton().equals(MouseButton.PRIMARY)) {
            Controller controller = View.getController();
            if (controller.isDragging()) {
                controller.setDragging(false);
                return;
            }
            controller.createBox(event.getX(), event.getY());
            event.consume();
        }
    }
}
