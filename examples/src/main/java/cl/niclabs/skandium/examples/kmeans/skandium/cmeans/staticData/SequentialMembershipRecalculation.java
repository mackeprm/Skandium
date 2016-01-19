package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;

public class SequentialMembershipRecalculation implements Execute<FuzzyRange, FuzzyRange> {

    private final List<Point> data;
    private final double fuzzynessIndex;

    public SequentialMembershipRecalculation(List<Point> data, double fuzzynessIndex) {
        this.data = data;
        this.fuzzynessIndex = fuzzynessIndex;
    }

    @Override
    public FuzzyRange execute(FuzzyRange param) throws Exception {
        return new FuzzyRange(param.left, param.right, updateMembershipMatrix(param.localMembershipMatrix, param.clusters, data), param.clusters);
    }

    private double[][] updateMembershipMatrix(double[][] membershipMatrix, List<Point> clusterCenters, List<Point> data) {
        for (int i = 0; i < data.size(); i++) {
            for (int k = 0; k < clusterCenters.size(); k++) {
                membershipMatrix[k][i] = calculateNewMembershipValue(data.get(i), k, clusterCenters);
            }
        }
        return membershipMatrix;
    }

    private double calculateNewMembershipValue(Point point, int currentClusterCenterIndex, List<Point> clusterCenters) {
        double t, sum;
        sum = 0.0;
        double exponent = 2 / (this.fuzzynessIndex - 1);
        for (int k = 0; k < clusterCenters.size(); k++) {
            t = ExpectationSteps.euclideanDistance(point.getValues(), clusterCenters.get(currentClusterCenterIndex).getValues()) / ExpectationSteps.euclideanDistance(point.getValues(), clusterCenters.get(k).getValues());
            t = Math.pow(t, exponent);
            sum += t;
        }
        double result = 1.0 / sum;
        return Double.isNaN(result) ? 0.0 : result;
    }


}
