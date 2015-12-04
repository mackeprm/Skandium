package cl.niclabs.skandium.examples.kmeans.skandium.kmedian.staticData;

import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.List;


public class MMKmedianExpectationStep implements Execute<Range, Range> {
    private final List<Point> data;

    public MMKmedianExpectationStep(List<Point> data) {
        this.data = data;
    }

    @Override
    public Range execute(Range param) throws Exception {
        final int rangeLength = param.right - param.left;
        final Range result = new Range(param.left, param.right);
        result.clusterIndices = new ArrayList<>(rangeLength);
        for (int i = param.left; i < param.right; i++) {
            result.clusterIndices.add(ExpectationSteps.nearestClusterCenterEuclidean(data.get(i), param.clusters).getClusterIndex());
        }
        return result;
    }
}
