package de.mackeprm.skandium.kmeans.implementations.kmedian;

import cl.niclabs.skandium.muscles.Execute;
import de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.KChunksMaximizationFunctions;
import de.mackeprm.skandium.kmeans.model.MaximizationSteps;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

public class SMKmedianMaximizationStep implements Execute<RangeWithAssignments, RangeWithCentroids> {
    private final int numberOfClusterCenters;
    private final int dimension;
    private final double[][] data;

    public SMKmedianMaximizationStep(int numberOfClusterCenters, int dimension, double[][] data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.dimension = dimension;
        this.data = data;
    }

    @Override
    public RangeWithCentroids execute(RangeWithAssignments param) throws Exception {
        //step 1: convert assignments into clusters
        double[][][] split = KChunksMaximizationFunctions.split(param, numberOfClusterCenters, data);
        //step 2: convert clusters into centroids
        double[][] centroids = new double[numberOfClusterCenters][];
        for (int i = 0; i < numberOfClusterCenters; i++) {
            centroids[i] = MaximizationSteps.calculateMedianOf(split[i], dimension);
        }
        return new RangeWithCentroids(0, data.length, centroids);
    }
}
