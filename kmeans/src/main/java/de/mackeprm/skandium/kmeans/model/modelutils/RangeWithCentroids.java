package de.mackeprm.skandium.kmeans.model.modelutils;

public class RangeWithCentroids extends Range {
    public double[][] centroids;

    public RangeWithCentroids(int left, int right) {
        super(left, right);
    }

    public RangeWithCentroids(int left, int right, double[][] centroids) {
        super(left, right);
        this.centroids = centroids;
    }
}
