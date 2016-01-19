package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class CalculateLocalSums implements Execute<FuzzyRange, PartialResult> {

    private final int dimension;
    private final List<Point> data;
    private final double fuzzynessIndex;
    private final int numberOfClusterCenters;

    public CalculateLocalSums(int dimension, List<Point> data, double fuzzynessIndex, int numberOfClusterCenters) {
        this.dimension = dimension;
        this.data = data;
        this.fuzzynessIndex = fuzzynessIndex;
        this.numberOfClusterCenters = numberOfClusterCenters;
    }

    @Override
    public PartialResult execute(FuzzyRange range) {
        double localAmounts[] = new double[numberOfClusterCenters];
        double localVectors[][] = new double[numberOfClusterCenters][dimension];
        for (int k = 0; k < numberOfClusterCenters; k++) {
            for (int i = range.left; i < range.right; i++) {
                List<Double> currentPointValues = data.get(i).getValues();
                for (int d = 0; d < dimension; d++) {
                    localVectors[k][d] += Math.pow(range.localMembershipMatrix[k][i], this.fuzzynessIndex) * currentPointValues.get(d);
                }
                localAmounts[k] += Math.pow(range.localMembershipMatrix[k][i], this.fuzzynessIndex);
            }
        }
        final PartialResult result = new PartialResult(localAmounts, localVectors);
        result.membershipMatrix = range.localMembershipMatrix;
        return result;
    }
}
