package de.mackeprm.skandium.kmeans.implementations.kmedian;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.MMKmeans;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;
import de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.MaximizationMerge;
import de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.MaximizationSplit;
import de.mackeprm.skandium.kmeans.implementations.lloyd.parallelutils.RangeExpectationMerge;
import de.mackeprm.skandium.kmeans.implementations.lloyd.parallelutils.RangeExpectationSplit;
import de.mackeprm.skandium.kmeans.model.GlobalIterationsConvergenceCriterion;
import de.mackeprm.skandium.kmeans.model.SeedingStrategies;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;
import de.mackeprm.skandium.kmeans.util.configuration.AbstractKmeans;
import de.mackeprm.skandium.kmeans.util.configuration.KMeansRunConfiguration;
import de.mackeprm.skandium.kmeans.util.io.DataSourceReader;
import de.mackeprm.skandium.kmeans.util.io.Printer;

import java.net.UnknownHostException;
import java.util.concurrent.Future;

import static de.mackeprm.skandium.kmeans.util.DefaultArgs.getOrDefault;

public class SDMMKmedian extends AbstractKmeans {

    public SDMMKmedian(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDMMKmedian(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDMMKmedian(getOrDefault(args, "kmd-mm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final double[][] data = DataSourceReader.initializeFromFileSource(inputFile, numberOfValues, dimension);
            final double[][] centroids = SeedingStrategies.randomFrom(data, seed, numberOfClusterCenters, dimension);
            final RangeWithCentroids startRange = new RangeWithCentroids(0, data.length, centroids);

            //1. Expectation Step
            Skeleton<RangeWithCentroids, RangeWithAssignments> expectationSkeleton = new Map<>(
                    new RangeExpectationSplit(numberOfThreads),
                    new KmedianRangeExpectationStep(data),
                    new RangeExpectationMerge(data.length)
            );

            //2. Maximization Step
            Skeleton<RangeWithAssignments, RangeWithCentroids> maximizationSkeleton = new Map<>(
                    new MaximizationSplit(numberOfClusterCenters, data),
                    new MMKmedianMaximizationStep(dimension),
                    new MaximizationMerge(data.length)
            );

            MMKmeans<RangeWithCentroids> kmeans = new MMKmeans<>(
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations),
                    expectationSkeleton,
                    maximizationSkeleton
            );


            long init = System.currentTimeMillis();
            Stream<RangeWithCentroids, RangeWithCentroids> stream = skandium.newStream(kmeans);
            Future<RangeWithCentroids> future = stream.input(startRange);

            RangeWithCentroids result = future.get();

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            Printer.printClusterCenters(result.centroids, dimension);
            storeMeasure(measure, System.currentTimeMillis() - totalInit);
        }
    }
}
