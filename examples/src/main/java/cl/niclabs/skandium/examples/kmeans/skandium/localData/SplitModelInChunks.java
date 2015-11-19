package cl.niclabs.skandium.examples.kmeans.skandium.localData;

import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;

public class SplitModelInChunks implements Split<Model, Chunk>{
    private int numberOfChunks;

    public SplitModelInChunks(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Chunk[] split(Model param) throws Exception {
        Chunk[] result = new Chunk[numberOfChunks];
        int dataSize = param.getData().size();
        int chunkLength = Math.floorDiv(dataSize, numberOfChunks);
        if (chunkLength < 1) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        int currentChunk = 0;
        for (int i = 0; i < dataSize; i += chunkLength) {
            result[currentChunk] = new Chunk(new ArrayList<>(param.getData().subList(i, Math.min(dataSize, i + chunkLength))),param.getClusterCenters());
            currentChunk++;
        }
        return result;
    }
}
