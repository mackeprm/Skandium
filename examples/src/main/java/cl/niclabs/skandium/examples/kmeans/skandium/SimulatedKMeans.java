package cl.niclabs.skandium.examples.kmeans.skandium;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.*;
import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Pipe;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

public class SimulatedKMeans {
    public static void main(String args[]) throws Exception {
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


        final RandomDataSetGenerator randomDataSetGenerator = new RandomDataSetGenerator(dimension, seed);
        final List<Point> data = randomDataSetGenerator.generatePoints(numberOfValues);
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

        Skandium skandium = new Skandium(numberOfThreads);

        long init = System.currentTimeMillis();
        //Define Skeletons:
        for (int i = 0; i < numberOfIterations; i++) {
            //1. Expectation Step
            Skeleton<List<Point>, Collection<ClusteredPoint>> expectationSkeleton = new Map<>
                    (new SplitInEqualChunks(numberOfThreads), new FindNearestClusterCenter(clusterCenters), new MergeChunksToSet());
            //2. Maximization Step
            Skeleton<Collection<ClusteredPoint>, List<Point>> maximizationSkeleton = new Map<>(
                    new SplitInClusters(numberOfClusterCenters), new CalculateMean(), new MergeClusterCenters()
            );
            //3. Piping the two skeletons:
            Skeleton<List<Point>, List<Point>> kmeansIteration = new Pipe<>(
                    expectationSkeleton, maximizationSkeleton
            );

            final Stream<List<Point>, List<Point>> stream = skandium.newStream(kmeansIteration);
            final Future<List<Point>> newClusterCenters = stream.input(data);
            clusterCenters = newClusterCenters.get();
        }

        System.out.println("time:" + (System.currentTimeMillis() - init) + "[ms]");
        int index = 0;
        for (Point clusterCenter : clusterCenters) {
            System.out.println(index++ + " : " + clusterCenter);
        }
        skandium.shutdown();
    }
}
