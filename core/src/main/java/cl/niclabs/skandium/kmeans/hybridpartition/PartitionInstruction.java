package cl.niclabs.skandium.kmeans.hybridpartition;


import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.instructions.MergeInst;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Partition;
import cl.niclabs.skandium.muscles.Split;

import java.util.List;
import java.util.Stack;

public class PartitionInstruction extends AbstractInstruction {
    Partition partition;
    Stack<Instruction> substack;
    Merge merge;

    public PartitionInstruction(StackTraceElement[] strace, Partition partition, Stack<Instruction> substack, Merge merge) {
        super(strace);
        this.partition = partition;
        this.substack = substack;
        this.merge = merge;
    }

    /**
     * Subdivides param using the {@link Split} muscle, and then a stack is copied for each subparam.
     * <p>
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if (param instanceof Object[]) {
            Object[] params = partition.partition((Object[]) param);
            for (int i = 0; i < params.length; i++) {
                children.add(copyStack(this.substack));
            }
            stack.push(new MergeInst(merge, strace));
            return params;
        } else {
            throw new IllegalStateException("tried to invoke partition on non array parameter, param was:" + param.getClass());
        }
    }

    @Override
    public Instruction copy() {
        return null;
    }
}
