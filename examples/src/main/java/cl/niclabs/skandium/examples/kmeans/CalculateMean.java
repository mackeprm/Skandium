package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.XYPoint;

import java.util.List;
import java.util.function.Function;

public class CalculateMean implements Function<List<XYPoint>, XYPoint> {
    @Override
    public XYPoint apply(List<XYPoint> cluster) {
        int sumX = 0;
        int sumY = 0;
        for(XYPoint point : cluster) {
            sumX += point.getX();
            sumY += point.getY();
        }
        final double clusterSize = (double) cluster.size();
        final double meanX = 1.0d/clusterSize * sumX;
        final double meanY = 1.0d/clusterSize * sumY;
        return new XYPoint((int)meanX,(int)meanY);
    }
}
