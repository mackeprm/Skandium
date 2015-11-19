package cl.niclabs.skandium.examples.kmeans.skandium.localData.simulated;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SplitInClusters implements Split<Collection<ClusteredPoint>, List<Point>> {
    final int numberOfClusterCenters;

    public SplitInClusters(int numberOfClusterCenters) {
        this.numberOfClusterCenters = numberOfClusterCenters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Point>[] split(Collection<ClusteredPoint> param) {
        final List<Point>[] result;
        result = (ArrayList<Point>[]) new ArrayList<?>[numberOfClusterCenters];
        for (ClusteredPoint clusterPoint : param) {
            Integer clusterIndex = clusterPoint.getClusterIndex();
            if (result[clusterIndex] == null) {
                result[clusterIndex] = new ArrayList<>();
            }
            result[clusterIndex].add(clusterPoint);
        }
        return result;
    }
}
