package cl.niclabs.skandium.instructions;

import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;

import java.util.List;
import java.util.Stack;

public class KmeansLoopInst extends AbstractInstruction {
    Condition convergenceCriterion;
    Split split;
    Stack<Instruction> expectationStep;
    Merge merge;
    Execute maximizationStep;

    public KmeansLoopInst(StackTraceElement[] strace, Condition convergenceCriterion, Split split, Stack<Instruction> expectationStep, Merge merge, Execute maximizationStep) {
        super(strace);
        this.convergenceCriterion = convergenceCriterion;
        this.split = split;
        this.expectationStep = expectationStep;
        this.merge = merge;
        this.maximizationStep = maximizationStep;
    }

    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if(!convergenceCriterion.condition(param)) {
            stack.push(copy());
            stack.push(new KmeansIterationInstruction(strace, split, expectationStep, merge, maximizationStep));
        } else {
            stack.push(new KmeansInstruction(convergenceCriterion,split,expectationStep,merge,maximizationStep,strace));
        }
        return param;
    }

    @Override
    public Instruction copy() {
        return new KmeansLoopInst(strace, convergenceCriterion, split, copyStack(expectationStep), merge, maximizationStep);
    }
}
