package cl.niclabs.skandium.examples.kmeans.skandium;


import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;


public class GlobalIterationsConvergenceCriterion implements Condition<Pair<Model,Model>>{
    private final int maxCounter;
    private int counter;

    public GlobalIterationsConvergenceCriterion(int maxCounter) {
        this.maxCounter = maxCounter;
        this.counter = 0;
    }

    @Override
    public boolean condition(Pair<Model,Model> param) throws Exception {
        counter++;
        return counter > maxCounter;
    }
}
