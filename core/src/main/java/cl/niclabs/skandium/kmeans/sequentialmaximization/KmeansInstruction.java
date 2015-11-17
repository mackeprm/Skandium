package cl.niclabs.skandium.kmeans.sequentialmaximization;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;

import java.util.List;
import java.util.Stack;

public class KmeansInstruction extends AbstractInstruction {

    Condition convergenceCriterion;
    Split split;
    Stack<Instruction> expectationStep;
    Merge merge;
    Execute maximizationStep;

    public KmeansInstruction(Condition convergenceCriterion, Split split, Stack<Instruction> expectationStep, Merge merge, Execute maximizationStep, StackTraceElement[] strace) {
        super(strace);
        this.convergenceCriterion = convergenceCriterion;
        this.split = split;
        this.expectationStep = expectationStep;
        this.merge = merge;
        this.maximizationStep = maximizationStep;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if(param instanceof Pair) {
            //when param is an instance of pair we are being called from the kmeans loop => kmeans iterations are done.
            //in this case we return the last computed model:
            return ((Pair) param).getSecond();
        } else {
            //When Param is a Model only, then we start the K-Means loop:
            stack.push(new KmeansLoopInst(strace,convergenceCriterion,split,expectationStep,merge,maximizationStep));
            return new Pair<>(param, param);
        }
    }

    @Override
    public Instruction copy() {
        return new KmeansInstruction(convergenceCriterion, split, copyStack(expectationStep), merge, maximizationStep, strace);
    }
}
