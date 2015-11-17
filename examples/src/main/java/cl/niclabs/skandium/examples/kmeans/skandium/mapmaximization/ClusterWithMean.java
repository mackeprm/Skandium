package cl.niclabs.skandium.examples.kmeans.skandium.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class ClusterWithMean extends Cluster {
    private Point mean;

    public ClusterWithMean(List<Point> points, Point mean) {
        super(points);
        this.mean = mean;
    }

    public Point getMean() {
        return mean;
    }
}
