package cl.niclabs.skandium.examples.kmeans.skandium.staticData.simulated;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.MergeRanges;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.RangeExpectationStep;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.SplitInSubranges;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.MaximizationMerge;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.MaximizationStep;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.Partition;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Pipe;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Future;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDSimKmeans extends AbstractKmeans {
    public SDSimKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDSimKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDSimKmeans(getOrDefault(args, "sd-sim"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long startpoint = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
        final Range startRange = new Range(0, data.size(), clusterCenters);

        try (final Skandium skandium = new Skandium(numberOfThreads)) {

            long init = System.currentTimeMillis();
            //Define Skeletons:
            for (int i = 0; i < numberOfIterations; i++) {
                //1. Expectation Step
                Skeleton<Range, Range> expectationSkeleton = new Map<>(
                        new SplitInSubranges(numberOfThreads),
                        new RangeExpectationStep(data),
                        new MergeRanges(data.size())
                );

                //2. Maximization Step
                Skeleton<Range, Range> maximizationSkeleton = new Map<>(
                        new Partition(numberOfClusterCenters, data),
                        new MaximizationStep(),
                        new MaximizationMerge(data.size())
                );
                //3. Piping the two skeletons:
                Skeleton<Range, Range> kmeansIteration = new Pipe<>(
                        expectationSkeleton, maximizationSkeleton
                );

                final Stream<Range, Range> stream = skandium.newStream(kmeansIteration);
                final Future<Range> resultRange = stream.input(startRange);
                clusterCenters = resultRange.get().clusters;
            }

            long parallelPartRuntime = System.currentTimeMillis() - init;
            System.out.println("time:" + parallelPartRuntime + "[ms]");
            int index = 0;
            for (Point clusterCenter : clusterCenters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
            storeMeasure(parallelPartRuntime, System.currentTimeMillis() - startpoint);
        }

    }
}
