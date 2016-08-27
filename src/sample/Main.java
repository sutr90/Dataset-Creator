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
import javafx.scene.input.*;
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

    private final static double SIZE = 30;

    private double zoom = 1.0;


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
                final Node panel = makeDraggable();
                panelsPane.getChildren().add(panel);
                panel.setScaleX(zoom);
                panel.setScaleY(zoom);
                panel.setTranslateX(click.getSceneX() - SIZE / 2);
                panel.setTranslateY(click.getSceneY() - SIZE / 2);

                panel.setOnMouseClicked(click2 -> {
                    if (click2.getButton().equals(MouseButton.SECONDARY)) {
                        panelsPane.getChildren().remove(panel);
                    }
                });

                panel.setOnScroll(event -> {
                    doZoom(event, panel);
                    event.consume();
                });
            }
        });

        panelsPane.setOnScroll(event -> panelsPane.getChildren().forEach(panel -> doZoom(event, panel)));

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

    private void doZoom(ScrollEvent event, Node panel){
        double zoomFactor = event.isControlDown()? 1.2 : 1.05;
        double deltaY = event.getDeltaY();
        if (deltaY < 0) {
            zoomFactor = 2.0 - zoomFactor;
        }
        zoom = Math.max(zoom * zoomFactor, 1.0);
        panel.setScaleX(Math.max(panel.getScaleX() * zoomFactor, 1.0));
        panel.setScaleY(Math.max(panel.getScaleY() * zoomFactor, 1.0));
    }

    private void updateImage(ImageView imageView, Stage stage) {
        imageView.setImage(new Image(images.get(imageIndex).toURI().toString()));
        stage.setWidth(imageView.getImage().getWidth() + decorationWidth);
        stage.setHeight(imageView.getImage().getHeight() + decorationHeight);
        imageIndex++;
        zoom = 1.0;
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

    private Node makeDraggable() {
        final HBox node = new HBox();
        node.setMinSize(SIZE, SIZE);
        node.setBlendMode(BlendMode.DIFFERENCE);
        node.setStyle("-fx-background-color: white;");

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

    private final class DragContext {
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