package cl.niclabs.skandium.impl.sequential;

import cl.niclabs.skandium.skeletons.For;

import java.util.function.Function;

public class SequentialFor<P> extends For<P> {

    public SequentialFor(Function<P, P> skeleton, int times) {
        super(skeleton, times);
    }

    @Override
    public P apply(P p) {
        P result = p;
        for (int i = 0; i < times; i++) {
            result = subskel.apply(result);
        }
        return result;
    }
}
