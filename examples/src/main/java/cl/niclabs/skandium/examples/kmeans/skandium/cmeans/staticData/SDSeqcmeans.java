package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;


import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDSeqcmeans extends AbstractKmeans {

    final double fuzzynessIndex = 2;

    public SDSeqcmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public SDSeqcmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDSeqcmeans(getOrDefault(args, "fcm-sd-seq"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        //INIT
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        List<Point> clusterCenters = null;
        double[][] membershipMatrix = CmeansUtils.randomizedMembershipValues(numberOfClusterCenters, numberOfValues, new Random(this.seed));

        long init = System.currentTimeMillis();
        // ITERATION
        for (int iteration = 0; iteration < numberOfIterations; iteration++) {
            clusterCenters = recalculateClusterCenters(membershipMatrix, data);
            membershipMatrix = updateMembershipMatrix(membershipMatrix, clusterCenters, data);
        }

        long measure = System.currentTimeMillis() - init;
        System.out.println("time:" + measure + "[ms]");
        int index = 0;
        for (Point clusterCenter : clusterCenters) {
            System.out.println(index++ + " : " + clusterCenter);
        }
        storeMeasure(measure, System.currentTimeMillis() - totalInit);
    }

    private double[][] updateMembershipMatrix(double[][] membershipMatrix, List<Point> clusterCenters, List<Point> data) {
        for (int i = 0; i < numberOfValues; i++) {
            for (int k = 0; k < numberOfClusterCenters; k++) {
                membershipMatrix[k][i] = calculateNewMembershipValue(data.get(i), k, clusterCenters);
            }
        }
        return membershipMatrix;
    }

    private double calculateNewMembershipValue(Point point, int currentClusterCenterIndex, List<Point> clusterCenters) {
        double t, sum;
        sum = 0.0;
        double exponent = 2 / (this.fuzzynessIndex - 1);
        for (int k = 0; k < numberOfClusterCenters; k++) {
            t = ExpectationSteps.euclideanDistance(point.getValues(), clusterCenters.get(currentClusterCenterIndex).getValues()) / ExpectationSteps.euclideanDistance(point.getValues(), clusterCenters.get(k).getValues());
            t = Math.pow(t, exponent);
            sum += t;
        }
        double result = 1.0 / sum;
        return Double.isNaN(result) ? 0.0 : result;
    }


    private List<Point> recalculateClusterCenters(final double[][] membershipMatrix, List<Point> data) {
        List<Point> clusterCenters = new ArrayList<>(numberOfClusterCenters);
        for (int k = 0; k < numberOfClusterCenters; k++) {
            double amount = 0.0;
            double[] summedUpPartials = new double[dimension];
            for (int i = 0; i < numberOfValues; i++) {
                List<Double> currentPointValues = data.get(i).getValues();
                for (int d = 0; d < dimension; d++) {
                    summedUpPartials[d] += Math.pow(membershipMatrix[k][i], this.fuzzynessIndex) * currentPointValues.get(d);
                }
                amount += Math.pow(membershipMatrix[k][i], this.fuzzynessIndex);
            }
            //New Centroid
            List<Double> newCentroidValues = new ArrayList<>(dimension);
            for (int d = 0; d < dimension; d++) {
                newCentroidValues.add(summedUpPartials[d] / amount);
            }
            clusterCenters.add(new Point(newCentroidValues));
        }

        return clusterCenters;
    }
}
