package sample.view;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.util.Optional;

public class View extends Application {

    private static String[] params;
    private final Pane panelsPane = new Pane();
    private final ImageView imageView = new ImageView();
    private static Controller controller;
    private String datasetName;

    public View() {
        final String datasetPath = params[0];
        View.controller = new Controller(datasetPath);

        controller.getBoxesProperty().addListener((ListChangeListener<Node>) c -> updateBoxes());
    }

    public static void main(final String[] args) {
        params = args;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.datasetName = getDatasetName();

        final StackPane sceneLayout = new StackPane(imageView);
        sceneLayout.getChildren().addAll(panelsPane);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(sceneLayout);

        final Scene scene = new Scene(scroll);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new SceneKeyHandler(this));
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new SceneMouseHandler());
        scene.addEventFilter(ScrollEvent.SCROLL, event -> {
            controller.doZoom(event);
            event.consume();
        });

        primaryStage.titleProperty().bind(controller.getTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.show();

        showNextImage();
    }

    @Override
    public void stop() throws Exception {
        controller.saveDataset(datasetName);
    }

    private String getDatasetName() {
        Dialog<String> dialog = new TextInputDialog("dataset");
        dialog.setHeaderText("Enter dataset name.");

        String entered = "dataset";
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            entered = result.get();
        }
        return entered;
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

    static Controller getController() {
        return controller;
    }

    private void updateBoxes() {
        panelsPane.getChildren().clear();
        panelsPane.getChildren().addAll(controller.getBoxesProperty());
    }
}
