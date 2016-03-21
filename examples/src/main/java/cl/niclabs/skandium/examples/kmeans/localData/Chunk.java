package cl.niclabs.skandium.examples.kmeans.localData;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Chunk {

    private List<Point> clusterCenters;
    private List<Point> points;

    public Chunk(ArrayList<Point> points, List<Point> clusterCenters) {
        this.points = points;
        this.clusterCenters = clusterCenters;
    }

    public Collection<Point> getPoints() {
        return points;
    }

    public int getSize() {
        return points.size();
    }

    public List<Point> getClusterCenters() {
        return clusterCenters;
    }
}
