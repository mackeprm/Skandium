package cl.niclabs.skandium.kmeans.sequentialmaximization;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;
import java.util.Stack;

public class SMKmeansMaximizationInst extends AbstractInstruction {

    private Execute maximizationStep;
    private Object oldParam;

    public <P> SMKmeansMaximizationInst(StackTraceElement[] strace, Execute maximizationStep, P oldParam) {
        super(strace);
        this.maximizationStep = maximizationStep;
        this.oldParam = oldParam;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        Object newParam = maximizationStep.execute(param);
        return new Pair<>(oldParam, newParam);
    }

    @Override
    public Instruction copy() {
        return new SMKmeansMaximizationInst(strace, maximizationStep, oldParam);
    }
}
