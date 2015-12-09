package cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge;

import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.muscles.Execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentAndPartialSumCalculation implements Execute<Range, PartialResultRange> {
    private final List<Point> data;

    public AssignmentAndPartialSumCalculation(List<Point> data) {
        this.data = data;
    }

    @Override
    public PartialResultRange execute(Range param) throws Exception {
        final Map<Integer, Partial> partials = new HashMap<>();
        for (int i = param.left; i < param.right; i++) {
            Point currentPoint = data.get(i);
            int clusterIndex = ExpectationSteps.nearestClusterCenterEuclidean(currentPoint, param.clusters).getClusterIndex();
            if (partials.get(clusterIndex) == null) {
                partials.put(clusterIndex, new Partial(currentPoint.getDimension()));
            }
            partials.get(clusterIndex).add(currentPoint);
        }
        final PartialResultRange result = new PartialResultRange(param.left, param.right);
        result.setPartials(partials);
        return result;
    }
}
