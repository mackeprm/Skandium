package de.mackeprm.skandium.kmeans.model.modelutils;

public class RangeWithCentroidsAndAssignments extends RangeWithCentroids {
    public int[] clusterIndices;

    public RangeWithCentroidsAndAssignments(int left, int right) {
        super(left, right);
        clusterIndices = new int[right - left];
    }


}
