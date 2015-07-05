package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FindNearestClusterCenter implements Execute<Collection<XYPoint>,Collection<ClusteredXYPoint>> {

    private final List<XYPoint> clusterCenters;

    public FindNearestClusterCenter(List<XYPoint> clusterCenters) {
        this.clusterCenters = clusterCenters;
    }

    @Override
    public Collection<ClusteredXYPoint> execute(Collection<XYPoint> param) throws Exception {
        Collection<ClusteredXYPoint> result = new ArrayList<>(param.size());
        for(XYPoint inputPoint : param) {
            result.add(assignSinglePoint(inputPoint,clusterCenters));
        }
        return result;
    }

    private ClusteredXYPoint assignSinglePoint(XYPoint point, List<XYPoint> clusterCenters) {
        double distance = Double.MAX_VALUE;
        int nearestClusterCenterIndex = Integer.MAX_VALUE;
        for(int i=0;i < clusterCenters.size();i++) {
            double currentDistance = calculateDistanceBetween(point, clusterCenters.get(i));
            if(currentDistance < distance) {
                distance = currentDistance;
                nearestClusterCenterIndex = i;
            }
        }
        return new ClusteredXYPoint(point, nearestClusterCenterIndex);
    }

    private double calculateDistanceBetween(XYPoint source, XYPoint destination) {
        double deltaX = source.getX() - destination.getX();
        double deltaY = source.getY() - destination.getY();
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }
}
