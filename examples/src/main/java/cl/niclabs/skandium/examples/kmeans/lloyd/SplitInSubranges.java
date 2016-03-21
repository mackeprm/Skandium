package cl.niclabs.skandium.examples.kmeans.lloyd;

import cl.niclabs.skandium.muscles.Split;

public class SplitInSubranges implements Split<Range, Range> {
    final int numberOfChunks;

    public SplitInSubranges(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @Override
    public Range[] split(Range param) throws Exception {
        final Range[] result = new Range[numberOfChunks];
        final int dataSize = param.right;
        if (dataSize < numberOfChunks) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        double chunkSize = (double) dataSize / (double) numberOfChunks;
        double ceil = Math.ceil(chunkSize);
        final int chunkLength = (int) ceil;
        int currentChunk = 0;
        for (int i = 0; i < dataSize; i += chunkLength) {
            result[currentChunk] = new Range(i, Math.min(dataSize, i + chunkLength), param.clusters);
            currentChunk++;
        }
        return result;
    }
}
