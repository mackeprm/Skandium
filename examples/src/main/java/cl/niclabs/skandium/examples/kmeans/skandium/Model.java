package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class Model {
    private final List<Point> data;
    private List<Point> clusterCenters;

    public Model(List<Point> data, List<Point> clusterCenters) {
        this.data = data;
        this.clusterCenters = clusterCenters;
    }

    public List<Point> getClusterCenters() {
        return clusterCenters;
    }

    public List<Point> getData() {
        return data;
    }
}
