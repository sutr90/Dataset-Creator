package sample.view;

import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import sample.model.Box;

public class BoxView extends HBox {
    public BoxView(Box box) {
        super();
        this.setMinSize(box.getWidth(), box.getHeight());
        this.setBlendMode(BlendMode.DIFFERENCE);
        this.setStyle("-fx-background-color: white;");
        this.setTranslateX(box.getX() - box.getWidth() / 2);
        this.setTranslateY(box.getY() - box.getHeight() / 2);
    }
}
