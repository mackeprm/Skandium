package cl.niclabs.skandium.examples.kmeans;


import cl.niclabs.skandium.examples.kmeans.model.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SplitInEqualChunksUnitTest {

    @Test
    public void divisibleInputProducesCorrectNumberOfChunks() throws Exception {
        final SplitInEqualChunks split = new SplitInEqualChunks(2);
        final ArrayList<Point> input = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            input.add(new Point(String.valueOf(i)));
        }
        final Collection<Collection<Point>> result = split.apply(input);

        assertThat(result).hasSize(2);
        final Iterator<Collection<Point>> iterator = result.iterator();
        assertThat(iterator.next()).hasSize(4);
        assertThat(iterator.next()).hasSize(4);
    }

    @Test
    public void chunkSizeGreaterInputProducesException() throws Exception {
        final SplitInEqualChunks split = new SplitInEqualChunks(4);
        final ArrayList<Point> input = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            input.add(new Point(String.valueOf(i)));
        }
        assertThatThrownBy(() -> split.apply(input)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void divisionWithRemainderProducesCorrectChunks() throws Exception {
        final SplitInEqualChunks split = new SplitInEqualChunks(3);
        final ArrayList<Point> input = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            input.add(new Point(String.valueOf(i)));
        }
        final Collection<Collection<Point>> result = split.apply(input);

        assertThat(result).hasSize(4);
        final Iterator<Collection<Point>> iterator = result.iterator();
        assertThat(iterator.next()).hasSize(2);
        assertThat(iterator.next()).hasSize(2);
        assertThat(iterator.next()).hasSize(2);
        assertThat(iterator.next()).hasSize(2);
    }
}