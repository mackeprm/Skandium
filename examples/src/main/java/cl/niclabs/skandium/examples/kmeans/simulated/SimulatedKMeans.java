package cl.niclabs.skandium.examples.kmeans.simulated;

import cl.niclabs.skandium.examples.kmeans.*;
import cl.niclabs.skandium.examples.kmeans.model.ClusteredPoint;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.impl.forkjoin.ForkJoinMap;
import cl.niclabs.skandium.impl.sequential.SequentialPipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class SimulatedKMeans {
    public static void main(String args[]) throws Exception {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        int numberOfClusterCenters = 3;
        int numberOfIterations = 10;
        int dimension = 3;
        int numberOfValues = 100;

        if (args.length != 0) {
            //TODO parse input parameters
        }

        System.out.println("K-Means: Threads=" + numberOfThreads +
                " k=" + numberOfClusterCenters +
                " i=" + numberOfIterations +
                " d=" + dimension +
                " n=" + numberOfValues);

        RandomDataSetGenerator randomDataSetGenerator = new RandomDataSetGenerator(dimension);
        List<Point> data = randomDataSetGenerator.generatePoints(numberOfValues);
        List<Point> clusterCenters = assignRandomClusterCentersFrom(data, numberOfClusterCenters);

        //Define Skeletons:
        for (int i = 0; i < numberOfIterations; i++) {
            //1. Expectation Step
            Function<List<Point>, Collection<ClusteredPoint>> expectationSkeleton = new ForkJoinMap<>
                    (new SplitInEqualChunks(numberOfThreads), new FindNearestClusterCenter(clusterCenters), new MergeChunksToSet());
            //2. Maximization Step
            Function<Collection<ClusteredPoint>, List<Point>> maximizationSkeleton = new ForkJoinMap<>(
                    new SplitInClusters(numberOfClusterCenters), new CalculateMean(), new MergeClusterCenters()
            );
            //3. Piping the two skeletons:
            Function<List<Point>, List<Point>> kmeansIteration = new SequentialPipe<>(
                    expectationSkeleton, maximizationSkeleton
            );

            clusterCenters = kmeansIteration.apply(data);
            int index = 0;
            for (Point clusterCenter : clusterCenters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
        }
    }

    private static List<Point> assignRandomClusterCentersFrom(final List<Point> data, int numberOfClusterCenters) {
        final List<Point> result = new ArrayList<>(numberOfClusterCenters);
        final Random random = new Random();
        for (int i = 0; i < numberOfClusterCenters; i++) {
            result.add(data.get(random.nextInt(data.size())));
        }
        return result;
    }
}
