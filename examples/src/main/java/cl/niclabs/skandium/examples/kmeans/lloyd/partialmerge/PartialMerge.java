package cl.niclabs.skandium.examples.kmeans.lloyd.partialmerge;

import cl.niclabs.skandium.examples.kmeans.lloyd.Range;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Merge;

import java.util.*;
import java.util.stream.Collectors;

public class PartialMerge implements Merge<PartialResultRange, Range> {
    private final int dataSize;
    private final int dimension;

    public PartialMerge(int dataSize, int dimension) {
        this.dataSize = dataSize;
        this.dimension = dimension;
    }

    @Override
    public Range merge(PartialResultRange[] param) throws Exception {
        Map<Integer, Partial> globalPartials = new HashMap<>();

        for (PartialResultRange partialResultRange : param) {
            for (Map.Entry<Integer, Partial> entry : partialResultRange.getPartials().entrySet()) {
                if (globalPartials.get(entry.getKey()) == null) {
                    globalPartials.put(entry.getKey(), new Partial(dimension));
                }
                globalPartials.get(entry.getKey()).add(entry.getValue());
            }
        }
        final List<Point> clusterCenters = new ArrayList<>(globalPartials.size());
        clusterCenters.addAll(globalPartials.values().stream().map(partial -> calculateMeanOf(partial, dimension)).collect(Collectors.toList()));
        final Range range = new Range(0, dataSize);
        range.clusters = clusterCenters;
        return range;
    }

    private Point calculateMeanOf(Partial partial, int dimension) {
        final Double[] centroid = new Double[dimension];
        for (int i = 0; i < dimension; i++) {
            centroid[i] = partial.sum[i] / (double) partial.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}
