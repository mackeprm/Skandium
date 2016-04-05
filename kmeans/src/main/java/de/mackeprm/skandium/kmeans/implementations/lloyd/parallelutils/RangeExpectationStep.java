package de.mackeprm.skandium.kmeans.implementations.lloyd.parallelutils;

import cl.niclabs.skandium.muscles.Execute;
import de.mackeprm.skandium.kmeans.model.ExpectationSteps;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

public class RangeExpectationStep implements Execute<RangeWithCentroids, RangeWithAssignments> {
    private final double[][] data;

    public RangeExpectationStep(double[][] data) {
        this.data = data;
    }

    @Override
    public RangeWithAssignments execute(RangeWithCentroids param) throws Exception {
        final RangeWithAssignments result = new RangeWithAssignments(param.left, param.right);
        for (int i = param.left; i < param.right; i++) {
            result.assignments[i - param.left] = ExpectationSteps.nearestClusterEuclidean(data[i], param.centroids);
        }
        return result;
    }
}
