package sample;

import java.util.ArrayList;
import java.util.List;

class DatasetXML {
    private List<ImageXML> images;
    private String name;

    DatasetXML(String name) {
        this.images = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='utf-8'?>\n");
        sb.append("<?xml-stylesheet type='text/xsl' href='image_metadata_stylesheet.xsl'?>\n");
        sb.append("<dataset>\n");
        sb.append("<name>");
        sb.append(name);
        sb.append("</name>\n");
        sb.append("<images>\n");
        images.forEach(im -> sb.append(im.toString()));
        sb.append("</images>\n");
        sb.append("</dataset>");
        return sb.toString();
    }

    void addImage(ImageXML image) {
        int index = images.indexOf(image);
        if (index == -1) {
            images.add(image);
        } else {
            images.remove(index);
            images.add(index, image);
        }
    }

    ImageXML getImage(int imageIndex) {
        if (imageIndex < 0 || imageIndex >= images.size()) {
            return null;
        }
        return images.get(imageIndex);
    }
}
