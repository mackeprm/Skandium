package cl.niclabs.skandium.examples.kmeans.simulated;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FindNearestClusterCenter implements Execute<Collection<Point>, Collection<ClusteredPoint>> {

    private final List<Point> clusterCenters;

    public FindNearestClusterCenter(List<Point> clusterCenters) {
        this.clusterCenters = clusterCenters;
    }

    @Override
    public Collection<ClusteredPoint> execute(Collection<Point> param) {
        Collection<ClusteredPoint> result = new ArrayList<>(param.size());
        for (Point inputPoint : param) {
            result.add(ExpectationSteps.nearestClusterCenterEuclidean(inputPoint, clusterCenters));
        }
        return result;
    }
}
