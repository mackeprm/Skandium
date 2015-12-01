package cl.niclabs.skandium.examples.kmeans.model;


import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectationStepsUnitTest {


    @Test
    public void testEuclideanDistance() throws Exception {
        List<Double> source = Arrays.asList(2.0,-1.0);
        List<Double> target = Arrays.asList(-2.0,2.0);
        final double result = ExpectationSteps.euclideanDistance(source, target);
        assertThat(result).isEqualTo(5.0, Offset.offset(0.001));
    }

    @Test
    public void testManhattanDistance() throws Exception {
        List<Double> source = Arrays.asList(1.0,1.0);
        List<Double> target = Arrays.asList(4.0,5.0);
        final double euclideanDistance = ExpectationSteps.euclideanDistance(source, target);
        final double manhattanDistance = ExpectationSteps.manhattanDistance(source, target);
        assertThat(euclideanDistance).isEqualTo(5.0, Offset.offset(0.001));
        assertThat(manhattanDistance).isEqualTo(7.0, Offset.offset(0.001));
    }
}