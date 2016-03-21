package cl.niclabs.skandium.examples.kmeans.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.Arrays;
import java.util.List;

public class BoundingBox {
    private final double[] minima;
    private final double[] maxima;


    public BoundingBox(Point minimum, Point maximum) {
        this.minima = minimum.getValues().stream().mapToDouble(Double::doubleValue).toArray();
        this.maxima = maximum.getValues().stream().mapToDouble(Double::doubleValue).toArray();
    }

    public boolean contains(Point input) {
        final List<Double> values = input.getValues();
        for (int d = 0; d < input.getDimension(); d++) {
            final Double pivot = values.get(d);
            if (pivot < minima[d] || pivot > maxima[d]) {
                return false;
            }
        }
        return true;
    }

    public double[] getMinima() {
        return minima;
    }

    public double[] getMaxima() {
        return maxima;
    }

    @Override
    public String toString() {
        return "[" + Arrays.toString(minima) + "|" + Arrays.toString(maxima) + "]";
    }
}
