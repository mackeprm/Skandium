package cl.niclabs.skandium.skeletons;

import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;

public class MMKmeans<P> extends AbstractSkeleton<P, P> {

    Condition<Pair<P, P>> convergenceCriterion;
    Skeleton<P, ?> expectationStep;
    Skeleton<?, P> maximizationStep;

    public <I> MMKmeans(Condition<Pair<P, P>> convergenceCriterion, Skeleton<P, I> expectationStep, Skeleton<I, P> maximizationStep) {
        this.convergenceCriterion = convergenceCriterion;
        this.expectationStep = expectationStep;
        this.maximizationStep = maximizationStep;
    }

    @Override
    public void accept(SkeletonVisitor visitor) {
        visitor.visit(this);
    }
}
