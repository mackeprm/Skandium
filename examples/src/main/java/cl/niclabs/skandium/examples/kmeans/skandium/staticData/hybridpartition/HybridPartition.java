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

    @Override
    public List<Point>[] partition(Range[] param) throws Exception {
        return split(merge(param));
    }

    @SuppressWarnings("unchecked")
    public List<Point>[] split(Range param) {
        final List<Point>[] result;
        result = (ArrayList<Point>[]) new ArrayList<?>[numberOfClusterCenters];
        init(result);
        for (int i = 0; i < param.clusterIndices.size(); i++) {
            final Integer currentIndex = param.clusterIndices.get(i);
            result[currentIndex].add(data.get(i));
        }
        return result;
    }

    private void init(List<Point>[] result) {
        for (int i = 0; i < numberOfClusterCenters; i++) {
            result[i] = new ArrayList<>();
        }
    }

    public Range merge(Range[] param) throws Exception {
        final List<Integer> clusterIndices = new ArrayList<>(data.size());
        for (Range subrange : param) {
            clusterIndices.addAll(subrange.clusterIndices);
        }
        final Range range = new Range(0, data.size());
        range.clusterIndices = clusterIndices;
        return range;
    }
}
