package sample.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
        return Collections.unmodifiableList(boxes);
    }

    void removeBox(Box box) {
        boxes.remove(box);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<image file='");
        sb.append(getName());
        sb.append("'>\n");
        boxes.forEach(box -> sb.append(box.toString()));
        sb.append("</image>\n");
        return sb.toString();
    }
}
