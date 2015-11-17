package cl.niclabs.skandium.kmeans.mapmaximization;


import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.muscles.Condition;

import java.util.List;
import java.util.Stack;

public class KmeansLoopInst extends AbstractInstruction {
    Condition convergenceCriterion;
    Stack<Instruction> expectationStep;
    Stack<Instruction> maximizationStep;

    public KmeansLoopInst(StackTraceElement[] strace, Condition convergenceCriterion, Stack<Instruction> expectationStep, Stack<Instruction> maximizationStep) {
        super(strace);
        this.convergenceCriterion = convergenceCriterion;
        this.expectationStep = expectationStep;
        this.maximizationStep = maximizationStep;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        //called with a pair <old,new>
        if (!convergenceCriterion.condition(param)) {
            stack.push(copy());
            stack.push(new KmeansIterationInstruction(strace, expectationStep, maximizationStep));
        } else {
            stack.push(new MMKmeansInstruction(convergenceCriterion, expectationStep, maximizationStep, strace));
        }
        return param;
    }

    @Override
    public Instruction copy() {
        return new KmeansLoopInst(strace, convergenceCriterion, copyStack(expectationStep), copyStack(maximizationStep));
    }
}
