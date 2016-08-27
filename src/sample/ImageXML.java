package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ImageXML {
    private String image;
    private List<BoxXML> boxes;

    ImageXML(String image) {
        this.image = image;
        boxes = new ArrayList<>();
    }

    void addBox(BoxXML box) {
        boxes.add(box);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<image file='");
        sb.append(image);
        sb.append("'>\n");
        boxes.forEach(box -> sb.append(box.toString()));
        sb.append("</image>\n");
        return sb.toString();
    }

    List<BoxXML> getBoxes() {
        return boxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageXML imageXML = (ImageXML) o;
        return Objects.equals(image, imageXML.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }
}
