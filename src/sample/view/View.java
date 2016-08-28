package sample.view;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controller.Controller;

public class View extends Application {

    private static String[] params;
    private final Pane panelsPane = new Pane();
    private final ImageView imageView = new ImageView();
    private final Controller controller;

    public View() {
        final String datasetPath = params[0];
        this.controller = new Controller(datasetPath);
    }

    public static void main(final String[] args) {
        params = args;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final StackPane sceneLayout = new StackPane();
        sceneLayout.getChildren().addAll(imageView, panelsPane);
        final Scene scene = new Scene(sceneLayout, 800, 800);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new SceneKeyHandler(this));
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new SceneMouseHandler(this));

        primaryStage.titleProperty().bind(controller.getTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.show();

        showNextImage();
    }

    /**
     * @param image show this image
     */
    private void showImage(sample.view.ImageView image) {
        imageView.setImage(new Image(image.getUriString()));
    }

    void showNextImage() {
        if (controller.hasNext()) {
            showImage(controller.next());
        }
    }

    void showPreviousImage() {
        if (controller.hasPrevious()) {
            showImage(controller.previous());
        }
    }

    Controller getController() {
        return controller;
    }

    void updateBoxes() {
        panelsPane.getChildren().clear();
        panelsPane.getChildren().addAll(controller.getBoxes());
    }
}
