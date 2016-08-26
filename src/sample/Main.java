/*
 * Handling mouse events with event filters
 */
package sample;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Main extends Application {
    private List<File> images;
    private int imageIndex = 0;
    private DatasetXML dataset = new DatasetXML("5318008");
    private double decorationWidth;
    private double decorationHeight;

//    @Override
//    public void start(Stage primaryStage) {
//        StackPane root = new StackPane();
//        Scene scene = new Scene(root, 800, 600);
//        primaryStage.setScene(scene);
//
//        System.out.println("before sceneW " + scene.getWidth());
//        System.out.println("before sceneH " +  scene.getHeight());
//        System.out.println("before stageW " + primaryStage.getWidth());
//        System.out.println("before stageH " + primaryStage.getHeight());
//
//        primaryStage.show();
//
//        System.out.println("after sceneW " + scene.getWidth());
//        System.out.println("after sceneH " +  scene.getHeight());
//        System.out.println("after stageW " + primaryStage.getWidth());
//        System.out.println("after stageH " + primaryStage.getHeight());
//
//        primaryStage.setWidth(640 + (primaryStage.getWidth()-scene.getWidth()));
//        primaryStage.setHeight(480);
//
//        System.out.println("before sceneW " + scene.getWidth());
//        System.out.println("before sceneH " +  scene.getHeight());
//        System.out.println("before stageW " + primaryStage.getWidth());
//        System.out.println("before stageH " + primaryStage.getHeight());
//    }

    @Override
    public void start(final Stage stage) {
        images = loadImageList("D:\\dev\\nudes\\");

        final Pane panelsPane = new Pane();
        final ImageView imageView = new ImageView();
        final StackPane sceneLayout = new StackPane();
        sceneLayout.getChildren().addAll(imageView, panelsPane);

        final Scene scene = new Scene(sceneLayout, 800, 800);

        stage.setScene(scene);
        stage.show();
        this.decorationWidth = (stage.getWidth() - scene.getWidth());
        this.decorationHeight = (stage.getHeight() - scene.getHeight());

        updateImage(imageView, stage);

        panelsPane.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                final Node panel = makeDraggable(createProgressPanel());
                panelsPane.getChildren().add(panel);
                panel.setTranslateX(click.getSceneX() - 30);
                panel.setTranslateY(click.getSceneY() - 30);

                panel.setOnMouseClicked(click2 -> {
                    if (click2.getButton().equals(MouseButton.SECONDARY)) {
                        panelsPane.getChildren().remove(panel);
                    }
                });
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER || key.getCode() == KeyCode.SPACE) {
                ImageXML img = new ImageXML(images.get(imageIndex - 1).getName());

                panelsPane.getChildren().forEach(node -> {
                    Bounds bb = node.localToScene(node.getLayoutBounds());
                    img.addBox(new BoxXML((int) (bb.getMinX()), (int) (bb.getMinY()), (int) bb.getWidth(), (int) bb
                            .getHeight()));
                });

                dataset.addImage(img);
                panelsPane.getChildren().clear();
                updateImage(imageView, stage);
            }
        });


    }

    private void updateImage(ImageView imageView, Stage stage) {
        imageView.setImage(new Image(images.get(imageIndex).toURI().toString()));
        stage.setWidth(imageView.getImage().getWidth() + decorationWidth);
        stage.setHeight(imageView.getImage().getHeight() + decorationHeight);
        imageIndex++;
    }

    private List<File> loadImageList(String path) {
        File[] files = new File(path).listFiles();
        if (files != null) {
            return Arrays.stream(files).filter(f -> f.isFile() && extensionIsValid(f)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private boolean extensionIsValid(File file) {
        int i = file.getName().lastIndexOf('.');
        return i > 0 && file.getName().substring(i + 1).toLowerCase().matches("jpg|jpeg|bmp|png");
    }

    public static void main(final String[] args) {
        launch(args);
    }

    private Node makeDraggable(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);
        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            dragContext.mouseAnchorX = mouseEvent.getX();
            dragContext.mouseAnchorY = mouseEvent.getY();
            dragContext.initialTranslateX = node.getTranslateX();
            dragContext.initialTranslateY = node.getTranslateY();
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            node.setTranslateX(dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX);
            node.setTranslateY(dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY);
        });
        return wrapGroup;
    }


    private static Node createProgressPanel() {
        final HBox hbox = new HBox();
        hbox.setMinSize(60, 60);
        hbox.setMaxSize(60, 60);
        hbox.setBlendMode(BlendMode.DIFFERENCE);
        hbox.setStyle("-fx-background-color: white;");
        return hbox;
    }

    private static final class DragContext {
        double mouseAnchorX;
        double mouseAnchorY;
        double initialTranslateX;
        double initialTranslateY;
    }

    @Override
    public void stop() {
        try (PrintWriter out = new PrintWriter("D:\\dev\\nudes\\dataset.xml")) {
            out.println(dataset.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}