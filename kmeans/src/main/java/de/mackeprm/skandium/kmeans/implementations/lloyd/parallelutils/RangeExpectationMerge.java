package de.mackeprm.skandium.kmeans.implementations.lloyd.parallelutils;

import cl.niclabs.skandium.muscles.Merge;
import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;

public class RangeExpectationMerge implements Merge<RangeWithAssignments, RangeWithAssignments> {

    private final int dataSize;

    public RangeExpectationMerge(int dataSize) {
        this.dataSize = dataSize;
    }

    @Override
    public RangeWithAssignments merge(RangeWithAssignments[] param) throws Exception {
        RangeWithAssignments result = new RangeWithAssignments(0, dataSize);
        for (RangeWithAssignments rwa : param) {
            System.arraycopy(rwa.assignments, 0, result.assignments, rwa.left, rwa.assignments.length);
        }
        return result;
    }
}
