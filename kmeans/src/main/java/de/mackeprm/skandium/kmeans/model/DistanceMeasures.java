package de.mackeprm.skandium.kmeans.model;

public class DistanceMeasures {

    public static double euclideanDistance(double[] source, double[] destination) {
        double sum = 0;
        for (int i = 0; i < source.length; i++) {
            final double dp = source[i] - destination[i];
            sum += dp * dp;
        }
        return Math.sqrt(sum);
    }
}
