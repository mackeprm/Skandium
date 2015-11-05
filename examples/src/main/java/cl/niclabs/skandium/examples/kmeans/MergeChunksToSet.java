package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.Collection;

public class MergeChunksToSet implements Merge<Collection<ClusteredXYPoint>, Collection<ClusteredXYPoint>> {
    @Override
    public Collection<ClusteredXYPoint> apply(final Collection<Collection<ClusteredXYPoint>> param) {
        final int estimatedSize = param.iterator().next().size() * param.size();
        final Collection<ClusteredXYPoint> result = new ArrayList<>(estimatedSize);
        for (final Collection<ClusteredXYPoint> chunk : param) {
            result.addAll(chunk);
        }
        return result;
    }
}
