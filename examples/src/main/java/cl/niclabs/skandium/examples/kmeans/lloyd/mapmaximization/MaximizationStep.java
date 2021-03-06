package cl.niclabs.skandium.examples.kmeans.lloyd.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class MaximizationStep implements Execute<List<Point>, Point> {
    @Override
    public Point execute(List<Point> param) throws Exception {
        if (param == null || param.isEmpty()) {
            //System.err.println("empty cluster detected");
            return Point.EMPTY_CLUSTER_POINT;
        } else {
            return MaximizationSteps.calculateMeanOf(param, param.get(0).getDimension());
        }
    }
}
