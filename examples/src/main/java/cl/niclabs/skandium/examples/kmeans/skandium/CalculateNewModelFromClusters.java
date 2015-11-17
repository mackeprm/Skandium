package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalculateNewModelFromClusters implements Execute<ClusteredModel, Model> {
    @Override
    public Model execute(ClusteredModel param) throws Exception {
        List<Point> newCentroids = new ArrayList<>();
        List<Point> data = new ArrayList<>();
        for (Map.Entry<Integer, List<Point>> clusters : param.getEntries()) {
            newCentroids.add(centroidOf(clusters.getValue(), clusters.getValue().get(0).getDimension()));
            data.addAll(clusters.getValue());
        }
        return new Model(data, newCentroids);
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
