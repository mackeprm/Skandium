package cl.niclabs.skandium.examples.kmeans.manual;

import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManualAssignmentStep extends Thread {
    private final List<Point> data;
    private final Range localRange;
    private List<Point> clusterCenters;
    private Map<Integer, Partial> partials;

    public ManualAssignmentStep(List<Point> data, Range range, List<Point> clusterCenters) {
        this.data = data;
        this.localRange = range;
        this.clusterCenters = clusterCenters;
    }

    @Override
    public void run() {
        partials = new HashMap<>();
        for (int j = localRange.left; j < localRange.right; j++) {
            Point currentPoint = data.get(j);
            int clusterIndex = ExpectationSteps.nearestClusterCenterEuclidean(currentPoint, clusterCenters).getClusterIndex();
            if (partials.get(clusterIndex) == null) {
                partials.put(clusterIndex, new Partial(currentPoint.getDimension()));
            }
            partials.get(clusterIndex).add(currentPoint);
        }
    }

    public void setClusterCenters(List<Point> clusterCenters) {
        this.clusterCenters = clusterCenters;
    }

    public Map<Integer, Partial> getPartials() {
        return partials;
    }
}
