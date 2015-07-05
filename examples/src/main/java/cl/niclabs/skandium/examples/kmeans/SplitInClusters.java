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
    public List<XYPoint>[] split(Collection<ClusteredXYPoint> param) throws Exception {
        final List<XYPoint>[] result;
        result = (ArrayList<XYPoint>[]) new ArrayList<?>[numberOfClusterCenters];
        for (ClusteredXYPoint clusterPoint : param) {
            Integer clusterIndex = clusterPoint.getClusterIndex();
            if (result[clusterIndex] == null) {
                result[clusterIndex] = new ArrayList<>();
            }
            result[clusterIndex].add(clusterPoint);
        }
        return result;
    }
}
