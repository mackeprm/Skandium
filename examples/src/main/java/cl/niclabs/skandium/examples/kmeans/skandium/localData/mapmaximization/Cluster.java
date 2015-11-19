package cl.niclabs.skandium.examples.kmeans.skandium.localData.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class Cluster {

    private List<Point> points;

    public Cluster(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }
}
