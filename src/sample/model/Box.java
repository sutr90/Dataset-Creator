package sample.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Box {
    private double x;
    private double y;
    private static final double MIN_WIDTH = 16;
    private static final double MIN_HEIGHT = 16;

    private DoubleProperty zoom = new SimpleDoubleProperty(4.0);
    private DoubleProperty widthProperty = new SimpleDoubleProperty();
    private DoubleProperty heightProperty = new SimpleDoubleProperty();

    Box(double x, double y) {
        this.x = x;
        this.y = y;

        widthProperty.bind(zoom.multiply(MIN_WIDTH));
        heightProperty.bind(zoom.multiply(MIN_HEIGHT));
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void addToY(double y) {
        this.y += y;
    }

    public void addToX(double x) {
        this.x += x;
    }

    public DoubleProperty getZoom() {
        return zoom;
    }

    String toXmlString() {
        return "<box top='" + (int)y + "' left='" + (int)x + "' width='" + (int) (widthProperty.get()) + "' height='" + (int)
                (heightProperty.get()) + "'/>\n";
    }

    public DoubleProperty getWidthProperty() {
        return widthProperty;
    }

    public DoubleProperty getHeightProperty() {
        return heightProperty;
    }
}
