package cl.niclabs.skandium.impl.sequential;

import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;
import cl.niclabs.skandium.skeletons.Map;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

public class SequentialMap<P, R, X, Y> extends Map<P, R, X, Y> {

    public SequentialMap(Split<P, X> split, Function<X, Y> skeleton, Merge<Y, R> merge) {
        super(split, skeleton, merge);
    }

    @Override
    public R apply(P param) {
        Collection<X> params = split.apply(param);
        Collection<Y> results = new LinkedList<>();
        for (X chunk : params) {
            results.add(skeleton.apply(chunk));
        }
        return merge.apply(results);
    }
}
