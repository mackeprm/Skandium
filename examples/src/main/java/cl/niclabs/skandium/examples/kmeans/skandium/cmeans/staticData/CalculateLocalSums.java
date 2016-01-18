package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class CalculateLocalSums implements Execute<FuzzyRange, PartialResult> {

    private final int dimension;
    private final List<Point> data;
    private final double fuzzynessIndex;

    public CalculateLocalSums(int dimension, List<Point> data, double fuzzynessIndex) {
        this.dimension = dimension;
        this.data = data;
        this.fuzzynessIndex = fuzzynessIndex;
    }

    @Override
    public PartialResult execute(FuzzyRange range) {

        double localAmount = 0.0;
        double[] localSums = new double[dimension];
        for (int k = 0; k < range.clusters.size(); k++) {
            for (int i = range.left; i < range.right; i++) {
                List<Double> currentPointValues = data.get(i).getValues();
                for (int d = 0; d < dimension; d++) {
                    localSums[d] += Math.pow(range.localMembershipMatrix[k][i], this.fuzzynessIndex) * currentPointValues.get(d);
                }
                localAmount += Math.pow(range.localMembershipMatrix[k][i], this.fuzzynessIndex);
            }
        }
        final PartialResult result = new PartialResult(localSums, localAmount);
        result.membershipMatrix = range.localMembershipMatrix;
        return result;
    }
}
