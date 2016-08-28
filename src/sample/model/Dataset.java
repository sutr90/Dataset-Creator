package sample.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Dataset {
    private final ListIterator<Image> imgIterator;

    private final String TITLE_TEMPLATE;
    private String title;

    public Dataset(String datasetPath) {
        List<Image> images = loadImages(datasetPath);
        TITLE_TEMPLATE = "%d/"+ images.size() + " - %s";
        imgIterator = images.listIterator();
    }

    private List<Image> loadImages(String datasetPath) {
        List<File> imgFiles = loadImageList(datasetPath);
        return imgFiles.stream().map(Image::new).collect(Collectors.toList());
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
        Image image = imgIterator.next();
        title = String.format(TITLE_TEMPLATE, imgIterator.nextIndex(), image.getName());
        return image;
    }

    public String getTitle() {
        return title;
    }
}
