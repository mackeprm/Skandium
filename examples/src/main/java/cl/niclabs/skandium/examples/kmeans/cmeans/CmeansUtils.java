package cl.niclabs.skandium.examples.kmeans.cmeans;

import java.util.Random;

public class CmeansUtils {

    public static double[][] randomizedMembershipValues(int numberOfClusterCenters, int numberOfValues, Random random) {
        double[][] membershipMatrix = new double[numberOfClusterCenters][numberOfValues];
        for (int i = 0; i < numberOfValues; i++) {
            double[] randomMembershipValues = getRandomMemberShipValues(numberOfClusterCenters, random);
            for (int k = 0; k < numberOfClusterCenters; k++) {
                membershipMatrix[k][i] = randomMembershipValues[k];
            }
        }
        return membershipMatrix;
    }

    private static double[] getRandomMemberShipValues(int numberOfClusterCenters, Random random) {
        double[] values = new double[numberOfClusterCenters];
        double min = 0.0, sum = 0.0;
        for (int i = 0; i < numberOfClusterCenters - 1; i++) {
            double nextDouble = min + (random.nextDouble() * (1.0 - min));
            sum += nextDouble - min;
            values[i] = nextDouble - min;
            min = nextDouble;
        }
        values[numberOfClusterCenters - 1] = 1.0 - sum;
        return values;
    }

}
