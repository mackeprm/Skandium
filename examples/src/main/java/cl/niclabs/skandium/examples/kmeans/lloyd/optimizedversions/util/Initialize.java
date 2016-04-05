package cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.util;

import java.util.Arrays;
import java.util.Random;

public class Initialize {

    public static double[][] randomFrom(double[][] data, long seed, int numberOfClusterCenters, int dimension) {
        final Random random = new Random(seed);
        final double[][] centroids = new double[numberOfClusterCenters][];
        for (int i = 0; i < numberOfClusterCenters; i++) {
            centroids[i] = Arrays.copyOf(data[random.nextInt(data.length)], dimension);
        }
        return centroids;
    }
}
