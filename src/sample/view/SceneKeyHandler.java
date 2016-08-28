package sample.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

class SceneKeyHandler implements EventHandler<KeyEvent> {
    private View view;

    SceneKeyHandler(View view) {
        this.view = view;
    }

    @Override
    public void handle(KeyEvent event) {
        if (!event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            return;
        }
        switch (event.getCode()) {
            case SPACE:
            case ENTER:
            case KP_RIGHT:
            case RIGHT:
            case K:
            case D:
                view.showNextImage();
                break;
            case KP_LEFT:
            case LEFT:
            case J:
            case A:
                view.showPreviousImage();
                break;
        }
        event.consume();
    }
}
