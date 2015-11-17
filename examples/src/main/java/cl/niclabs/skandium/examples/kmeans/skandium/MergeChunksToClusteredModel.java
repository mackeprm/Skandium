package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.muscles.Merge;

public class MergeChunksToClusteredModel implements Merge<ClusteredChunk, ClusteredModel> {

    @Override
    public ClusteredModel merge(ClusteredChunk[] param) throws Exception {
        ClusteredModel result = new ClusteredModel();
        for (ClusteredChunk chunk : param) {
            chunk.getPoints().forEach(result::add);
        }
        return result;
    }
}
