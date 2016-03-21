package cl.niclabs.skandium.examples.kmeans.cmeans;

import cl.niclabs.skandium.examples.kmeans.lloyd.Range;
import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class FuzzyRange extends Range {

    final double[][] localMembershipMatrix;

    public FuzzyRange(int left, int right, final double[][] localMembershipMatrix, List<Point> clusters) {
        super(left, right, clusters);
        this.localMembershipMatrix = localMembershipMatrix;
    }
}
