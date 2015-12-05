package cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.SplitInSubranges;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.net.UnknownHostException;
import java.util.List;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDPMKmeans extends AbstractKmeans {
    public SDPMKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDPMKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDPMKmeans(getOrDefault(args, "sd-pm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final List<Point> data = getDataFromFile();
            final List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
            Range startRange = new Range(0, data.size(), clusterCenters);

            Skeleton<Range, Range> partialMergeKmeans = new Map<>(
                    new SplitInSubranges(numberOfThreads),
                    new AssignmentAndPartialSumCalculation(data),
                    new PartialMerge(data.size(), this.dimension)
            );

            long init = System.currentTimeMillis();
            Stream<Range, Range> stream = skandium.newStream(partialMergeKmeans);
            for (int i = 0; i < numberOfIterations; i++) {
                final Range result = stream.input(startRange).get();
                startRange = result;
            }

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            int index = 0;
            for (Point clusterCenter : startRange.clusters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
            storeMeasure(measure, System.currentTimeMillis() - totalInit);
        }
    }

}