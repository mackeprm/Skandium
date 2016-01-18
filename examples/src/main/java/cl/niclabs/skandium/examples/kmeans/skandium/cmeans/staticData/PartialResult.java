package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

public class PartialResult {
    double amount;
    double[] partialSums;
    double[][] membershipMatrix;

    public PartialResult(double[] localSums, double localAmount) {
        this.amount = localAmount;
        this.partialSums = localSums;
    }
}
