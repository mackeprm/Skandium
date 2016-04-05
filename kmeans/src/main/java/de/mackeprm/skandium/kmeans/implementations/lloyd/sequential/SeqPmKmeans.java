package de.mackeprm.skandium.kmeans.implementations.lloyd.sequential;

import de.mackeprm.skandium.kmeans.model.ExpectationSteps;
import de.mackeprm.skandium.kmeans.model.SeedingStrategies;
import de.mackeprm.skandium.kmeans.model.modelutils.Partial;
import de.mackeprm.skandium.kmeans.util.DefaultArgs;
import de.mackeprm.skandium.kmeans.util.configuration.AbstractKmeans;
import de.mackeprm.skandium.kmeans.util.configuration.KMeansRunConfiguration;
import de.mackeprm.skandium.kmeans.util.io.DataSourceReader;
import de.mackeprm.skandium.kmeans.util.io.Printer;

import java.net.UnknownHostException;
import java.util.Arrays;

public class SeqPmKmeans extends AbstractKmeans {


    public SeqPmKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SeqPmKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SeqPmKmeans(DefaultArgs.getOrDefault(args, "seq-pm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }


    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final double[][] data = DataSourceReader.initializeFromFileSource(inputFile, numberOfValues, dimension);
        double[][] centroids = SeedingStrategies.randomFrom(data, seed, numberOfClusterCenters, dimension);
        Partial[] partials;

        long init = System.currentTimeMillis();

        for (int i = 0; i < this.numberOfIterations; i++) {
            partials = new Partial[numberOfClusterCenters];
            //Expectation:
            for (double[] point : data) {
                int nearestClusterIndex = ExpectationSteps.nearestClusterEuclidean(point, centroids);
                if (partials[nearestClusterIndex] == null) {
                    partials[nearestClusterIndex] = new Partial(this.dimension);
                }
                partials[nearestClusterIndex].add(point);
            }
            //Maximization:
            for (int c = 0; c < numberOfClusterCenters; c++) {
                if (partials[c] != null) {
                    final Partial currentPartial = partials[c];
                    for (int d = 0; d < dimension; d++) {
                        centroids[c][d] = currentPartial.sum[d] / (float) currentPartial.count;
                    }
                } else {
                    centroids[c] = new double[dimension];
                    //hm.
                    Arrays.fill(centroids[c], 0d);
                }
            }
        }

        long measure = System.currentTimeMillis() - init;
        System.out.println("time:" + measure + "[ms]");
        Printer.printClusterCenters(centroids, dimension);
        storeMeasure(measure, System.currentTimeMillis() - totalInit);
    }

}
