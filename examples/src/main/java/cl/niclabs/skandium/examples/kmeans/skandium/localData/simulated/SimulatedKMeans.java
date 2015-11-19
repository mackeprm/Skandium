package cl.niclabs.skandium.examples.kmeans.skandium.localData.simulated;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
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

public class SimulatedKMeans extends AbstractKmeans {
    public SimulatedKMeans(String name, String[] args) {
        super(name, args);
    }

    public static void main(String args[]) throws Exception {
        SimulatedKMeans kmeans = new SimulatedKMeans("Simulated K-Means", args);
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    public void run() throws Exception {
        final RandomDataSetGenerator randomDataSetGenerator = new RandomDataSetGenerator(dimension, seed);
        final List<Point> data = randomDataSetGenerator.generatePoints(numberOfValues);
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

        try (final Skandium skandium = new Skandium(numberOfThreads)) {

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
        }
    }
}
