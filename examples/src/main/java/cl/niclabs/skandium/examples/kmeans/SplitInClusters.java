package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SplitInClusters implements Split<Collection<ClusteredXYPoint>, List<XYPoint>> {
    final int numberOfClusterCenters;

    public SplitInClusters(int numberOfClusterCenters) {
        this.numberOfClusterCenters = numberOfClusterCenters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<List<XYPoint>> apply(Collection<ClusteredXYPoint> param) {
        final List<List<XYPoint>> result = new ArrayList<>(numberOfClusterCenters);
        for (ClusteredXYPoint clusterPoint : param) {
            Integer clusterIndex = clusterPoint.getClusterIndex();
            if (result.get(clusterIndex) == null) {
                result.set(clusterIndex, new ArrayList<>());
            }
            result.get(clusterIndex).add(clusterPoint);
        }
        return result;
    }
}
