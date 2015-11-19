package cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.RangeExpectationStep;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.SplitInSubranges;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.MaximizationMerge;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.MaximizationStep;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.skeletons.HPKmeans;

import java.util.List;
import java.util.concurrent.Future;

public class SHPKmeans extends AbstractKmeans {
    public SHPKmeans(String[] args) {
        super("Kmeans (native, static, hybrid partition)", args);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SHPKmeans(args);
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final RandomDataSetGenerator randomDataSetGenerator = new RandomDataSetGenerator(dimension, seed);
            final List<Point> data = randomDataSetGenerator.generatePoints(numberOfValues);
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

            System.out.println("time:" + (System.currentTimeMillis() - init) + "[ms]");
            int index = 0;
            for (Point clusterCenter : result.clusters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
        }
    }
}
