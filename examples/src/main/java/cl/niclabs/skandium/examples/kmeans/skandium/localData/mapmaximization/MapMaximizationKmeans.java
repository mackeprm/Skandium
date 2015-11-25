package cl.niclabs.skandium.examples.kmeans.skandium.localData.mapmaximization;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.*;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.skeletons.MMKmeans;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Future;

public class MapMaximizationKmeans extends AbstractKmeans {

    public MapMaximizationKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        String[] defaultArgs;
        if (args == null || args.length == 0) {
            defaultArgs = new String[1];
            defaultArgs[0] = "dd-mm";
        } else {
            defaultArgs = args;
        }
        AbstractKmeans kmeans = new MapMaximizationKmeans(defaultArgs);
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
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations),
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

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            storeMeasure(measure);
            int index = 0;
            for (Point clusterCenter : result.getClusterCenters()) {
                System.out.println(index++ + " : " + clusterCenter);
            }
        }
    }
}
