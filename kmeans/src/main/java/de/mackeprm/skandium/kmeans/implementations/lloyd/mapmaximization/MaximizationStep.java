package de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization;

import cl.niclabs.skandium.muscles.Execute;
import de.mackeprm.skandium.kmeans.model.MaximizationSteps;

public class MaximizationStep implements Execute<double[][], double[]> {
    private final int dimension;

    public MaximizationStep(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public double[] execute(double[][] param) throws Exception {
        if (param == null || param.length == 0) {
            //System.err.println("empty cluster detected");
            return new double[dimension];
        } else {
            return MaximizationSteps.calculateMeanOf(param, dimension);
        }
    }
}
