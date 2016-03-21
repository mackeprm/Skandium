package cl.niclabs.skandium.examples.kmeans.localData.simulated;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.Collection;

public class MergeChunksToSet implements Merge<Collection<ClusteredPoint>, Collection<ClusteredPoint>> {
    @Override
    public Collection<ClusteredPoint> merge(Collection<ClusteredPoint>[] param) {
        final int estimatedSize = param[0].size() * param.length;
        final Collection<ClusteredPoint> result = new ArrayList<>(estimatedSize);
        for (final Collection<ClusteredPoint> chunk : param) {
            result.addAll(chunk);
        }
        return result;
    }
}
