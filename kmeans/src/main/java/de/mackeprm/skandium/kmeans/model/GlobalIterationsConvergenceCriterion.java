package de.mackeprm.skandium.kmeans.model;


import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;


public class GlobalIterationsConvergenceCriterion<T> implements Condition<Pair<T, T>> {
    private final int maxCounter;
    private int counter;

    public GlobalIterationsConvergenceCriterion(int maxCounter) {
        this.maxCounter = maxCounter;
        this.counter = 0;
    }

    @Override
    public boolean condition(Pair<T, T> param) throws Exception {
        counter++;
        return counter > maxCounter;
    }
}
