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

    static double manhattanDistance(double[] source, double[] destination) {
        //int distance = Math.abs(x1-x0) + Math.abs(y1-y0);
        double sum = 0;
        for (int i = 0; i < source.length; i++) {
            final double blockDistance = Math.abs(source[i] - destination[i]);
            sum += blockDistance;
        }
        return sum;
    }
}
