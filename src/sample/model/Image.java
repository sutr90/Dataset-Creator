package sample.model;

import java.io.File;

public class Image {
    private final File imgFile;

    Image(File img) {
        this.imgFile = img;
    }

    public String getUriString() {
        return imgFile.toURI().toString();
    }
}
