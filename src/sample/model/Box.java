package sample.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Box {
    private double x;
    private double y;
    private double width = 60;
    private double height = 60;

    private DoubleProperty zoom = new SimpleDoubleProperty(1.0);

    Box(double x, double y) {
        this.x = x;
        this.y = y;
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

    public void setZoom(DoubleProperty zoom) {
        this.zoom = zoom;
    }
}
