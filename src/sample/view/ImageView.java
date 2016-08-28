package sample.view;

import sample.model.Image;

public class ImageView {
    private static ImageView imageViewInstance = new ImageView();
    private String uriString;

    private ImageView(){}

    public String getUriString() {
        return uriString;
    }

    public static ImageView get(Image image){
        return imageViewInstance;
    }
}
