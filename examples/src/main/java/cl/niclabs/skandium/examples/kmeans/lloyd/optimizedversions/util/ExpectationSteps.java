package cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.util;

public class ExpectationSteps {

    public static int nearestClusterEuclidean(double[] point, double[][] centroids) {
        double distance = Double.MAX_VALUE;
        int nearestClusterCenterIndex = Integer.MAX_VALUE;
        for (int i = 0; i < centroids.length; i++) {
            double currentDistance = DistanceMeasures.euclideanDistance(point, centroids[i]);
            if (currentDistance < distance) {
                distance = currentDistance;
                nearestClusterCenterIndex = i;
            }
        }
        return nearestClusterCenterIndex;
    }

}
