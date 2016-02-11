package cl.niclabs.skandium.examples.kmeans.manual;


import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cl.niclabs.skandium.examples.kmeans.manual.Utils.calculateMeanOf;
import static cl.niclabs.skandium.examples.kmeans.manual.Utils.splitInEqualSubranges;
import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDManualPmKMeans extends AbstractKmeans {
    public SDManualPmKMeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDManualPmKMeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDManualPmKMeans(getOrDefault(args, "sd-manual-pm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        final Range[] ranges = splitInEqualSubranges(data, numberOfThreads);
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

        long init = System.currentTimeMillis();
        for (int i = 0; i < this.numberOfIterations; i++) {
            List<PartialUpdateThread> threads = new ArrayList<>(numberOfThreads);
            for (int k = 0; k < numberOfThreads; k++) {
                threads.add(new PartialUpdateThread(data, ranges[k], clusterCenters));
                threads.get(k).start();
            }

            Map<Integer, Partial> globalPartials = new HashMap<>();
            for (PartialUpdateThread thread : threads) {
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
}
