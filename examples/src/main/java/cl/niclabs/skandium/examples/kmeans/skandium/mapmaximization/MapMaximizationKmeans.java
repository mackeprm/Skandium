package cl.niclabs.skandium.examples.kmeans.skandium.mapmaximization;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.*;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.skeletons.MMKmeans;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.List;
import java.util.concurrent.Future;

public class MapMaximizationKmeans extends AbstractKmeans {

    public MapMaximizationKmeans(String name, String[] args) {
        super(name, args);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new MapMaximizationKmeans("native K-Means (map maximization)", args);
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        try (final Skandium skandium = new Skandium(numberOfThreads)) {

            //1. Expectation Step
            Skeleton<Model, ClusteredModel> expectationSkeleton = new Map<>(
                    new SplitModelInChunks(numberOfThreads),
                    new ChunkExpectationStep(),
                    new MergeChunksToClusteredModel()
            );

            //2. Maximization Step
            Skeleton<ClusteredModel, Model> maximizationSkeleton = new Map<>(
                    new PartitionInCluster(),
                    new CalculateMean(),
                    new MergeToModel()
            );

            MMKmeans<Model> kmeans = new MMKmeans<>(
                    new GlobalIterationsConvergenceCriterion(numberOfIterations),
                    expectationSkeleton,
                    maximizationSkeleton
            );

            final RandomDataSetGenerator randomDataSetGenerator = new RandomDataSetGenerator(dimension, seed);
            final List<Point> data = randomDataSetGenerator.generatePoints(numberOfValues);
            final List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
            final Model startModel = new Model(data, clusterCenters);


            long init = System.currentTimeMillis();
            Stream<Model, Model> stream = skandium.newStream(kmeans);
            Future<Model> future = stream.input(startModel);

            Model result = future.get();

            System.out.println("time:" + (System.currentTimeMillis() - init) + "[ms]");
            int index = 0;
            for (Point clusterCenter : result.getClusterCenters()) {
                System.out.println(index++ + " : " + clusterCenter);
            }
        }
    }
}
