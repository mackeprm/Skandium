package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;

import java.util.ArrayList;
import java.util.List;

public class ClusteredChunk {
    private List<ClusteredPoint> points;

    public List<ClusteredPoint> getPoints() {
        return points;
    }

    public ClusteredChunk(int size) {
        this.points = new ArrayList<>(size);
    }

    public void add(ClusteredPoint point) {
        points.add(point);
    }

}
