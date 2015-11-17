package cl.niclabs.skandium.examples.kmeans.model;

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
