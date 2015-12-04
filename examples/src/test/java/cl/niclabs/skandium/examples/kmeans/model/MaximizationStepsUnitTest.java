package cl.niclabs.skandium.examples.kmeans.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MaximizationStepsUnitTest {

    @Test
    public void testMedian() {
        final double[] even = {3d, 5d, 7d, 9d};
        //{3, 5, 7, 9} is (5 + 7) / 2 = 6)
        assertThat(MaximizationSteps.medianOf(even)).isEqualTo(6.0);
        final double[] odd = {3d, 3d, 5d, 9d, 11d};
        assertThat(MaximizationSteps.medianOf(odd)).isEqualTo(5.0);
    }

    @Test
    public void testMultidimensionalMedian() {
        //For example, given the vectors (0,1), (1,0) and (2,2), the Manhattan-distance median is (1,1), which does not exist in the original data,
        List<Point> points = new ArrayList<>(3);
        points.add(new Point(Arrays.asList(0d, 1d)));
        points.add(new Point(Arrays.asList(1d, 0d)));
        points.add(new Point(Arrays.asList(2d, 2d)));
        Point median = MaximizationSteps.calculateMedianOf(points, 2);
        //noinspection ConstantConditions
        assertThat(median.getValues()).containsExactly(1d, 1d);
    }
}