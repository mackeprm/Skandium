package cl.niclabs.skandium.examples.kmeans.manual;


import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDManualKMeans extends AbstractKmeans {
    public SDManualKMeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDManualKMeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDManualKMeans(getOrDefault(args, "sd-manual"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        final Range[] ranges = splitInSubranges(data);
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

        long init = System.currentTimeMillis();
        for (int i = 0; i < this.numberOfIterations; i++) {
            List<ManualAssignmentStep> threads = new ArrayList<>(numberOfThreads);
            for (int k = 0; k < numberOfThreads; k++) {
                threads.add(new ManualAssignmentStep(data, ranges[k], clusterCenters));
                threads.get(k).start();
            }

            Map<Integer, Partial> globalPartials = new HashMap<>();
            for (ManualAssignmentStep thread : threads) {
                thread.join();
                //update Partials
                for (Map.Entry<Integer, Partial> entry : thread.getPartials().entrySet()) {
                    if (globalPartials.get(entry.getKey()) == null) {
                        globalPartials.put(entry.getKey(), new Partial(dimension));
                    }
                    globalPartials.get(entry.getKey()).add(entry.getValue());
                }
            }

            final List<Point> newClusterCenters = new ArrayList<>(globalPartials.size());
            newClusterCenters.addAll(globalPartials.values().stream().map(partial -> calculateMeanOf(partial, dimension)).collect(Collectors.toList()));
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

    private Range[] splitInSubranges(List<Point> data) {
        Range[] result = new Range[numberOfThreads];
        if (data.size() < numberOfThreads) {
            throw new IllegalStateException("input was smaller than number of chunks");
        }
        double chunkSize = (double) data.size() / (double) numberOfThreads;
        double ceil = Math.ceil(chunkSize);
        final int chunkLength = (int) ceil;
        int currentChunk = 0;
        for (int i = 0; i < data.size(); i += chunkLength) {
            result[currentChunk] = new Range(i, Math.min(data.size(), i + chunkLength));
            currentChunk++;
        }
        return result;
    }

    private Point calculateMeanOf(Partial partial, int dimension) {
        final Double[] centroid = new Double[dimension];
        for (int i = 0; i < dimension; i++) {
            centroid[i] = partial.sum[i] / (double) partial.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}
