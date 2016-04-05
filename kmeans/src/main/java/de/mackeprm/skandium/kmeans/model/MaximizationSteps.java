package de.mackeprm.skandium.kmeans.model;

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
}
