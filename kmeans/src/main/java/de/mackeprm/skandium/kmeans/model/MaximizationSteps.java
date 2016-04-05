package de.mackeprm.skandium.kmeans.model;

import java.util.Arrays;

public class MaximizationSteps {
    public static double[] calculateMeanOf(final double[][] points, final int dimension) {
        final double[] centroid = new double[dimension];
        for (final double[] p : points) {
            for (int i = 0; i < centroid.length; i++) {
                centroid[i] += p[i];
            }
        }
        for (int i = 0; i < centroid.length; i++) {
            centroid[i] /= points.length;
        }
        return centroid;
    }

    public static double[] calculateMedianOf(double[][] input, int dimension) {
        final double[] result = new double[dimension];
        final double[][] dimensionValues = new double[dimension][input.length];
        //transpose:
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < dimension; j++) {
                dimensionValues[j][i] = input[i][j];
            }
        }

        for (int i = 0; i < dimension; i++) {
            result[i] = medianOf(dimensionValues[i]);
        }

        return result;
    }

    static double medianOf(double[] doubles) {
        double median;
        int size = doubles.length;
        Arrays.sort(doubles);
        if (size % 2 == 0) {
            //the median is then usually defined to be the mean of the two middle values [1] [2] (the median of {3, 5, 7, 9} is (5 + 7) / 2 = 6)
            median = (doubles[((size / 2) - 1)] + doubles[(size / 2)]) / 2.0;
        } else {
            median = doubles[(size / 2)];
        }
        return median;
    }

}
