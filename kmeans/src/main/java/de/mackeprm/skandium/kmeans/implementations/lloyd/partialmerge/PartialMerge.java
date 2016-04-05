package de.mackeprm.skandium.kmeans.implementations.lloyd.partialmerge;

import cl.niclabs.skandium.muscles.Merge;
import de.mackeprm.skandium.kmeans.model.modelutils.Partial;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

import java.util.Arrays;

public class PartialMerge implements Merge<RangeWithPartials, RangeWithCentroids> {
    private final int dataSize;
    private final int dimension;
    private final int numberOfClusterCenters;

    public PartialMerge(int dataSize, int dimension, int numberOfClusterCenters) {
        this.dataSize = dataSize;
        this.dimension = dimension;
        this.numberOfClusterCenters = numberOfClusterCenters;
    }

    @Override
    public RangeWithCentroids merge(RangeWithPartials[] param) throws Exception {
        Partial[] globalPartials = new Partial[numberOfClusterCenters];
        for (int i = 0; i < globalPartials.length; i++) {
            globalPartials[i] = new Partial(dimension);
        }

        for (RangeWithPartials localPartial : param) {
            for (int i = 0; i < numberOfClusterCenters; i++) {
                globalPartials[i].add(localPartial.partials[i]);
            }
        }

        return new RangeWithCentroids(0, dataSize, getClusterCentroids(globalPartials));
    }

    private double[][] getClusterCentroids(Partial[] globalPartials) {
        double[][] centroids = new double[numberOfClusterCenters][];
        for (int i = 0; i < numberOfClusterCenters; i++) {
            centroids[i] = new double[dimension];
        }
        //Maximization:
        for (int c = 0; c < numberOfClusterCenters; c++) {
            if (globalPartials[c] != null) {
                final Partial currentPartial = globalPartials[c];
                for (int d = 0; d < dimension; d++) {
                    centroids[c][d] = currentPartial.sum[d] / (float) currentPartial.count;
                }
            } else {
                centroids[c] = new double[dimension];
                //hm.
                Arrays.fill(centroids[c], 0d);
            }
        }
        return centroids;
    }
}
