package cl.niclabs.skandium.examples.kmeans.skandium.localData.simulated;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SplitInEqualChunks implements Split<List<Point>, Collection<Point>> {
    private int numberOfChunks;

    public SplitInEqualChunks(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Point>[] split(List<Point> param) throws Exception {
        Collection<Point>[] result;
        result = (List<Point>[]) new ArrayList<?>[numberOfChunks];
        int chunkLength = Math.floorDiv(param.size(), numberOfChunks);
        if (chunkLength < 1) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        int currentChunk = 0;
        for (int i = 0; i < param.size(); i += chunkLength) {
            result[currentChunk] = (new ArrayList<>(param.subList(i, Math.min(param.size(), i + chunkLength))));
            currentChunk++;
        }
        return result;
    }
}
