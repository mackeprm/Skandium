package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.List;

public class RecalculateGlobalCentroids implements Merge<PartialResult, FuzzyRange> {
    final int numberOfValues;
    final int numberOfClusters;
    final int dimension;

    public RecalculateGlobalCentroids(int numberOfValues, int numberOfClusters, int dimension) {
        this.numberOfValues = numberOfValues;
        this.numberOfClusters = numberOfClusters;
        this.dimension = dimension;
    }

    @Override
    public FuzzyRange merge(PartialResult[] param) throws Exception {
        final List<Point> newClusterCenters = new ArrayList<>(numberOfClusters);
        final double[] globalSum = new double[param.length];
        double globalAmount = 0.0;
        for (PartialResult partialResult : param) {
            final double[] partialSums = partialResult.partialSums;
            for (int d = 0; d < partialSums.length; d++) {
                globalSum[d] += partialSums[d];
            }
            globalAmount += partialResult.amount;
        }
        for (int k = 0; k < numberOfClusters; k++) {
            final List<Double> values = new ArrayList<>(dimension);
            for (int d = 0; d < dimension; d++) {
                values.add(globalSum[d] / globalAmount);
            }
            newClusterCenters.add(new Point(values));
        }
        FuzzyRange result = new FuzzyRange(0, numberOfValues, param[0].membershipMatrix);
        result.clusters = newClusterCenters;
        return result;
    }
}
