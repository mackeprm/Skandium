package cl.niclabs.skandium.examples.kmeans.util;

public class ClusteredXYPoint extends XYPoint {

    int clusterIndex;

    public ClusteredXYPoint(double x, double y, int clusterIndex) {
        super(x, y);
        this.clusterIndex = clusterIndex;
    }

    public ClusteredXYPoint(XYPoint point, int clusterIndex) {
        this(point.getX(), point.getY(), clusterIndex);
    }

    public Integer getClusterIndex() {
        return clusterIndex;
    }

    @Override
    public String toString() {
        return "ClusteredXYPoint{" +
                super.toString() +
                "clusterIndex=" + clusterIndex +
                '}';
    }
}
