package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.examples.kmeans.util.DataFileReader;
import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Pipe;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

public class KMeans {
    public static void main(String args[]) throws Exception {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        int numberOfClusterCenters = 3;
        int numberOfIterations = 10;
        String filename = "";

        if (args.length != 0) {
            //TODO parse input parameters
        }

        System.out.println("Computing K-Means Clustering numberOfThreads=" + numberOfThreads + " input-data=" + filename + ", "
                + " number of Cluster Centers:" + numberOfClusterCenters);

        DataFileReader reader = new DataFileReader();
        List<XYPoint> data = reader.readData(filename);
        List<XYPoint> clusterCenters = assignRandomClusterCentersFrom(data, numberOfClusterCenters);
        Skandium skandium = new Skandium(numberOfThreads);

        //Define Skeletons:
        for (int i = 0; i < numberOfIterations; i++) {
            //1. Expectation Step
            Skeleton<List<XYPoint>, Collection<ClusteredXYPoint>> expectationSkeleton = new Map<>
                    (new SplitInUniformChunks(numberOfThreads), new FindNearestClusterCenter(clusterCenters), new MergeChunksToSet());
            //2. Maximization Step
            Skeleton<Collection<ClusteredXYPoint>, List<XYPoint>> maximizationSkeleton = new Map<>(
                    new SplitInClusters(numberOfClusterCenters), new CalculateMean(), new MergeClusterCenters()
            );
            //3. Piping the two skeletons:
            Skeleton<List<XYPoint>, List<XYPoint>> kmeansIteration = new Pipe<>(
                    expectationSkeleton, maximizationSkeleton
            );
            Stream<List<XYPoint>, List<XYPoint>> stream = skandium.newStream(kmeansIteration);
            Future<List<XYPoint>> newClusterCenters = stream.input(data);
            clusterCenters = newClusterCenters.get();

            /*Stream<List<XYPoint>, Collection<ClusteredXYPoint>> expectationStream = skandium.newStream(expectationSkeleton);
            Future<Collection<ClusteredXYPoint>> clusteredData = expectationStream.input(data);
            Stream<Collection<ClusteredXYPoint>, List<XYPoint>> maximizationStream = skandium.newStream(maximizationSkeleton);
            Future<List<XYPoint>> newClusterCenters = maximizationStream.input(clusteredData.get());
            clusterCenters = newClusterCenters.get();*/
        }
    }

    private static List<XYPoint> assignRandomClusterCentersFrom(final List<XYPoint> data, int numberOfClusterCenters) {
        final List<XYPoint> result = new ArrayList<>(numberOfClusterCenters);
        final Random random = new Random();
        for (int i = 0; i < numberOfClusterCenters; i++) {
            result.add(data.get(random.nextInt(data.size())));
        }
        return result;
    }
}
