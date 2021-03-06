package cl.niclabs.skandium.examples.kmeans.treebased.randomdecomposition;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.examples.kmeans.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.lloyd.partialmerge.Partial;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.treebased.model.*;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SdKDRDKmeans extends AbstractKmeans {

    public SdKDRDKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SdKDRDKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SdKDRDKmeans(getOrDefault(args, "sd-kd-rd"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }

    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        try (final Skandium skandium = new Skandium(numberOfThreads)) {
            final List<Point> data = getDataFromFile();
            List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

            //initialization:
            int dataSize = data.size();
            if (dataSize < numberOfThreads) {
                throw new IllegalStateException("input was smaller than number of chunks");
            }

            double chunkSize = (double) dataSize / (double) numberOfThreads;
            double ceil = Math.ceil(chunkSize);
            final int chunkLength = (int) ceil;


            KDTreeGenerator kdTreeGenerator = new KDTreeGenerator();
            final KDTree trees[] = new KDTree[numberOfThreads];
            //Split input data
            for (int i = 0; i < numberOfThreads; i++) {
                final int currentChunkStart = i * chunkLength;
                trees[i] = kdTreeGenerator.generate(data.subList(currentChunkStart, Math.min(dataSize, currentChunkStart + chunkLength)));
            }

            final Skeleton<CandidateSet, CandidateSet> partialMergeKmeans = new Map<>(
                    input -> {
                        PartialTree[] partialTrees = new PartialTree[trees.length];
                        for (int i = 0; i < trees.length; i++) {
                            partialTrees[i] = new PartialTree(trees[i], input);
                        }
                        return partialTrees;
                    },
                    partialTree -> {
                        final KDNode root = partialTree.getKdTree().getRoot();
                        final CandidateSet candidateSet = partialTree.getLocalCandidateSet();
                        FilteringNodeVisitor.visit(root, candidateSet);
                        return candidateSet;
                    },
                    partialCandidateSets -> {
                        final CandidateSet globalPartials = new CandidateSet(partialCandidateSets[0].getCentroids());
                        for (CandidateSet partialCandidateSet : partialCandidateSets) {
                            final List<Point> centroids = partialCandidateSet.getCentroids();
                            for (Point centroid : centroids) {
                                globalPartials.get(centroid).add(partialCandidateSet.get(centroid));
                            }
                        }
                        //merge
                        final List<Partial> resultPartials = globalPartials.getPartials();
                        final List<Point> newClusterCenters = new ArrayList<>(resultPartials.size());
                        newClusterCenters.addAll(resultPartials.stream().filter(p -> p.count > 0).map(partial -> calculateMeanOf(partial, dimension)).collect(Collectors.toList()));
                        return new CandidateSet(newClusterCenters);
                    }
            );

            CandidateSet start = new CandidateSet(clusterCenters);
            Stream<CandidateSet, CandidateSet> stream = skandium.newStream(partialMergeKmeans);

            long init = System.currentTimeMillis();
            for (int i = 0; i < numberOfIterations; i++) {
                final CandidateSet result = stream.input(start).get();
                start = result;
            }

            long measure = System.currentTimeMillis() - init;
            System.out.println("time:" + measure + "[ms]");
            int index = 0;
            for (Point clusterCenter : start.getCentroids()) {
                System.out.println(index++ + " : " + clusterCenter);
            }
            storeMeasure(measure, System.currentTimeMillis() - totalInit);

        }
    }

    private Point calculateMeanOf(Partial partial, int dimension) {
        final Double[] centroid = new Double[dimension];
        for (int i = 0; i < dimension; i++) {
            centroid[i] = partial.sum[i] / (double) partial.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}