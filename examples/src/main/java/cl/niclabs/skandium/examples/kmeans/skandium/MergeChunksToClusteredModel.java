package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.muscles.Merge;

import java.util.List;

public class MergeChunksToClusteredModel implements Merge<List<ClusteredPoint>, ClusteredModel> {

    @Override
    public ClusteredModel merge(List<ClusteredPoint>[] param) throws Exception {
        ClusteredModel result = new ClusteredModel();
        for (List<ClusteredPoint> chunk : param) {
            chunk.forEach(result::add);
        }
        return result;
    }
}
