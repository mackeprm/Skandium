package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class CalculateMean implements Execute<List<XYPoint>,XYPoint> {
    @Override
    public XYPoint execute(List<XYPoint> cluster) throws Exception {
        int sumX = 0;
        int sumY = 0;
        for(XYPoint point : cluster) {
            sumX += point.getX();
            sumY += point.getY();
        }
        final double meanX = 1.0d/(double)cluster.size() * sumX;
        final double meanY = 1.0d/(double)cluster.size() * sumY;
        return new XYPoint((int)meanX,(int)meanY);
    }
}
