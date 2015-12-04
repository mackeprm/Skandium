package cl.niclabs.skandium.examples.kmeans.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaximizationSteps {

    public static Point calculateMeanOf(final List<Point> points, final int dimension) {
        final Double[] centroid = new Double[dimension];
        Arrays.fill(centroid, 0.0);
        for (final Point p : points) {
            final List<Double> point = p.getValues();
            for (int i = 0; i < centroid.length; i++) {
                centroid[i] += point.get(i);
            }
        }
        for (int i = 0; i < centroid.length; i++) {
            centroid[i] /= points.size();
        }
        return new Point(Arrays.asList(centroid));
    }

    //median for each dimension using the manhattan distance:
    //see https://en.wikipedia.org/wiki/K-medians_clustering
    //https://en.wikipedia.org/wiki/Geometric_median ?
    //https://de.wikipedia.org/wiki/Median
    public static Point calculateMedianOf(final List<Point> points, final int dimension) {
        final List<Double> resultValues = new ArrayList<>(dimension);
        double[][] dimensionValues = new double[dimension][points.size()];
        for (int pointIndex = 0; pointIndex < points.size(); pointIndex++) {
            List<Double> currentValues = points.get(pointIndex).getValues();
            for (int dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++) {
                dimensionValues[dimensionIndex][pointIndex] = currentValues.get(dimensionIndex);
            }
        }
        for (int i = 0; i < dimension; i++) {
            resultValues.add(medianOf(dimensionValues[i]));
        }
        return new Point(resultValues);
    }

    static double medianOf(double[] doubles) {
        double median;
        int size = doubles.length;
        Arrays.sort(doubles);
        if (size % 2 == 0) {
            //the median is then usually defined to be the mean of the two middle values [1] [2] (the median of {3, 5, 7, 9} is (5 + 7) / 2 = 6)
            median = (doubles[((size / 2) - 1)] + doubles[(size / 2)]) / 2.0;
        } else {
            median = doubles[(size / 2)];
        }
        return median;
    }

    /*
    Swap-step: Within each cluster, each point is tested as a potential medoid by checking if the sum of within-cluster distances gets smaller using that point as the medoid. If so, the point is defined as a new medoid. Every point is then assigned to the cluster with the closest medoid.
     */
    public static Point metoidOf(final List<Point> points, final int dimension) {
        Point result = null;
        double minDistance = Double.MAX_VALUE;
        for (Point point : points) {
            double distance = calculateDistanceBetween(point, points);
            if (minDistance > distance) {
                result = point;
                minDistance = distance;
            }
        }
        return result;
    }

    private static double calculateDistanceBetween(Point pivot, List<Point> points) {
        double sum = 0;
        for (Point point : points) {
            sum += ExpectationSteps.manhattanDistance(pivot.getValues(), point.getValues());
        }
        return sum;
    }
}
