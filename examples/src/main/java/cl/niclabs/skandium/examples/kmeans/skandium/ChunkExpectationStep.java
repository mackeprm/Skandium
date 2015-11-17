package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class ChunkExpectationStep implements Execute<Chunk, ClusteredChunk> {

    @Override
    public ClusteredChunk execute(Chunk param) throws Exception {
        final ClusteredChunk result = new ClusteredChunk(param.getSize());
        for (final Point point : param.getPoints()) {
            result.add(assignSinglePoint(point,param.getClusterCenters()));
        }
        return result;
    }

    private ClusteredPoint assignSinglePoint(Point point, List<Point> clusterCenters) {
        double distance = Double.MAX_VALUE;
        int nearestClusterCenterIndex = Integer.MAX_VALUE;
        for (int i = 0; i < clusterCenters.size(); i++) {
            double currentDistance = calculateDistanceBetween(point.getValues(), clusterCenters.get(i).getValues());
            if (currentDistance < distance) {
                distance = currentDistance;
                nearestClusterCenterIndex = i;
            }
        }
        return new ClusteredPoint(nearestClusterCenterIndex, point.getValues());
    }

    private double calculateDistanceBetween(List<Double> source, List<Double> destination) {
        double sum = 0;
        for (int i = 0; i < source.size(); i++) {
            final double dp = source.get(i) - destination.get(i);
            sum += dp * dp;
        }
        return Math.sqrt(sum);
    }
}
