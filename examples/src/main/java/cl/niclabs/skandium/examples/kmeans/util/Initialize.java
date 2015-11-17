package cl.niclabs.skandium.examples.kmeans.util;


import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Initialize {
    public static List<Point> randomClusterCentersFrom(final List<Point> data, int numberOfClusterCenters, long seed) {
        final List<Point> result = new ArrayList<>(numberOfClusterCenters);
        final Random random = new Random(seed);
        for (int i = 0; i < numberOfClusterCenters; i++) {
            result.add(data.get(random.nextInt(data.size())));
        }
        return result;
    }
}
