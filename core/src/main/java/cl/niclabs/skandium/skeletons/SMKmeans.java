package cl.niclabs.skandium.skeletons;

import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;

public class SMKmeans<P> extends AbstractSkeleton<P, P> {


    Condition<Pair<P,P>> convergenceCriterion;
    Split<P,?> split;
    Skeleton<?,?> expectationStep;
    Merge<?,?> merge;
    Execute<?,P> maximizationStep;

    public <I, O, C> SMKmeans(Split<P, I> split, Execute<I, O> expectationStep, Merge<O, C> merge, Execute<C, P> maximizationStep, Condition<Pair<P, P>> convergenceCriterion) {
        this.convergenceCriterion = convergenceCriterion;
        this.split = split;
        this.expectationStep = new Seq<>(expectationStep);
        this.merge = merge;
        this.maximizationStep = maximizationStep;
    }

    @Override
    public void accept(SkeletonVisitor visitor) {
        visitor.visit(this);
    }
}
