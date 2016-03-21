package cl.niclabs.skandium.examples.kmeans.cmeans;

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
        List<Point> newClusterCenters = new ArrayList<>(numberOfClusters);
        double globalAmount[] = new double[numberOfClusters];
        double globalVectors[][] = new double[numberOfClusters][dimension];
        for (int k = 0; k < numberOfClusters; k++) {
            for (PartialResult partialResult : param) {
                globalAmount[k] += partialResult.localAmount[k];
                for (int d = 0; d < dimension; d++) {
                    globalVectors[k][d] += partialResult.localVectors[k][d];
                }
            }
        }
        for (int k = 0; k < numberOfClusters; k++) {
            newClusterCenters.add(new Point(getCentroidFor(globalAmount[k], globalVectors[k])));
        }
        return new FuzzyRange(0, numberOfValues, param[0].membershipMatrix, newClusterCenters);
    }

    private List<Double> getCentroidFor(double globalSum, double[] globalVector) {
        List<Double> result = new ArrayList<>(dimension);
        for (double value : globalVector) {
            result.add(value / globalSum);
        }
        return result;
    }
}
