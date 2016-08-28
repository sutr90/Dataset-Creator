package sample.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controller.Controller;

public class View extends Application {

    private final Controller controller;

    public View() {
        getParameters();
        this.controller = new Controller();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Pane panelsPane = new Pane();
        final ImageView imageView = new ImageView();
        final StackPane sceneLayout = new StackPane();
        sceneLayout.getChildren().addAll(imageView, panelsPane);
        final Scene scene = new Scene(sceneLayout, 800, 800);

        primaryStage.titleProperty().bind(controller.getTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param image show this image
     * @param imageView on this image view component
     */
    private void showImage(sample.view.ImageView image, ImageView imageView) {
        imageView.setImage(new Image(image.getUriString()));
    }
}
