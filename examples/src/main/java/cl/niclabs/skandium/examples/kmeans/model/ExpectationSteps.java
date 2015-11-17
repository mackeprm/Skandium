package cl.niclabs.skandium.examples.kmeans.model;

import java.util.List;
import java.util.function.BiFunction;

public class ExpectationSteps {

    public static ClusteredPoint nearestClusterCenterEuclidean(Point point, List<Point> clusterCenters) {
        return ExpectationSteps.nearestClusterCenter(point, clusterCenters, ExpectationSteps::euclideanDistance);
    }

    public static ClusteredPoint nearestClusterCenterManhattan(Point point, List<Point> clusterCenters) {
        return ExpectationSteps.nearestClusterCenter(point, clusterCenters, ExpectationSteps::manhattanDistance);
    }

    private static ClusteredPoint nearestClusterCenter(Point point, List<Point> clusterCenters, BiFunction<List<Double>, List<Double>, Double> distanceMeasure) {
        double distance = Double.MAX_VALUE;
        int nearestClusterCenterIndex = Integer.MAX_VALUE;
        for (int i = 0; i < clusterCenters.size(); i++) {
            double currentDistance = distanceMeasure.apply(point.getValues(), clusterCenters.get(i).getValues());
            if (currentDistance < distance) {
                distance = currentDistance;
                nearestClusterCenterIndex = i;
            }
        }
        return new ClusteredPoint(nearestClusterCenterIndex, point.getValues());
    }


    static double euclideanDistance(List<Double> source, List<Double> destination) {
        double sum = 0;
        for (int i = 0; i < source.size(); i++) {
            final double dp = source.get(i) - destination.get(i);
            sum += dp * dp;
        }
        return Math.sqrt(sum);
    }

    static double manhattanDistance(List<Double> source, List<Double> destination) {
        //int distance = Math.abs(x1-x0) + Math.abs(y1-y0);
        double sum = 0;
        for (int i = 0; i < source.size(); i++) {
            final double blockDistance = Math.abs(source.get(i) - destination.get(i));
            sum += blockDistance;
        }
        return sum;
    }
}
