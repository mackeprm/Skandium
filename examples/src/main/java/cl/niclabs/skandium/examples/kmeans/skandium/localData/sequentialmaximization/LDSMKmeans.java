package cl.niclabs.skandium.examples.kmeans.skandium.localData.sequentialmaximization;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.ChunkExpectationStep;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.MergeChunksToClusteredModel;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.Model;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.SplitModelInChunks;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.skeletons.SMKmeans;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Future;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class LDSMKmeans extends AbstractKmeans {


    public LDSMKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new LDSMKmeans(getOrDefault(args, "ld-sm"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {

            SMKmeans<Model> kmeans = new SMKmeans<>(
                    new SplitModelInChunks(numberOfThreads),
                    new ChunkExpectationStep(),
                    new MergeChunksToClusteredModel(),
                    new CalculateNewModelFromClusters(),
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations)
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
            int index = 0;
            for (Point clusterCenter : result.getClusterCenters()) {
                System.out.println(index++ + " : " + clusterCenter);
            }
            storeMeasure(measure, System.currentTimeMillis() - totalInit);
        }
    }

}
