package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Split;

import java.util.*;

public class SplitInClusters implements Split<Collection<ClusteredPoint>, List<Point>> {
    final int numberOfClusterCenters;

    public SplitInClusters(int numberOfClusterCenters) {
        this.numberOfClusterCenters = numberOfClusterCenters;
    }

    @Override
    public Collection<List<Point>> apply(Collection<ClusteredPoint> param) {
        Map<Integer, List<Point>> clusters = new HashMap<>(numberOfClusterCenters);
        for (ClusteredPoint clusterPoint : param) {
            Integer clusterIndex = clusterPoint.getClusterIndex();
            if (clusters.get(clusterIndex) == null) {
                clusters.put(clusterIndex, new ArrayList<>());
            }
            clusters.get(clusterIndex).add(clusterPoint);
        }
        return clusters.values();
    }
}
