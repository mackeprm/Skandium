package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateSet {
    Map<Point, Partial> candidates;
    List<Point> centroids;

    public CandidateSet(List<Point> centroids) {
        this.centroids = centroids;
        int dimension = centroids.get(0).getDimension();
        candidates = new HashMap<>(centroids.size());
        for (Point centroid : centroids) {
            candidates.put(centroid, new Partial(dimension));
        }
    }

    public CandidateSet(List<Point> centroids, Map<Point, Partial> candidates) {
        this.centroids = centroids;
        this.candidates = candidates;
    }

    public List<Point> getCentroids() {
        return this.centroids;
    }

    public List<Partial> getPartials() {
        return new ArrayList<>(candidates.values());
    }

    public Partial get(Point centroid) {
        return candidates.get(centroid);
    }
}
