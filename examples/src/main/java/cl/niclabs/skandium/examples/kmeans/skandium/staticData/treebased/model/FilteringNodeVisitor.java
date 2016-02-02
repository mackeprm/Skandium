package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps.euclideanDistance;
import static cl.niclabs.skandium.examples.kmeans.model.ExpectationSteps.nearestClusterCenterEuclidean;

public class FilteringNodeVisitor {

    public static void visit(KDNode node, CandidateSet candidateSet) {
        final List<Point> localCentroids = new ArrayList<>(candidateSet.getCentroids());
        if (node.isLeaf()) {
            int clusterIndex = nearestClusterCenterEuclidean(node.getWeightedCentroid(), localCentroids).getClusterIndex();
            candidateSet.get(localCentroids.get(clusterIndex)).add(node.getWeightedCentroid());
        } else {
            final Point cellMidpoint = node.getMean();
            int clusterIndex = nearestClusterCenterEuclidean(cellMidpoint, localCentroids).getClusterIndex();

            final Point pivotCentroid = localCentroids.get(clusterIndex);
            final List<Point> pivotList = new ArrayList<>(localCentroids.size());
            pivotList.addAll(localCentroids);
            for (Point centroid : pivotList) {
                if (centroid != pivotCentroid && isFartherThan(centroid, pivotCentroid, node.getCell())) {
                    localCentroids.remove(centroid);
                }
            }
            if (localCentroids.size() == 1) {
                candidateSet.get(localCentroids.get(0)).add(node.getWeightedCentroid(), node.getCount());
            } else {
                //recursion:
                final CandidateSet nextCandiates = new CandidateSet(localCentroids, candidateSet.candidates);
                visit(node.getBelow(), nextCandiates);
                visit(node.getAbove(), nextCandiates);
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
