package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Merge;

import java.util.Collection;
import java.util.List;

public class MergeClusterCenters implements Merge<Point, List<Point>> {
    @Override
    public List<Point> apply(Collection<Point> param) {
        return (List<Point>) param;
    }
}
