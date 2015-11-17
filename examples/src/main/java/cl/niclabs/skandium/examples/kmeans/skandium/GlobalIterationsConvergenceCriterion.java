package cl.niclabs.skandium.examples.kmeans.skandium;


import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;

import java.util.concurrent.atomic.AtomicInteger;


public class GlobalIterationsConvergenceCriterion implements Condition<Pair<Model,Model>>{
    private AtomicInteger counter;
    private final int maxCounter;

    public GlobalIterationsConvergenceCriterion(int maxCounter) {
        this.maxCounter = maxCounter;
        this.counter = new AtomicInteger(0);
    }

    @Override
    public boolean condition(Pair<Model,Model> param) throws Exception {
        return this.counter.incrementAndGet() > maxCounter;
    }
}
