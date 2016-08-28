package sample.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Image {
    private final File imgFile;
    private final List<Box> boxes;

    Image(File img) {
        this.imgFile = img;
        boxes = new ArrayList<>();
    }

    public String getUriString() {
        return imgFile.toURI().toString();
    }

    String getName() {
        return imgFile.getName();
    }

    void createBox(double x, double y) {
        boxes.add(new Box(x,y));
    }

    List<Box> getBoxes() {
        return boxes;
    }
}
