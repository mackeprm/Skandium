package cl.niclabs.skandium.examples.map;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;
import cl.niclabs.skandium.skeletons.Map;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public class SimpleMapExample {


    public static void main(String[] args) throws Exception {

        Split<List<Integer>, Integer> split = (input) -> input.toArray(new Integer[input.size()]);

        Merge<Integer, List<Integer>> merge = Arrays::asList;

        Execute<Integer, Integer> twice = (x) -> 2 * x;

        Map<List<Integer>, List<Integer>> mapToDouble = new Map<>(
                split,
                twice,
                merge
        );

        try (Skandium skandium = new Skandium(Runtime.getRuntime().availableProcessors())) {

            Stream<List<Integer>, List<Integer>> stream = skandium.newStream(mapToDouble);
            Future<List<Integer>> result = stream.input(Arrays.asList(1, 2, 3, 4));

            System.out.println("Result:" + result.get());
        }
    }
}
