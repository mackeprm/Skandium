package cl.niclabs.skandium.examples.kmeans.lloyd.hybridpartition;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.examples.kmeans.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.lloyd.Range;
import cl.niclabs.skandium.examples.kmeans.lloyd.RangeExpectationStep;
import cl.niclabs.skandium.examples.kmeans.lloyd.SplitInSubranges;
import cl.niclabs.skandium.examples.kmeans.lloyd.mapmaximization.MaximizationMerge;
import cl.niclabs.skandium.examples.kmeans.lloyd.mapmaximization.MaximizationStep;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.skeletons.HPKmeans;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Future;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SDHPKmeans extends AbstractKmeans {
    public SDHPKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SDHPKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SDHPKmeans(getOrDefault(args, "sd-hp"));
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

            HPKmeans<Range> kmeans = new HPKmeans<>(
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations),
                    new SplitInSubranges(numberOfThreads),
                    new RangeExpectationStep(data),
                    new HybridPartition(numberOfClusterCenters, data),
                    new MaximizationStep(),
                    new MaximizationMerge(data.size())
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
