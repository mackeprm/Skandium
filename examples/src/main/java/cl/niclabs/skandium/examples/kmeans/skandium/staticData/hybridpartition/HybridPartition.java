package cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.muscles.Partition;

import java.util.ArrayList;
import java.util.List;

public class HybridPartition implements Partition<Range, List<Point>> {

    private int numberOfClusterCenters;
    private List<Point> data;

    public HybridPartition(int numberOfClusterCenters, List<Point> data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Point>[] partition(Range[] param) throws Exception {
        final List<Point>[] result;
        result = (ArrayList<Point>[]) new ArrayList<?>[numberOfClusterCenters];
        for (Range subrange : param) {
            for (int i = 0; i < subrange.clusterIndices.size(); i++) {
                final Integer currentIndex = subrange.clusterIndices.get(i);
                if (result[currentIndex] == null) {
                    result[currentIndex] = new ArrayList<>();
                }
                result[currentIndex].add(data.get(i));
            }
        }

        return result;
    }
}
