package cl.niclabs.skandium.impl.sequential;

import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;
import cl.niclabs.skandium.skeletons.DaC;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

public class SequentialDaC<P, R> extends DaC<P, R> {

    public SequentialDaC(Condition<P> condition, Split<P, P> split, Function<P, R> skeleton, Merge<R, R> merge) {
        super(condition, split, skeleton, merge);
    }

    @Override
    public R apply(P param) {
        if (condition.apply(param)) {
            Collection<P> params = split.apply(param);
            Collection<R> results = new LinkedList<>();
            for (P p : params) {
                results.add(apply(p));
            }
            return merge.apply(results);
        } else {
            return skeleton.apply(param);
        }
    }
}
