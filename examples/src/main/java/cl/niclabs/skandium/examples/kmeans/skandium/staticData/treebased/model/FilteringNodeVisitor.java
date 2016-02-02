package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps.euclideanDistance;
import static cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps.nearestClusterCenterEuclidean;

public class FilteringNodeVisitor {

    public static void visit(KDNode node, CandidateSet candidateSet) {
        final List<Point> centroids = candidateSet.getCentroids();
        if (node.isLeaf()) {
            int clusterIndex = nearestClusterCenterEuclidean(node.getWeightedCentroid(), centroids).getClusterIndex();
            candidateSet.get(centroids.get(clusterIndex)).add(node.getWeightedCentroid());
        } else {
            final Point cellMidpoint = node.getMean();
            int clusterIndex = nearestClusterCenterEuclidean(cellMidpoint, centroids).getClusterIndex();

            final Point pivotCentroid = centroids.get(clusterIndex);
            final List<Point> pivotList = new ArrayList<>(centroids.size());
            pivotList.addAll(centroids);
            for (Point centroid : pivotList) {
                if (centroid != pivotCentroid && isFartherThan(centroid, pivotCentroid, node.getCell())) {
                    centroids.remove(centroid);
                }
            }
            if (centroids.size() == 1) {
                candidateSet.get(centroids.get(0)).add(node.getWeightedCentroid(), node.getCount());
            } else {
                //recursion:
                visit(node.getBelow(), candidateSet);
                visit(node.getAbove(), candidateSet);
            }

        }
    }

    private static boolean isFartherThan(Point centroid, Point pivotCentroid, BoundingBox cell) {
        int dimension = centroid.getDimension();

        // z = centroid
        // z* = pivotCentroid
        //u = z - z*
        Double u[] = centroid.subtract(pivotCentroid);
        Double vh[] = new Double[dimension];

        //v(H) = vh
        for (int i = 0; i < dimension; i++) {
            if (u[i] < 0) {
                vh[i] = cell.getMinima()[i];
            } else {
                vh[i] = cell.getMaxima()[i];
            }
        }

        final List<Double> vhValues = Arrays.asList(vh);
        return euclideanDistance(centroid.getValues(), vhValues) >= euclideanDistance(pivotCentroid.getValues(), vhValues);
    }

}
