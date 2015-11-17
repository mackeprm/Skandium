package cl.niclabs.skandium.impl.skandium;

import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.skeletons.If;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;

public class SkandiumIf<P, R> extends If<P, R> {

    private final Skandium skandium;

    public SkandiumIf(Condition<P> condition, Function<P, R> trueCase, Function<P, R> falseCase, Skandium skandium) {
        super(condition, trueCase, falseCase);
        this.skandium = skandium;
    }

    @Override
    public R apply(P p) {
        Future<R> result;
        ExecutorService executorService = skandium.getExecutorService();
        if (condition.apply(p)) {
            result = executorService.submit(() -> this.trueCase.apply(p));
        } else {
            result = executorService.submit(() -> this.falseCase.apply(p));
        }
        try {
            return result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
