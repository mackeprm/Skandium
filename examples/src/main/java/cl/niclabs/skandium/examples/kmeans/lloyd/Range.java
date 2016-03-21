package cl.niclabs.skandium.examples.kmeans.lloyd;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class Range {
    public int left;
    public int right;
    public List<Integer> clusterIndices;
    public List<Point> clusters;

    public Range(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public Range(int left, int right, List<Point> clusters) {
        this.left = left;
        this.right = right;
        this.clusters = clusters;
    }
}
