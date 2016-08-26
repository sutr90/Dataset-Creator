package sample;

import java.util.ArrayList;
import java.util.List;

class ImageXML {
    private String image;
    private List<BoxXML> boxes;

    public ImageXML(String image) {
        this.image = image;
        boxes = new ArrayList<>();
    }

    public void addBox(BoxXML box) {
        boxes.add(box);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<image file='");
        sb.append(image);
        sb.append("'>");
        boxes.forEach(box -> sb.append(box.toString()));
        sb.append("</image>\n");
        return sb.toString();
    }
}
