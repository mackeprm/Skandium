package cl.niclabs.skandium.examples.kmeans.cmeans;

import cl.niclabs.skandium.muscles.Split;

public class SplitInFuzzySubranges implements Split<FuzzyRange, FuzzyRange> {
    final int numberOfChunks;

    public SplitInFuzzySubranges(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @Override
    public FuzzyRange[] split(FuzzyRange param) throws Exception {
        final FuzzyRange[] result = new FuzzyRange[numberOfChunks];
        final int dataSize = param.right;
        if (dataSize < numberOfChunks) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        double chunkSize = (double) dataSize / (double) numberOfChunks;
        double ceil = Math.ceil(chunkSize);
        final int chunkLength = (int) ceil;
        int currentChunk = 0;
        for (int i = 0; i < dataSize; i += chunkLength) {
            final int left = i;
            //give the full matrix because it wont be edited:
            final int right = Math.min(dataSize, i + chunkLength);
            result[currentChunk] = new FuzzyRange(left, right, param.localMembershipMatrix, param.clusters);
            currentChunk++;
        }
        return result;
    }
}
