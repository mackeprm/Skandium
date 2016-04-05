package de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization;

import de.mackeprm.skandium.kmeans.model.modelutils.RangeWithAssignments;

public class KChunksMaximizationFunctions {


    public static double[][][] split(RangeWithAssignments param, int numberOfClusterCenters, double[][] data) {
        final double[][][] result = new double[numberOfClusterCenters][][];
        int[] assignmentCounts = new int[numberOfClusterCenters];
        for (int assignment : param.assignments) {
            assignmentCounts[assignment]++;
        }
        for (int i = 0; i < numberOfClusterCenters; i++) {
            result[i] = new double[assignmentCounts[i]][];
        }
        int withinClusterIndex[] = new int[numberOfClusterCenters];
        for (int i = 0; i < param.assignments.length; i++) {
            final Integer currentIndex = param.assignments[i];
            result[currentIndex][withinClusterIndex[currentIndex]] = data[i];
            withinClusterIndex[currentIndex]++;
        }
        return result;
    }

    //TODO this is the RangeExpectationMerge
    public static RangeWithAssignments merge(RangeWithAssignments[] param, int dataSize) throws Exception {
        RangeWithAssignments result = new RangeWithAssignments(0, dataSize);
        for (RangeWithAssignments rwa : param) {
            System.arraycopy(rwa.assignments, 0, result.assignments, rwa.left, rwa.assignments.length);
        }
        return result;
    }
}
