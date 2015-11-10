package cl.niclabs.skandium.examples.kmeans.model;

import java.util.List;

public class ClusteredPoint extends Point {
    int clusterIndex;

    public ClusteredPoint(int clusterIndex, List<Double> values) {
        super(values);
        if (clusterIndex >= 0) {
            this.clusterIndex = clusterIndex;
        } else {
            throw new IllegalArgumentException("clusterIndex was below 0");
        }
    }

    public int getClusterIndex() {
        return clusterIndex;
    }
}
