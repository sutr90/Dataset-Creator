package sample.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Box {
    private double x;
    private double y;
    private double width = 16;
    private double height = 16;

    private DoubleProperty zoom = new SimpleDoubleProperty(4.0);

    Box(double x, double y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public DoubleProperty getZoom() {
        return zoom;
    }

    String toXmlString() {
        return "<box top='" + (int)y + "' left='" + (int)x + "' width='" + (int) (width * zoom.get()) + "' height='" + (int)
                (height * zoom.get()) + "'/>\n";
    }
}
