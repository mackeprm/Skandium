package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.ClusteredXYPoint;
import cl.niclabs.skandium.examples.kmeans.util.DataFileReader;
import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.impl.forkjoin.ForkJoinMap;
import cl.niclabs.skandium.impl.sequential.SequentialPipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class KMeans {
    public static void main(String args[]) throws Exception {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        int numberOfClusterCenters = 3;
        int numberOfIterations = 10;
        //TODO system independend file storage. Classpath-Resources?
        String filename = "D:\\Projekte\\Java\\Skandium\\examples\\src\\main\\java\\cl\\niclabs\\skandium\\examples\\kmeans\\old-faithful.csv";

        if (args.length != 0) {
            //TODO parse input parameters
        }

        System.out.println("Computing K-Means Clustering numberOfThreads=" + numberOfThreads + " input-data=" + filename + ", "
                + " number of Cluster Centers:" + numberOfClusterCenters);

        DataFileReader reader = new DataFileReader();
        List<XYPoint> data = reader.readData(filename);
        List<XYPoint> clusterCenters = assignRandomClusterCentersFrom(data, numberOfClusterCenters);

        //Define Skeletons:
        for (int i = 0; i < numberOfIterations; i++) {
            //1. Expectation Step
            Function<List<XYPoint>, Collection<ClusteredXYPoint>> expectationSkeleton = new ForkJoinMap<>
                    (new SplitInUniformChunks(numberOfThreads), new FindNearestClusterCenter(clusterCenters), new MergeChunksToSet());
            //2. Maximization Step
            Function<Collection<ClusteredXYPoint>, List<XYPoint>> maximizationSkeleton = new ForkJoinMap<>(
                    new SplitInClusters(numberOfClusterCenters), new CalculateMean(), new MergeClusterCenters()
            );
            //3. Piping the two skeletons:
            Function<List<XYPoint>, List<XYPoint>> kmeansIteration = new SequentialPipe<>(
                    expectationSkeleton, maximizationSkeleton
            );

            clusterCenters = kmeansIteration.apply(data);
            int index = 0;
            for(XYPoint clusterCenter : clusterCenters) {
                System.out.println(index++ + " : " + clusterCenter);
            }
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
