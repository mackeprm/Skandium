package cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData;

import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;

public class FuzzyRange extends Range {

    final double[][] localMembershipMatrix;

    public FuzzyRange(int left, int right, final double[][] localMembershipMatrix) {
        super(left, right);
        this.localMembershipMatrix = localMembershipMatrix;
    }
}
