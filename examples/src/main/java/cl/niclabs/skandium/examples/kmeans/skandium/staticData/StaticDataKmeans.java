package cl.niclabs.skandium.examples.kmeans.skandium.staticData;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.GlobalIterationsConvergenceCriterion;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;
import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.skeletons.SMKmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class StaticDataKmeans extends AbstractKmeans {
    public StaticDataKmeans(String name, String[] args) {
        super(name, args);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new StaticDataKmeans("staticDataKmeans", args);
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

            Execute<Range, Range> maximizationStep = (param) -> {
                final java.util.Map<Integer, List<Point>> clusters = new HashMap<>(numberOfClusterCenters);
                for (int i = 0; i < param.clusterIndices.size(); i++) {
                    final Integer currentIndex = param.clusterIndices.get(i);
                    if (clusters.get(currentIndex) == null) {
                        clusters.put(currentIndex, new ArrayList<>());
                    }
                    clusters.get(currentIndex).add(data.get(i));
                }
                final List<Point> newClusterCenters = new ArrayList<>(numberOfClusterCenters);
                //TODO empty cluster handling
                newClusterCenters.addAll(clusters.entrySet().stream().map(entry -> MaximizationSteps.calculateMeanOf(entry.getValue(), entry.getValue().get(0).getDimension())).collect(Collectors.toList()));
                final Range result = new Range(0, data.size());
                result.clusters = newClusterCenters;
                return result;
            };

            SMKmeans<Range> kmeans = new SMKmeans<>(
                    new SplitInSubranges(numberOfThreads),
                    new RangeExpectationStep(data),
                    new MergeRanges(data.size()),
                    maximizationStep,
                    new GlobalIterationsConvergenceCriterion<>(numberOfIterations)
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
