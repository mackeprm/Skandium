package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CalculateMean implements Function<List<Point>, Point> {
    @Override
    public Point apply(List<Point> cluster) {
        return centroidOf(cluster, cluster.get(0).getDimension());
    }

    private Point centroidOf(final List<Point> points, final int dimension) {
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
}
