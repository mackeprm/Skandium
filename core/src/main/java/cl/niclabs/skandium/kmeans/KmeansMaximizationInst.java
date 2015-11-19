package cl.niclabs.skandium.kmeans;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;

import java.util.List;
import java.util.Stack;

public class KmeansMaximizationInst extends AbstractInstruction {

    private Object oldParam;

    public <P> KmeansMaximizationInst(StackTraceElement[] strace, P oldParam) {
        super(strace);
        this.oldParam = oldParam;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        return new Pair<>(oldParam, param);
    }

    @Override
    public Instruction copy() {
        return new KmeansMaximizationInst(strace, oldParam);
    }
}