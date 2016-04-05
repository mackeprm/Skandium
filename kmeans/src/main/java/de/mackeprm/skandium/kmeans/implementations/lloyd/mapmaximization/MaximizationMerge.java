package de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization;

import cl.niclabs.skandium.muscles.Merge;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

public class MaximizationMerge implements Merge<double[], RangeWithCentroids> {

    private final int dataSize;

    public MaximizationMerge(int dataSize) {
        this.dataSize = dataSize;
    }

    @Override
    public RangeWithCentroids merge(double[][] param) throws Exception {
        return new RangeWithCentroids(0, dataSize, param);
    }
}
