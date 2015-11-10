package cl.niclabs.skandium.examples.kmeans;

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

    @Override
    public Collection<Collection<Point>> apply(List<Point> param) {
        List<Collection<Point>> result = new ArrayList<>();
        int chunkLength = Math.floorDiv(param.size(), numberOfChunks);
        if (chunkLength < 1) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        for(int i=0;i<param.size();i += chunkLength) {
            result.add(new ArrayList<>(param.subList(i, Math.min(param.size(), i + chunkLength))));
        }
        return result;
    }
}
