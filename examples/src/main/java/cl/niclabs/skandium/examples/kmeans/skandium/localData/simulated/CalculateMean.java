package cl.niclabs.skandium.examples.kmeans.skandium.localData.simulated;

import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class CalculateMean implements Execute<List<Point>, Point> {
    @Override
    public Point execute(List<Point> cluster) {
        return MaximizationSteps.calculateMeanOf(cluster, cluster.get(0).getDimension());
    }
}
