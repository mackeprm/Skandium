package cl.niclabs.skandium.examples.kmeans.manual;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;

import java.util.Arrays;
import java.util.List;

public class Utils {

    static Range[] splitInEqualSubranges(List<Point> data, Integer numberOfThreads) {
        Range[] result = new Range[numberOfThreads];
        if (data.size() < numberOfThreads) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        double chunkSize = (double) data.size() / (double) numberOfThreads;
        double ceil = Math.ceil(chunkSize);
        final int chunkLength = (int) ceil;
        int currentChunk = 0;
        for (int i = 0; i < data.size(); i += chunkLength) {
            result[currentChunk] = new Range(i, Math.min(data.size(), i + chunkLength));
            currentChunk++;
        }
        return result;
    }

    static Point calculateMeanOf(Partial partial, int dimension) {
        final Double[] centroid = new Double[dimension];
        for (int i = 0; i < dimension; i++) {
            centroid[i] = partial.sum[i] / (double) partial.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}
