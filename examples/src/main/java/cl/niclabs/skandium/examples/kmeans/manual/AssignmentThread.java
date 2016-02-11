package cl.niclabs.skandium.examples.kmeans.manual;

import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.ArrayList;
import java.util.List;

public class AssignmentThread extends Thread {
    private final List<Point> data;
    private final Range localRange;
    private List<Point> clusterCenters;
    private List<Integer> clusterIndices;

    public AssignmentThread(List<Point> data, Range range, List<Point> clusterCenters) {
        this.data = data;
        this.localRange = range;
        this.clusterCenters = clusterCenters;
    }

    @Override
    public void run() {
        final int rangeLength = localRange.right - localRange.left;
        this.clusterIndices = new ArrayList<>(rangeLength);
        for (int i = localRange.left; i < localRange.right; i++) {
            this.clusterIndices.add(ExpectationSteps.nearestClusterCenterEuclidean(data.get(i), clusterCenters).getClusterIndex());
        }
    }

    public List<Integer> getClusterIndices() {
        return clusterIndices;
    }
}
