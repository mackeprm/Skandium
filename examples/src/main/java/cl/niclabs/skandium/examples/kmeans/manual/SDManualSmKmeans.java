package cl.niclabs.skandium.examples.kmeans.manual;

import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDManualSmKmeans extends AbstractKmeans {
    public SDManualSmKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDManualSmKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDManualSmKmeans(getOrDefault(args, "sd-manual-sm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        final Range[] ranges = Utils.splitInEqualSubranges(data, numberOfThreads);
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

        long init = System.currentTimeMillis();


        for (int i = 0; i < this.numberOfIterations; i++) {
            List<AssignmentThread> threads = new ArrayList<>(numberOfThreads);
            for (int k = 0; k < numberOfThreads; k++) {
                threads.add(new AssignmentThread(data, ranges[k], clusterCenters));
                threads.get(k).start();
            }

            final List<Integer> globalClusterIndices = new ArrayList<>(data.size());
            for (AssignmentThread thread : threads) {
                thread.join();
                globalClusterIndices.addAll(thread.getClusterIndices());
            }

            final java.util.Map<Integer, List<Point>> clusters = new HashMap<>(numberOfClusterCenters);
            for (int j = 0; j < globalClusterIndices.size(); j++) {
                final Integer currentIndex = globalClusterIndices.get(j);
                if (clusters.get(currentIndex) == null) {
                    clusters.put(currentIndex, new ArrayList<>());
                }
                clusters.get(currentIndex).add(data.get(j));
            }

            final List<Point> newClusterCenters = new ArrayList<>(numberOfClusterCenters);
            newClusterCenters.addAll(clusters.entrySet().stream().map(entry -> MaximizationSteps.calculateMeanOf(entry.getValue(), entry.getValue().get(0).getDimension())).collect(Collectors.toList()));
            clusterCenters = newClusterCenters;
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
