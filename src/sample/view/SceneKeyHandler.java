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
        switch (event.getCode()) {
            case SPACE:
            case ENTER:
                view.showNextImage();
                break;
            case KP_RIGHT:
            case RIGHT:
                view.showNextImage();
                break;
            case KP_LEFT:
            case LEFT:
                view.showPreviousImage();
                break;
        }
    }
}
