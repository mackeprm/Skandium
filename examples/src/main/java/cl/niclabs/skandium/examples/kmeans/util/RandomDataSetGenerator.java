package cl.niclabs.skandium.examples.kmeans.util;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataSetGenerator {
    private final Random random;
    private final int dimension;

    public RandomDataSetGenerator(int dimension) {
        this.random = new Random();
        this.dimension = dimension;
    }

    public RandomDataSetGenerator(int dimension, long seed) {
        this.random = new Random(seed);
        this.dimension = dimension;
    }

    public List<Point> generatePoints(int numberOfPoints) {
        final List<Point> result = new ArrayList<>(numberOfPoints);
        for (int i = 0; i < numberOfPoints; i++) {
            result.add(new Point(getRandomDoubles()));
        }
        return result;
    }

    protected int getDimension() {
        return dimension;
    }

    protected List<Double> getRandomDoubles() {
        final List<Double> result = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            result.add(random.nextDouble());
        }
        return result;
    }
}
