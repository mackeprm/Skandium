package de.mackeprm.skandium.kmeans.implementations.lloyd.partialmerge;

import cl.niclabs.skandium.muscles.Execute;
import de.mackeprm.skandium.kmeans.model.ExpectationSteps;
import de.mackeprm.skandium.kmeans.model.modelutils.Partial;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

public class PartialExpectationStep implements Execute<RangeWithCentroids, RangeWithPartials> {
    private final double[][] data;
    private final int dimension;

    public PartialExpectationStep(double[][] data, int dimension) {
        this.data = data;
        this.dimension = dimension;
    }

    @Override
    public RangeWithPartials execute(RangeWithCentroids param) throws Exception {
        Partial[] partials = new Partial[param.centroids.length];
        for (int i = 0; i < partials.length; i++) {
            partials[i] = new Partial(dimension);
        }
        for (int i = param.left; i < param.right; i++) {
            final double[] point = data[i];
            final int clusterIndex = ExpectationSteps.nearestClusterEuclidean(point, param.centroids);
            partials[clusterIndex].add(point);
        }
        return new RangeWithPartials(param.left, param.right, partials);
    }
}
