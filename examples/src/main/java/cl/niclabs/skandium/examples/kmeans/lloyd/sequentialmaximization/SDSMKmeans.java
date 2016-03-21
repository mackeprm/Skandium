package cl.niclabs.skandium.examples.kmeans.lloyd.sequentialmaximization;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.examples.kmeans.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.lloyd.MergeRanges;
import cl.niclabs.skandium.examples.kmeans.lloyd.Range;
import cl.niclabs.skandium.examples.kmeans.lloyd.RangeExpectationStep;
import cl.niclabs.skandium.examples.kmeans.lloyd.SplitInSubranges;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.skeletons.SMKmeans;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Future;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDSMKmeans extends AbstractKmeans {
    public SDSMKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDSMKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDSMKmeans(getOrDefault(args, "sd-sm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final List<Point> data = getDataFromFile();
            final List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
            final Range startRange = new Range(0, data.size(), clusterCenters);
            System.out.println("init: " + (System.currentTimeMillis() - totalInit) + "[ms]");

            SMKmeans<Range> kmeans = new SMKmeans<>(
                    new SplitInSubranges(numberOfThreads),
                    new RangeExpectationStep(data),
                    new MergeRanges(data.size()),
                    new SequentialRangeMaximizationStep(numberOfClusterCenters, data),
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations)
            );

            long init = System.currentTimeMillis();
            Stream<Range, Range> stream = skandium.newStream(kmeans);
            Future<Range> future = stream.input(startRange);

            Range result = future.get();

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            int index = 0;
            for (Point clusterCenter : result.clusters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
            storeMeasure(measure, System.currentTimeMillis() - totalInit);
        }
    }
}
