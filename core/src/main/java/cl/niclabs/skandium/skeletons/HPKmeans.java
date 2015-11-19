package cl.niclabs.skandium.skeletons;

import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.*;

public class HPKmeans<P> extends AbstractSkeleton<P, P> {
    Condition<Pair<P, P>> convergenceCriterion;

    Split<P, ?> split;
    Skeleton<?, ?> expectationStep;
    Partition<?, ?> partition;
    Skeleton<?, ?> maximizationStep;
    Merge<?, P> merge;

    public <I, O, C, V> HPKmeans(Condition<Pair<P, P>> convergenceCriterion, Split<P, I> split, Execute<I, O> expectationStep, Partition<O, C> partition, Execute<C, V> maximizationStep, Merge<V, P> merge) {
        this.convergenceCriterion = convergenceCriterion;
        this.split = split;
        this.expectationStep = new Seq<>(expectationStep);
        this.partition = partition;
        this.maximizationStep = new Seq<>(maximizationStep);
        this.merge = merge;
    }

    @Override
    public void accept(SkeletonVisitor visitor) {
        visitor.visit(this);
    }

}
