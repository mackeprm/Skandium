package cl.niclabs.skandium.examples.kmeans.util;

public class XYPoint {
    double x;
    double y;

    public XYPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public XYPoint(String input) {
        String[] split = input.split(",");
        if (split.length == 2) {
            this.x = Double.valueOf(split[0]);
            this.y = Double.valueOf(split[1]);
        } else {
            throw new RuntimeException("incorred input format for: " + input);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "XYPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
