package de.mackeprm.skandium.kmeans.implementations.lloyd.hybridpartition;

import cl.niclabs.skandium.muscles.Partition;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;

import static de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.KChunksMaximizationFunctions.merge;
import static de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.KChunksMaximizationFunctions.split;

public class HybridPartition implements Partition<RangeWithAssignments, double[][]> {

    private final double[][] data;
    private int numberOfClusterCenters;

    public HybridPartition(int numberOfClusterCenters, double[][] data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.data = data;
    }

    @Override
    public double[][][] partition(RangeWithAssignments[] param) throws Exception {
        return split(merge(param, data.length), numberOfClusterCenters, data);
    }
}
