package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.Collection;

public class MergeChunksToSet implements Merge<Collection<ClusteredXYPoint>, Collection<ClusteredXYPoint>> {
    @Override
    public Collection<ClusteredXYPoint> merge(final Collection<ClusteredXYPoint>[] param) throws Exception {
        final int estimatedSize = param[0].size() * param.length;
        final Collection<ClusteredXYPoint> result = new ArrayList<>(estimatedSize);
        for(final Collection<ClusteredXYPoint> chunk : param) {
            result.addAll(chunk);
        }
        return result;
    }
}
