package de.mackeprm.skandium.kmeans.model.modelutils;

public class RangeWithAssignments extends Range {
    public int[] assignments;

    public RangeWithAssignments(int left, int right) {
        super(left, right);
        assignments = new int[right - left];
    }
}
