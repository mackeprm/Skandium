package cl.niclabs.skandium.examples.kmeans.manual;


import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
        final Range startRange = new Range(0, data.size());

        final Range[] ranges = splitInSubranges(data);

        List<Thread> threads = new ArrayList<>(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final int currentThread = i;
            threads.add(new Thread(() -> {
                final Range localRange = ranges[currentThread];

                final Map<Integer, Partial> partials = new HashMap<>();
                for (int j = localRange.left; j < localRange.right; j++) {
                    Point currentPoint = data.get(j);
                    int clusterIndex = ExpectationSteps.nearestClusterCenterEuclidean(currentPoint, clusterCenters).getClusterIndex();
                    if (partials.get(clusterIndex) == null) {
                        partials.put(clusterIndex, new Partial(currentPoint.getDimension()));
                    }
                    partials.get(clusterIndex).add(currentPoint);
                }
                //TODO return: sync with global object.
            }));
        }

        long init = System.currentTimeMillis();


        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
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

}
