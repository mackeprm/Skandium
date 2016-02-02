package cl.niclabs.skandium.examples.kmeans.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.niclabs.skandium.examples.kmeans.util.StringUtils.isBlank;

public class Point {
    //This is needed to prevent a NPE beeing thrown by the Task class that attempts to construct
    //the final result array for a parent with the datatype of the last finished subtask. If the
    //last subtask returned null (because of an empty cluster the whole thing breaks.
    public static final Point EMPTY_CLUSTER_POINT = new Point("0");

    private final List<Double> values;
    private int dimension;

    public Point(List<Double> values) {
        this.values = Collections.unmodifiableList(values);
        this.dimension = this.values.size();
    }

    public Point(String input) {
        this(parseDoublesFrom(input));
    }

    protected static List<Double> parseDoublesFrom(String input) {
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

    public Double[] subtract(Point subtract) {
        final Double result[] = new Double[this.dimension];
        final List<Double> values = subtract.getValues();
        for (int d = 0; d < this.dimension; d++) {
            result[d] = this.values.get(d) - values.get(d);
        }
        return result;
    }
}
