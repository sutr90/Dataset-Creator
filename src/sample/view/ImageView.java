package sample.view;

import sample.model.Image;

public class ImageView {
    private static ImageView imageViewInstance = new ImageView();
    private Image image;

    private ImageView(){}

    String getUriString() {
        return image.getUriString();
    }

    public static ImageView get(Image image){
        imageViewInstance.image = image;
        return imageViewInstance;
    }
}
