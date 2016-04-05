package de.mackeprm.skandium.kmeans.implementations.lloyd.partialmerge;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;
import de.mackeprm.skandium.kmeans.implementations.lloyd.parallelutils.RangeExpectationSplit;
import de.mackeprm.skandium.kmeans.model.SeedingStrategies;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithCentroids;
import de.mackeprm.skandium.kmeans.util.configuration.AbstractKmeans;
import de.mackeprm.skandium.kmeans.util.configuration.KMeansRunConfiguration;
import de.mackeprm.skandium.kmeans.util.io.DataSourceReader;
import de.mackeprm.skandium.kmeans.util.io.Printer;

import java.net.UnknownHostException;

import static de.mackeprm.skandium.kmeans.util.DefaultArgs.getOrDefault;

public class PmKmeans extends AbstractKmeans {
    public PmKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public PmKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new PmKmeans(getOrDefault(args, "pm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final double[][] data = DataSourceReader.initializeFromFileSource(inputFile, numberOfValues, dimension);
            final double[][] centroids = SeedingStrategies.randomFrom(data, seed, numberOfClusterCenters, dimension);
            RangeWithCentroids startRange = new RangeWithCentroids(0, data.length, centroids);

            Skeleton<RangeWithCentroids, RangeWithCentroids> partialMergeKmeans = new Map<>(
                    new RangeExpectationSplit(numberOfThreads),
                    new PartialExpectationStep(data, dimension),
                    new PartialMerge(data.length, this.dimension, numberOfClusterCenters)
            );

            long init = System.currentTimeMillis();
            Stream<RangeWithCentroids, RangeWithCentroids> stream = skandium.newStream(partialMergeKmeans);
            for (int i = 0; i < numberOfIterations; i++) {
                @SuppressWarnings("UnnecessaryLocalVariable") final RangeWithCentroids result = stream.input(startRange).get();
                startRange = result;
            }

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            Printer.printClusterCenters(startRange.centroids, dimension);
            storeMeasure(measure, System.currentTimeMillis() - totalInit);
        }
    }

}