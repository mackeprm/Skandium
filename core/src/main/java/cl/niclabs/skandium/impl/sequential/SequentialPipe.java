package cl.niclabs.skandium.impl.sequential;

import cl.niclabs.skandium.skeletons.Pipe;

import java.util.function.Function;

public class SequentialPipe<P, R, X> extends Pipe<P, R, X> {

    public SequentialPipe(Function<P, X> stage1, Function<X, R> stage2) {
        super(stage1, stage2);
    }

    @Override
    public R apply(P p) {
        return stage1.andThen(stage2).apply(p);
    }
}
