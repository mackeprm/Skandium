package cl.niclabs.skandium.examples.kmeans.lloyd;

import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.List;

public class MergeRanges implements Merge<Range, Range> {

    private final int dataSize;

    public MergeRanges(int dataSize) {
        this.dataSize = dataSize;
    }

    @Override
    public Range merge(Range[] param) throws Exception {
        final List<Integer> clusterIndices = new ArrayList<>(dataSize);
        for (Range subrange : param) {
            clusterIndices.addAll(subrange.clusterIndices);
        }
        //TODO make clusterIndices immutable;
        final Range range = new Range(0, dataSize);
        range.clusterIndices = clusterIndices;
        return range;
    }
}
