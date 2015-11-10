package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.Collection;

public class MergeChunksToSet implements Merge<Collection<ClusteredPoint>, Collection<ClusteredPoint>> {
    @Override
    public Collection<ClusteredPoint> apply(final Collection<Collection<ClusteredPoint>> param) {
        final int estimatedSize = param.iterator().next().size() * param.size();
        final Collection<ClusteredPoint> result = new ArrayList<>(estimatedSize);
        for (final Collection<ClusteredPoint> chunk : param) {
            result.addAll(chunk);
        }
        return result;
    }
}
