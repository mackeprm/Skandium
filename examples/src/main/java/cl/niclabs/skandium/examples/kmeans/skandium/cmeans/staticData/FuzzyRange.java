package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;

import java.util.List;

public class FuzzyRange extends Range {

    final double[][] localMembershipMatrix;

    public FuzzyRange(int left, int right, final double[][] localMembershipMatrix, List<Point> clusters) {
        super(left, right, clusters);
        this.localMembershipMatrix = localMembershipMatrix;
    }
}
