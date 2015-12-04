package cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequential;

import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.*;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDSeqKmeans extends AbstractKmeans {

    public SDSeqKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDSeqKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDSeqKmeans(getOrDefault(args, "sd-seq"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }


    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
        java.util.Map<Integer, List<Point>> clustermap;

        long init = System.currentTimeMillis();

        for (int i = 0; i < this.numberOfIterations; i++) {
            clustermap = new HashMap<>();
            for (Point point : data) {
                ClusteredPoint clusteredPoint = ExpectationSteps.nearestClusterCenterEuclidean(point, clusterCenters);
                int clusterIndex = clusteredPoint.getClusterIndex();
                if (clustermap.get(clusterIndex) == null) {
                    clustermap.put(clusterIndex, new ArrayList<>());
                }
                clustermap.get(clusterIndex).add(clusteredPoint);
            }
            clusterCenters = new ArrayList<>(this.numberOfClusterCenters);
            for (java.util.Map.Entry<Integer, List<Point>> entry : clustermap.entrySet()) {
                clusterCenters.add(MaximizationSteps.calculateMeanOf(entry.getValue(), entry.getValue().get(0).getDimension()));
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
}
