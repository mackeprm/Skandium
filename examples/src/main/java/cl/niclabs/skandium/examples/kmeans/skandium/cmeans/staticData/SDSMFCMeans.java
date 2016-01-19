package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.skeletons.SMKmeans;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDSMFCMeans extends AbstractKmeans {
    final double fuzzynessIndex = 2;

    public SDSMFCMeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public SDSMFCMeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDSMFCMeans(getOrDefault(args, "fcm-sd-sm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final List<Point> data = getDataFromFile();
            double[][] membershipMatrix = CmeansUtils.randomizedMembershipValues(numberOfClusterCenters, numberOfValues, new Random(this.seed));

            final FuzzyRange startRange = new FuzzyRange(0, data.size(), membershipMatrix, null);

            SMKmeans<FuzzyRange> kmeans = new SMKmeans<>(
                    new SplitInFuzzySubranges(numberOfThreads),
                    new CalculateLocalSums(dimension, data, fuzzynessIndex, numberOfClusterCenters),
                    new RecalculateGlobalCentroids(data.size(), numberOfClusterCenters, dimension),
                    new SequentialMembershipRecalculation(data, fuzzynessIndex),
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations)
            );

            long init = System.currentTimeMillis();
            Stream<FuzzyRange, FuzzyRange> stream = skandium.newStream(kmeans);
            Future<FuzzyRange> future = stream.input(startRange);

            FuzzyRange result = future.get();

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            int index = 0;
            for (Point clusterCenter : result.clusters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
            storeMeasure(measure, System.currentTimeMillis() - totalInit);
        }
    }
}
