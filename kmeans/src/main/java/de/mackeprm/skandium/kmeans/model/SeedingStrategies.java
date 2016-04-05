package de.mackeprm.skandium.kmeans.model;

import java.util.Arrays;
import java.util.Random;

public class SeedingStrategies {

    public static double[][] randomFrom(double[][] data, long seed, int numberOfClusterCenters, int dimension) {
        final Random random = new Random(seed);
        final double[][] centroids = new double[numberOfClusterCenters][];
        for (int i = 0; i < numberOfClusterCenters; i++) {
            centroids[i] = Arrays.copyOf(data[random.nextInt(data.length)], dimension);
        }
        return centroids;
    }
}
