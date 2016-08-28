package sample.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.io.File;
import java.util.Optional;

public class View extends Application {

    private static String[] params;
    private final Pane panelsPane = new Pane();
    private final ImageView imageView = new ImageView();
    private static Controller controller;
    private String datasetName;

    public View() {

        if (params.length < 1) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select directory with images.");
            File file = directoryChooser.showDialog(null);

            if (file != null) {
                View.controller = new Controller(file.getPath());
            }
        } else {
            View.controller = new Controller(params[0]);
        }

    }

    public static void main(final String[] args) {
        params = args;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        if (controller.getNumberOfImages() == 0) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("There are no valid files in selected directory.");
            a.setContentText("Application will terminate.");
            a.showAndWait();
            Platform.exit();
            return;
        }

        controller.getBoxesProperty().addListener((ListChangeListener<Node>) c -> updateBoxes());
        this.datasetName = getDatasetName();

        final StackPane sceneLayout = new StackPane(imageView);
        sceneLayout.getChildren().addAll(panelsPane);
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setContent(sceneLayout);

        final Scene scene = new Scene(scroll);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new SceneKeyHandler(this));
        panelsPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new SceneMouseHandler());
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
        if (controller.getNumberOfImages() > 0) {
            controller.saveDataset(datasetName);
        }
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
