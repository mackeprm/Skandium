package de.mackeprm.skandium.kmeans.implementations.lloyd.sequentialmaximization;

import cl.niclabs.skandium.muscles.Execute;
import de.mackeprm.skandium.kmeans.model.modelutils.Partial;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

import java.util.Arrays;

public class SequentialRangeMaximizationStep implements Execute<RangeWithAssignments, RangeWithCentroids> {

    private final int numberOfClusterCenters;
    private final int dimension;
    private final double[][] data;

    public SequentialRangeMaximizationStep(int numberOfClusterCenters, int dimension, double[][] data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.dimension = dimension;
        this.data = data;
    }

    @Override
    public RangeWithCentroids execute(RangeWithAssignments param) throws Exception {
        double[][] centroids = new double[numberOfClusterCenters][];
        for (int i = 0; i < numberOfClusterCenters; i++) {
            centroids[i] = new double[dimension];
        }

        //Maximization:
        Partial[] partials = new Partial[numberOfClusterCenters];
        for (int j = 0; j < partials.length; j++) {
            partials[j] = new Partial(dimension);
        }

        for (int j = 0; j < data.length; j++) {
            partials[param.assignments[j]].add(data[j]);
        }
        for (int c = 0; c < numberOfClusterCenters; c++) {
            if (partials[c] != null) {
                final Partial currentPartial = partials[c];
                for (int d = 0; d < dimension; d++) {
                    centroids[c][d] = currentPartial.sum[d] / (float) currentPartial.count;
                }
            } else {
                centroids[c] = new double[dimension];
                //hm.
                Arrays.fill(centroids[c], 0d);
            }
        }
        return new RangeWithCentroids(0, data.length, centroids);
    }

}
