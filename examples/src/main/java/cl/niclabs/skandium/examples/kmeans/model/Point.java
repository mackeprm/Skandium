package cl.niclabs.skandium.examples.kmeans.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.niclabs.skandium.examples.kmeans.util.StringUtils.isBlank;

public class Point {
    private final List<Double> values;
    private int dimension;

    public Point(List<Double> values) {
        this.values = Collections.unmodifiableList(values);
        this.dimension = this.values.size();
    }

    public Point(String input) {
        this(parseDoublesFrom(input));
    }

    private static List<Double> parseDoublesFrom(String input) {
        if (isBlank(input)) {
            throw new IllegalArgumentException("Input: '" + input + "' can not be parsed");
        }
        final String[] split = input.split(",");
        final List<Double> parsedValues = new ArrayList<>(split.length);
        for (final String part : split) {
            parsedValues.add(Double.valueOf(part));
        }
        return parsedValues;
    }

    public int getDimension() {
        return dimension;
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "Vector: " + values;
    }
}
