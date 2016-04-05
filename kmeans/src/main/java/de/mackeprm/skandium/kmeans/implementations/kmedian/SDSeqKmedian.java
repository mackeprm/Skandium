package de.mackeprm.skandium.kmeans.implementations.kmedian;

import de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.KChunksMaximizationFunctions;
import de.mackeprm.skandium.kmeans.model.ExpectationSteps;
import de.mackeprm.skandium.kmeans.model.MaximizationSteps;
import de.mackeprm.skandium.kmeans.model.SeedingStrategies;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;
import de.mackeprm.skandium.kmeans.util.configuration.AbstractKmeans;
import de.mackeprm.skandium.kmeans.util.configuration.KMeansRunConfiguration;
import de.mackeprm.skandium.kmeans.util.io.DataSourceReader;
import de.mackeprm.skandium.kmeans.util.io.Printer;

import java.net.UnknownHostException;

import static de.mackeprm.skandium.kmeans.util.DefaultArgs.getOrDefault;

public class SDSeqKmedian extends AbstractKmeans {

    public SDSeqKmedian(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDSeqKmedian(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDSeqKmedian(getOrDefault(args, "kmd-seq"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }


    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final double[][] data = DataSourceReader.initializeFromFileSource(inputFile, numberOfValues, dimension);
        double[][] centroids = SeedingStrategies.randomFrom(data, seed, numberOfClusterCenters, dimension);
        RangeWithAssignments globalRange = new RangeWithAssignments(0, data.length);

        long init = System.currentTimeMillis();

        for (int i = 0; i < this.numberOfIterations; i++) {
            for (int j = 0; j < data.length; j++) {
                globalRange.assignments[j] = ExpectationSteps.nearestClusterManhattan(data[j], centroids);
            }

            //step 1: convert assignments into clusters
            double[][][] split = KChunksMaximizationFunctions.split(globalRange, numberOfClusterCenters, data);
            //step 2: convert clusters into centroids
            for (int j = 0; j < numberOfClusterCenters; j++) {
                centroids[j] = MaximizationSteps.calculateMedianOf(split[j], dimension);
            }
        }
        long measure = System.currentTimeMillis() - init;
        System.out.println("time:" + measure + "[ms]");
        Printer.printClusterCenters(centroids, dimension);
        storeMeasure(measure, System.currentTimeMillis() - totalInit);
    }
}