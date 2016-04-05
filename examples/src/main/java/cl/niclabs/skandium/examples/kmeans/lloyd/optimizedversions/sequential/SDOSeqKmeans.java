package cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.sequential;

import cl.niclabs.skandium.examples.kmeans.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.util.DataSourceReader;
import cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.util.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;

import java.net.UnknownHostException;
import java.util.Arrays;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDOSeqKmeans extends AbstractKmeans {


    public SDOSeqKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDOSeqKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDOSeqKmeans(getOrDefault(args, "sdo-seq"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }


    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final double[][] data = DataSourceReader.initializeFromFileSource(inputFile, numberOfValues, dimension);
        double[][] centroids = Initialize.randomFrom(data, seed, numberOfClusterCenters, dimension);
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
        for (int i = 0; i < numberOfClusterCenters; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < dimension; j++) {
                System.out.print(centroids[i][j] + ", ");
            }
            System.out.print("\n");
        }
        storeMeasure(measure, System.currentTimeMillis() - totalInit);
    }

}
