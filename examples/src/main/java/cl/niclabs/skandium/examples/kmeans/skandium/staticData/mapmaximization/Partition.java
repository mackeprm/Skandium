package cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.List;

public class Partition implements Split<Range, List<Point>> {

    private final int numberOfClusterCenters;
    private final List<Point> data;

    public Partition(int numberOfClusterCenters, List<Point> data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Point>[] split(Range param) {
        final List<Point>[] result;
        result = (ArrayList<Point>[]) new ArrayList<?>[numberOfClusterCenters];
        for (int i = 0; i < param.clusterIndices.size(); i++) {
            final Integer currentIndex = param.clusterIndices.get(i);
            if (result[currentIndex] == null) {
                result[currentIndex] = new ArrayList<>();
            }
            result[currentIndex].add(data.get(i));
        }
        return result;
    }
}
