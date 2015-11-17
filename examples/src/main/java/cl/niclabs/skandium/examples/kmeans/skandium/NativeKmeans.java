package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.skeletons.Kmeans;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NativeKmeans {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        int numberOfClusterCenters = 2;
        int numberOfIterations = 10;
        int dimension = 3;
        int numberOfValues = 40;
        long seed = 4711l;

        if (args.length != 0) {
            numberOfThreads = Integer.parseInt(args[0]);
            numberOfValues = Integer.parseInt(args[1]);
            numberOfClusterCenters = Integer.parseInt(args[2]);
            dimension = Integer.parseInt(args[3]);
            numberOfIterations = Integer.parseInt(args[4]);
        }


        System.out.println("K-Means: Threads=" + numberOfThreads +
                        " n=" + numberOfValues +
                        " k=" + numberOfClusterCenters +
                        " d=" + dimension +
                        " i=" + numberOfIterations
        );

        Kmeans<Model> kmeans = new Kmeans<> (
                new SplitModelInChunks(numberOfThreads),
                new ChunkExpectationStep(),
                new MergeChunksToClusteredModel(),
                new CalculateNewModelFromClusters(),
                new GlobalIterationsConvergenceCriterion(numberOfIterations)
        );

        final RandomDataSetGenerator randomDataSetGenerator = new RandomDataSetGenerator(dimension, seed);
        final List<Point> data = randomDataSetGenerator.generatePoints(numberOfValues);
        final List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);
        final Model startModel = new Model(data, clusterCenters);

        final Skandium skandium = new Skandium(numberOfThreads);
        long init = System.currentTimeMillis();
        Stream<Model, Model> stream = skandium.newStream(kmeans);
        Future<Model> future = stream.input(startModel);

        Model result = future.get();

        System.out.println("time:" + (System.currentTimeMillis() - init) + "[ms]");
        int index = 0;
        for (Point clusterCenter : result.getClusterCenters()) {
            System.out.println(index++ + " : " + clusterCenter);
        }
        skandium.shutdown();
    }

}
