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
        view.showNextImage();
    }
}
