package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.muscles.Execute;

import java.util.Collection;
import java.util.List;

public class FindNearestClusterCenter implements Execute<Collection<XYPoint>,Collection<ClusteredXYPoint>> {

    private final List<XYPoint> clusterCenters;

    public FindNearestClusterCenter(List<XYPoint> clusterCenters) {
        this.clusterCenters = clusterCenters;
    }

    @Override
    public Collection<ClusteredXYPoint> execute(Collection<XYPoint> param) throws Exception {
        //TODO implement euclidean distance
        return null;
    }
}
