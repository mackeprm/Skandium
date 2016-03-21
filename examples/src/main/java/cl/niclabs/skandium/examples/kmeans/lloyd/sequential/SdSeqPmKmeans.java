package cl.niclabs.skandium.examples.kmeans.lloyd.sequential;

import cl.niclabs.skandium.examples.kmeans.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.lloyd.partialmerge.Partial;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.*;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SdSeqPmKmeans extends AbstractKmeans {

    public SdSeqPmKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SdSeqPmKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SdSeqPmKmeans(getOrDefault(args, "sd-seq-pm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }


    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
        Map<Integer, Partial> clustermap;

        long init = System.currentTimeMillis();

        for (int i = 0; i < this.numberOfIterations; i++) {
            clustermap = new HashMap<>();
            for (Point point : data) {
                ClusteredPoint clusteredPoint = ExpectationSteps.nearestClusterCenterEuclidean(point, clusterCenters);
                int clusterIndex = clusteredPoint.getClusterIndex();
                if (clustermap.get(clusterIndex) == null) {
                    clustermap.put(clusterIndex, new Partial(dimension));
                }
                clustermap.get(clusterIndex).add(clusteredPoint);
            }
            clusterCenters = new ArrayList<>(this.numberOfClusterCenters);
            for (java.util.Map.Entry<Integer, Partial> entry : clustermap.entrySet()) {
                clusterCenters.add(calculateMeanOf(entry.getValue(), dimension));
            }
        }


        long measure = System.currentTimeMillis() - init;
        System.out.println("time:" + measure + "[ms]");
        int index = 0;
        for (Point clusterCenter : clusterCenters) {
            System.out.println(index++ + " : " + clusterCenter);
        }
        storeMeasure(measure, System.currentTimeMillis() - totalInit);
    }

    private Point calculateMeanOf(Partial partial, int dimension) {
        final Double[] centroid = new Double[dimension];
        for (int i = 0; i < dimension; i++) {
            centroid[i] = partial.sum[i] / (double) partial.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}
