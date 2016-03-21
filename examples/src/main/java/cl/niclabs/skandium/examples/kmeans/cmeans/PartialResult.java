package cl.niclabs.skandium.examples.kmeans.cmeans;

public class PartialResult {
    double[] localAmount;
    double[][] localVectors;
    double[][] membershipMatrix;

    public PartialResult(double[] localAmount, double[][] localVectors) {
        this.localAmount = localAmount;
        this.localVectors = localVectors;
    }
}
