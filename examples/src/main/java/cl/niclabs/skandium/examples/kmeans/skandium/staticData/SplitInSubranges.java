package cl.niclabs.skandium.examples.kmeans.skandium.staticData;

import cl.niclabs.skandium.muscles.Split;

public class SplitInSubranges implements Split<Range, Range> {
    final int numberOfChunks;

    public SplitInSubranges(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @Override
    public Range[] split(Range param) throws Exception {
        final Range[] result;
        final int dataSize = param.right;
        if (dataSize % numberOfChunks == 0) {
            result = new Range[numberOfChunks];
        } else {
            result = new Range[numberOfChunks + 1];
        }
        final int chunkLength = Math.floorDiv(dataSize, numberOfChunks);
        if (chunkLength < 1) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        int currentChunk = 0;
        for (int i = 0; i < dataSize; i += chunkLength) {
            result[currentChunk] = new Range(i, Math.min(dataSize, i + chunkLength), param.clusters);
            currentChunk++;
        }
        return result;
    }
}
