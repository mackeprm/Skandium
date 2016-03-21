package cl.niclabs.skandium.examples.kmeans.lloyd.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.lloyd.Range;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.List;

public class MaximizationMerge implements Merge<Point, Range> {

    private final int dataSize;

    public MaximizationMerge(int dataSize) {
        this.dataSize = dataSize;
    }

    @Override
    public Range merge(Point[] param) throws Exception {
        final List<Point> newClusterCenters = new ArrayList<>(param.length);
        for(Point p : param) {
            if (p != null && p != Point.EMPTY_CLUSTER_POINT) {
                newClusterCenters.add(p);
            }
        }
        return new Range(0, dataSize, newClusterCenters);
    }
}
