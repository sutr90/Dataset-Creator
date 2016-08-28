package sample.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

class SceneMouseHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        if (!event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            return;
        }

        if (event.getClickCount() == 1 && event.getButton().equals(MouseButton.PRIMARY) && event.isStillSincePress()) {
            View.getController().createBox(event.getSceneX(), event.getSceneY());
            event.consume();
        }
    }
}
