package cl.niclabs.skandium.impl.forkjoin;


import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;
import cl.niclabs.skandium.skeletons.DaC;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ForkJoinDaC<P, R> extends DaC<P, R> {

    public ForkJoinDaC(Condition<P> condition, Split<P, P> split, Function<P, R> skeleton, Merge<R, R> merge) {
        super(condition, split, skeleton, merge);
    }

    @Override
    public R apply(P param) {
        if (condition.apply(param)) {
            Collection<P> params = split.apply(param);
            Collection<R> results =
                    params.parallelStream().map(this::apply).collect(Collectors.toList());
            return merge.apply(results);
        } else {
            return skeleton.apply(param);
        }
    }
}
