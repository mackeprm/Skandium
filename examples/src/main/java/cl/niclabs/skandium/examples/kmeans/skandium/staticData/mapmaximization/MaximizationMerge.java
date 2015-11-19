package cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.muscles.Merge;

import java.util.Arrays;

public class MaximizationMerge implements Merge<Point, Range> {

    private final int dataSize;

    public MaximizationMerge(int dataSize) {
        this.dataSize = dataSize;
    }

    @Override
    public Range merge(Point[] param) throws Exception {
        return new Range(0, dataSize, Arrays.asList(param));
    }
}
