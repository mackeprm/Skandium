package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.sequential;

import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.Partial;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model.CandidateSet;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model.FilteringNodeVisitor;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model.KDTree;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model.KDTreeGenerator;
import cl.niclabs.skandium.examples.kmeans.util.Initialize;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cl.niclabs.skandium.examples.kmeans.util.DefaultArgs.getOrDefault;

public class SdKDSeqKmeans extends AbstractKmeans {

    public SdKDSeqKmeans(String[] args) throws UnknownHostException {
        super(args);
    }

    public SdKDSeqKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        super(config);
    }

    public static void main(String[] args) throws Exception {
        AbstractKmeans kmeans = new SdKDSeqKmeans(getOrDefault(args, "sd-kd-seq"));
        System.out.println(kmeans.toString());
        kmeans.run();
    }


    @Override
    public void run() throws Exception {
        long totalInit = System.currentTimeMillis();
        final List<Point> data = getDataFromFile();
        List<Point> clusterCenters = Initialize.randomClusterCentersFrom(data, numberOfClusterCenters, seed);

        //initialization:
        KDTreeGenerator kdTreeGenerator = new KDTreeGenerator();
        KDTree kdTree = kdTreeGenerator.generate(data);
        CandidateSet candidateSet = new CandidateSet(clusterCenters);
        long init = System.currentTimeMillis();

        for (int i = 0; i < this.numberOfIterations; i++) {
            FilteringNodeVisitor.visit(kdTree.getRoot(), candidateSet);
            List<Partial> partials = candidateSet.getPartials();
            final List<Point> newClusterCenters = new ArrayList<>(partials.size());
            newClusterCenters.addAll(partials.stream().filter(partial1 -> partial1.count > 0).map(partial -> calculateMeanOf(partial, dimension)).collect(Collectors.toList()));
            candidateSet = new CandidateSet(newClusterCenters);
        }


        long measure = System.currentTimeMillis() - init;
        System.out.println("time:" + measure + "[ms]");
        int index = 0;
        for (Point clusterCenter : candidateSet.getCentroids()) {
            System.out.println(index++ + " : " + clusterCenter);
        }
        storeMeasure(measure, System.currentTimeMillis() - totalInit);
    }


    private Point calculateMeanOf(Partial partial, int dimension) {
        final Double[] centroid = new Double[dimension];
        for (int i = 0; i < dimension; i++) {
            centroid[i] = partial.sum[i] / (double) partial.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}
