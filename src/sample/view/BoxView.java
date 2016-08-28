package sample.view;

import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

class BoxView extends HBox {
    private final int SIZE = 60;
    private final View view;

    BoxView(View view, double x, double y) {
        super();
        this.view = view;
        this.setMinSize(SIZE, SIZE);
        this.setBlendMode(BlendMode.DIFFERENCE);
        this.setStyle("-fx-background-color: white;");
        this.setTranslateX(x - SIZE / 2);
        this.setTranslateY(y - SIZE / 2);

        this.setOnMouseClicked(click -> {
            if(click.getClickCount() == 1 && click.getButton().equals(MouseButton.SECONDARY)){
                view.removeBox(this);
            }
        });
    }
}
