package de.mackeprm.skandium.kmeans.implementations.lloyd.parallelutils;

import cl.niclabs.skandium.muscles.Split;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;

public class RangeExpectationSplit implements Split<RangeWithCentroids, RangeWithCentroids> {
    final int numberOfChunks;

    public RangeExpectationSplit(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @Override
    public RangeWithCentroids[] split(RangeWithCentroids param) throws Exception {
        final RangeWithCentroids[] result = new RangeWithCentroids[numberOfChunks];
        final int dataSize = param.right;
        if (dataSize < numberOfChunks) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        double chunkSize = (double) dataSize / (double) numberOfChunks;
        double ceil = Math.ceil(chunkSize);
        final int chunkLength = (int) ceil;
        int currentChunk = 0;
        for (int i = 0; i < dataSize; i += chunkLength) {
            result[currentChunk] = new RangeWithCentroids(i, Math.min(dataSize, i + chunkLength), param.centroids);
            currentChunk++;
        }
        return result;
    }
}
