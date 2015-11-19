package cl.niclabs.skandium.examples.kmeans.skandium.staticData;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SplitInSubrangesUnitTest {

    @Test
    public void testEvenSplit() throws Exception {
        final SplitInSubranges split = new SplitInSubranges(5);
        final Range[] results = split.split(new Range(0, 10));
        assertThat(results).hasSize(5);
        assertThat(results[0]).isEqualToComparingOnlyGivenFields(new Range(0, 2), "left", "right");
        assertThat(results[1]).isEqualToComparingOnlyGivenFields(new Range(2, 4), "left", "right");
        assertThat(results[2]).isEqualToComparingOnlyGivenFields(new Range(4, 6), "left", "right");
        assertThat(results[3]).isEqualToComparingOnlyGivenFields(new Range(6, 8), "left", "right");
        assertThat(results[4]).isEqualToComparingOnlyGivenFields(new Range(8, 10), "left", "right");
    }

    @Test
    public void testSplitWithRemainder() throws Exception {
        final SplitInSubranges split = new SplitInSubranges(3);
        final Range[] results = split.split(new Range(0, 10));
        assertThat(results).hasSize(4);
        assertThat(results[0]).isEqualToComparingOnlyGivenFields(new Range(0, 3), "left", "right");
        assertThat(results[1]).isEqualToComparingOnlyGivenFields(new Range(3, 6), "left", "right");
        assertThat(results[2]).isEqualToComparingOnlyGivenFields(new Range(6, 9), "left", "right");
        assertThat(results[3]).isEqualToComparingOnlyGivenFields(new Range(9, 10), "left", "right");
    }

    @Test
    public void testImpossibleSplit() throws Exception {
        final SplitInSubranges split = new SplitInSubranges(10);
        assertThatThrownBy(() -> split.split(new Range(0, 5))).isInstanceOf(IllegalStateException.class).hasMessage("input was smaller than number of chunks");
    }
}