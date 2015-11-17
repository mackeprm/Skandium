package cl.niclabs.skandium.impl.skandium;

import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;
import cl.niclabs.skandium.skeletons.Map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Function;

public class SkandiumMap<P, R, I, O> extends Map<P, R, I, O> {
    private Skandium skandium;

    public SkandiumMap(Split<P, I> split, Function<I, O> skeleton, Merge<O, R> merge, Skandium skandium) {
        super(split, skeleton, merge);
        this.skandium = skandium;
    }

    @Override
    public R apply(P param) {
        Collection<I> params = split.apply(param);
        List<Callable<O>> mappingTaks = new ArrayList<>(params.size());
        for (I i : params) {
            mappingTaks.add(() -> this.skeleton.apply(i));
        }
        try {
            List<Future<O>> futures = skandium.getExecutorService().invokeAll(mappingTaks);
            List<O> results = new ArrayList<>(futures.size());
            for (Future<O> future : futures) {
                results.add(future.get());
            }
            return merge.apply(results);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
