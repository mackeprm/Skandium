package de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization;

import cl.niclabs.skandium.muscles.Split;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;

public class MaximizationSplit implements Split<RangeWithAssignments, double[][]> {

    private final int numberOfClusterCenters;
    private final double[][] data;

    public MaximizationSplit(int numberOfClusterCenters, double[][] data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public double[][][] split(RangeWithAssignments param) {
        return KChunksMaximizationFunctions.split(param, numberOfClusterCenters, data);
    }
}
