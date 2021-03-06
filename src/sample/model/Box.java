package sample.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Box {
    private static final double MIN_WIDTH = 16;
    private static final double MIN_HEIGHT = 16;

    private DoubleProperty zoom = new SimpleDoubleProperty(4.0);
    private DoubleProperty widthProperty = new SimpleDoubleProperty();
    private DoubleProperty heightProperty = new SimpleDoubleProperty();
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;

    Box(double x, double y) {
        widthProperty.bind(zoom.multiply(MIN_WIDTH));
        heightProperty.bind(zoom.multiply(MIN_HEIGHT));
        xProperty = new SimpleDoubleProperty(x);
        yProperty = new SimpleDoubleProperty(y);
    }

    public DoubleProperty getZoom() {
        return zoom;
    }

    String toXmlString() {
        return "<box top='" + (yProperty.intValue() - heightProperty.intValue() / 2) + "' left='" + (xProperty
                .intValue() - widthProperty.intValue() / 2) + "' width='" + widthProperty.intValue() + "' " +
                "height='" + heightProperty.intValue() + "'/>\n";
    }

    public DoubleProperty getWidthProperty() {
        return widthProperty;
    }

    public DoubleProperty getHeightProperty() {
        return heightProperty;
    }

    public DoubleProperty getXProperty() {
        return xProperty;
    }

    public DoubleProperty getYProperty() {
        return yProperty;
    }
}
