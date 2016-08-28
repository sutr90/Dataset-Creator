package sample.model;

public class Box {
    private double x;
    private double y;
    private double width = 60;
    private double height = 60;

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
}
