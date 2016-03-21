package cl.niclabs.skandium.examples.kmeans.localData;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.List;

public class ChunkExpectationStep implements Execute<Chunk, List<ClusteredPoint>> {

    @Override
    public List<ClusteredPoint> execute(Chunk param) throws Exception {
        final List<ClusteredPoint> result = new ArrayList<>(param.getSize());
        for (final Point point : param.getPoints()) {
            result.add(ExpectationSteps.nearestClusterCenterEuclidean(point, param.getClusterCenters()));
        }
        return result;
    }
}
