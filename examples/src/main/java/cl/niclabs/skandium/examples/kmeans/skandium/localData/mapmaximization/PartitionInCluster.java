package cl.niclabs.skandium.examples.kmeans.skandium.localData.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.ClusteredModel;
import cl.niclabs.skandium.muscles.Split;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PartitionInCluster implements Split<ClusteredModel, Cluster> {
    @Override
    public Cluster[] split(ClusteredModel param) throws Exception {
        final Set<Map.Entry<Integer, List<Point>>> entries = param.getEntries();
        final Cluster[] result = new Cluster[entries.size()];
        for (Map.Entry<Integer, List<Point>> entry : entries) {
            result[entry.getKey()] = new Cluster(entry.getValue());
        }
        return result;
    }
}
