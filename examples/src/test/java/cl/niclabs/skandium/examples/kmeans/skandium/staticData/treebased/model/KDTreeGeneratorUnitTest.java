package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KDTreeGeneratorUnitTest {

    //(2,3), (5,4), (9,6), (4,7), (8,1), (7,2).
    @Test
    public void testCells() {
        final List<Point> input = getWikipediaExampleInput();
        final KDTreeGenerator generator = new KDTreeGenerator();
        final KDTree result = generator.generate(input);
        final KDNode root = result.getRoot();
        assertThat(root.getCell().contains(new Point("1,3"))).isFalse();
        assertThat(root.getCell().contains(new Point("2,3"))).isTrue();
        assertThat(root.getCell().contains(new Point("10,3"))).isFalse();

        assertThat(root.getCell().contains(new Point("5,0"))).isFalse();
        assertThat(root.getCell().contains(new Point("5,1"))).isTrue();
        assertThat(root.getCell().contains(new Point("5,8"))).isFalse();

        final KDNode b = root.getBelow();
        assertThat(b.getCell().contains(new Point("9,6"))).isFalse();
        assertThat(b.getCell().contains(new Point("5,4"))).isTrue();
        assertThat(b.getCell().contains(new Point("4,7"))).isTrue();
        assertThat(b.getCell().contains(new Point("2,3"))).isTrue();

        final KDNode bb = b.getBelow();
        assertThat(bb.getCell().contains(new Point("5,4"))).isFalse();
        assertThat(bb.getCell().contains(new Point("2,3"))).isTrue();

        final KDNode ba = b.getAbove();
        assertThat(ba.getCell().contains(new Point("2,3"))).isFalse();
        assertThat(ba.getCell().contains(new Point("5,4"))).isTrue();
        assertThat(ba.getCell().contains(new Point("4,7"))).isTrue();

        final KDNode a = root.getAbove();
        assertThat(a.getCell().contains(new Point("5,4"))).isFalse();
        assertThat(a.getCell().contains(new Point("7,2"))).isTrue();
        assertThat(a.getCell().contains(new Point("9,6"))).isTrue();
        assertThat(a.getCell().contains(new Point("8,1"))).isTrue();

        final KDNode ab = a.getBelow();
        assertThat(ab.getCell().contains(new Point("9,6"))).isFalse();
        assertThat(ab.getCell().contains(new Point("8,1"))).isTrue();

        final KDNode aa = a.getAbove();
        assertThat(aa.getCell().contains(new Point("8,1"))).isFalse();
        assertThat(aa.getCell().contains(new Point("9,6"))).isTrue();
        assertThat(aa.getCell().contains(new Point("7,2"))).isTrue();
    }

    @Test
    public void testCountsAndWeightedCentroids() {
        final List<Point> input = getWikipediaExampleInput();
        final KDTreeGenerator generator = new KDTreeGenerator();
        final KDTree result = generator.generate(input);
        final KDNode root = result.getRoot();
        assertThat(root.getCount()).isEqualTo(6);
        assertThat(root.getWeightedCentroid()).isEqualToComparingFieldByField(new Point("35,23"));

        final KDNode b = root.getBelow();
        assertThat(b.getCount()).isEqualTo(3);
        assertThat(b.getWeightedCentroid()).isEqualToComparingFieldByField(new Point("11,14"));

        final KDNode bb = b.getBelow();
        assertThat(bb.getCount()).isEqualTo(1);
        assertThat(bb.getWeightedCentroid()).isEqualToComparingFieldByField(new Point("2,3"));

        final KDNode ba = b.getAbove();
        assertThat(ba.getCount()).isEqualTo(2);
        assertThat(ba.getWeightedCentroid()).isEqualToComparingFieldByField(new Point("9,11"));

        final KDNode a = root.getAbove();
        assertThat(a.getCount()).isEqualTo(3);
        assertThat(a.getWeightedCentroid()).isEqualToComparingFieldByField(new Point("24,9"));

        final KDNode ab = a.getBelow();
        assertThat(ab.getCount()).isEqualTo(1);

        final KDNode aa = a.getAbove();
        assertThat(aa.getCount()).isEqualTo(2);
        assertThat(aa.getWeightedCentroid()).isEqualToComparingFieldByField(new Point("16,8"));
    }


    private List<Point> getWikipediaExampleInput() {
        return Arrays.asList(new Point("2,3"),
                new Point("5,4"),
                new Point("9,6"),
                new Point("4,7"),
                new Point("8,1"),
                new Point("7,2"));
    }
}