package sample.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dataset {
    private int imgIndex;
    private Image[] images;
    private final String TITLE_TEMPLATE;

    public Dataset(String datasetPath) {
        images = loadImages(datasetPath);
        TITLE_TEMPLATE = "%d/" + images.length + " - %s";
        imgIndex = -1;
    }

    private Image[] loadImages(String datasetPath) {
        List<File> imgFiles = loadImageList(datasetPath);
        return imgFiles.stream().map(Image::new).toArray(Image[]::new);
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

    public Image next() {
        imgIndex++;
        return images[imgIndex];
    }

    public String getTitle() {
        return String.format(TITLE_TEMPLATE, imgIndex+1, images[imgIndex].getName());
    }

    public boolean hasNext() {
        return imgIndex + 1 < images.length;
    }

    public boolean hasPrevious() {
        return imgIndex - 1 >= 0;
    }

    public Image previous() {
        imgIndex--;
        return images[imgIndex];
    }

    public void createBox(double x, double y) {
        images[imgIndex].createBox(x,y);
    }

    public List<Box> getBoxes() {
        return images[imgIndex].getBoxes();
    }

    public void removeBox(Box box) {
        images[imgIndex].removeBox(box);
    }
}
