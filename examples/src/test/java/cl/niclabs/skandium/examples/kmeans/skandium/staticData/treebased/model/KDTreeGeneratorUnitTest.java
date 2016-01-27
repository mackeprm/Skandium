package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class KDTreeGeneratorUnitTest {

    @Test
    public void testGenerate() throws Exception {
        KDTreeGenerator generator = new KDTreeGenerator();
        KDTree result = generator.generate(Arrays.asList(new Point("1,1,1"), new Point("2,2,2"), new Point("3,3,3"), new Point("4,4,4"), new Point("5,5,5")));
        assertThat(result.getRoot().getSplittingHyperplane()).isCloseTo(3.0d, Offset.offset(0.001));
    }

    //this is taken from: https://en.wikipedia.org/wiki/K-d_tree
    @Test
    public void testExample() throws Exception {
        //(2,3), (5,4), (9,6), (4,7), (8,1), (7,2).
        KDTreeGenerator generator = new KDTreeGenerator();
        KDTree result = generator.generate(Arrays.asList(new Point("2,3"),
                new Point("5,4"),
                new Point("9,6"),
                new Point("4,7"),
                new Point("8,1"),
                new Point("7,2")));
        //Checking the whole tree layout:
        final Node root = result.getRoot();
        assertThat(root.getSplittingHyperplane()).isCloseTo(7.0d, Offset.offset(0.001));
        assertThat(root.getRegion().dimensionality()).isEqualTo(2);
        assertThat(root.getRegion().contains(new Hypercube(new double[]{2, 1}, new double[]{9, 6}))).isTrue();
        //second level
        final Node yBelow = root.getBelow();
        final Node yAbove = root.getAbove();
        assertThat(yBelow.getSplittingHyperplane()).isCloseTo(4.0d, Offset.offset(0.001));
        //should contain everything under 7,X
        assertThat(yBelow.getRegion().contains(new Hypercube(new double[]{0, 0}, new double[]{7, 6}))).isTrue();
        //should not contain 8,X
        assertThat(yBelow.getRegion().contains(new Hypercube(new double[]{0, 0}, new double[]{8, 6}))).isFalse();

        assertThat(yAbove.getSplittingHyperplane()).isCloseTo(6.0d, Offset.offset(0.001));
        //should contain everything over 7,X
        assertThat(yAbove.getRegion().contains(new Hypercube(new double[]{7, 1}, new double[]{8, 6}))).isTrue();
        //should not contain 6,X
        assertThat(yAbove.getRegion().contains(new Hypercube(new double[]{2, 1}, new double[]{5, 6}))).isFalse();

        //last level
        assertThat(yBelow.getBelow().getSplittingHyperplane()).isCloseTo(2.0d, Offset.offset(0.001));
        assertThat(yBelow.getBelow().getRegion().intersects(new double[]{2, 3})).isTrue();
        assertThat(yBelow.getBelow().getRegion().intersects(new double[]{4, 7})).isFalse();

        assertThat(yBelow.getAbove().getSplittingHyperplane()).isCloseTo(4.0d, Offset.offset(0.001));
        assertThat(yBelow.getAbove().getRegion().intersects(new double[]{4, 7})).isTrue();
        assertThat(yBelow.getAbove().getRegion().intersects(new double[]{2, 3})).isFalse();

        assertThat(yAbove.getBelow().getSplittingHyperplane()).isCloseTo(8.0d, Offset.offset(0.001));
        assertThat(yAbove.getBelow().getRegion().intersects(new double[]{8, 1})).isTrue();
        assertThat(yAbove.getBelow().getRegion().intersects(new double[]{9, 6})).isTrue();


        assertThat(yAbove.getAbove()).isNull();
    }
}