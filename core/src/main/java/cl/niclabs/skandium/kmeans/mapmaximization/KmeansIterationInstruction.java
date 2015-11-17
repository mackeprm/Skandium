package cl.niclabs.skandium.kmeans.mapmaximization;


import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.kmeans.Pair;

import java.util.List;
import java.util.Stack;

public class KmeansIterationInstruction extends AbstractInstruction {

    Stack<Instruction> expectationStep;
    Stack<Instruction> maximizationStep;

    public KmeansIterationInstruction(StackTraceElement[] strace, Stack<Instruction> expectationStep, Stack<Instruction> maximizationStep) {
        super(strace);
        this.expectationStep = expectationStep;
        this.maximizationStep = maximizationStep;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if (param instanceof Pair) {
            //MaximizationStep
            //Treat pair.getSecond as old Model in Maximization Instruction
            stack.push(new KmeansMaximizationInst(strace, ((Pair) param).getSecond()));
            stack.addAll(maximizationStep);

            //ExpectationStep
            stack.addAll(expectationStep);
            stack.push(new KmeansExpectationInst(strace));
            return param;
        } else {
            throw new IllegalStateException("param expected to be pair. Was:" + param.getClass());
        }
    }

    @Override
    public Instruction copy() {
        return null;
    }
}
