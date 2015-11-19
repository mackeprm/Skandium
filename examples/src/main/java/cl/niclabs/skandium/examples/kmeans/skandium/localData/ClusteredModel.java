package cl.niclabs.skandium.examples.kmeans.skandium.localData;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.*;

public class ClusteredModel {
    private Map<Integer, List<Point>> clusters;

    public ClusteredModel() {
        clusters = new HashMap<>();
    }

    public void add(ClusteredPoint point) {
        int clusterIndex = point.getClusterIndex();
        if(this.clusters.get(clusterIndex) == null) {
            this.clusters.put(clusterIndex, new ArrayList<>());
        }
        this.clusters.get(clusterIndex).add(point);
    }

    public Set<Map.Entry<Integer, List<Point>>> getEntries() {
        return this.clusters.entrySet();
    }

}
