package cl.niclabs.skandium.impl.forkjoin;


import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;
import cl.niclabs.skandium.skeletons.Map;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ForkJoinMap<P, R, X, Y> extends Map<P, R, X, Y> {

    public ForkJoinMap(Split<P, X> split, Function<X, Y> skeleton, Merge<Y, R> merge) {
        super(split, skeleton, merge);
    }

    @Override
    public R apply(P p) {
        final Collection<X> apply = split.apply(p);
        final List<Y> collect = apply.parallelStream().map(skeleton).collect(Collectors.toList());
        return merge.apply(collect);
    }
}
