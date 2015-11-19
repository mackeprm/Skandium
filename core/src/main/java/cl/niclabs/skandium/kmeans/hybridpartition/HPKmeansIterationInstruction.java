package cl.niclabs.skandium.kmeans.hybridpartition;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.kmeans.KmeansMaximizationInst;
import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Partition;
import cl.niclabs.skandium.muscles.Split;

import java.util.List;
import java.util.Stack;

public class HPKmeansIterationInstruction extends AbstractInstruction {
    Split split;
    Stack<Instruction> expectationStep;
    Partition partition;
    Stack<Instruction> maximizationStep;
    Merge merge;

    public HPKmeansIterationInstruction(StackTraceElement[] strace, Split split, Stack<Instruction> expectationStep, Partition partition, Stack<Instruction> maximizationStep, Merge merge) {
        super(strace);
        this.split = split;
        this.expectationStep = expectationStep;
        this.partition = partition;
        this.maximizationStep = maximizationStep;
        this.merge = merge;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if (param instanceof Pair) {
            Object[] params = split.split(((Pair) param).getSecond());
            for (int i = 0; i < params.length; i++) {
                children.add(copyStack(expectationStep));
            }
            stack.push(new KmeansMaximizationInst(strace, ((Pair) param).getSecond()));
            stack.push(new PartitionInstruction(strace, partition, maximizationStep, merge));
            return params;
        } else {
            throw new IllegalStateException("param expected to be pair. Was:" + param.getClass());
        }
    }

    @Override
    public Instruction copy() {
        return new HPKmeansIterationInstruction(strace, split, expectationStep, partition, maximizationStep, merge);
    }
}
