package cl.niclabs.skandium.kmeans;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.muscles.Condition;

import java.util.List;
import java.util.Stack;

public class LoopInstruction extends AbstractInstruction {


    private Condition convergenceCriterion;
    private Instruction parentInstruction;
    private Instruction iterationInstruction;

    public LoopInstruction(StackTraceElement[] strace, Condition convergenceCriterion, Instruction parentInstruction, Instruction iterationInstruction) {
        super(strace);
        this.convergenceCriterion = convergenceCriterion;
        this.parentInstruction = parentInstruction;
        this.iterationInstruction = iterationInstruction;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        //called with a pair <old,new>
        if (!convergenceCriterion.condition(param)) {
            stack.push(copy());
            stack.push(iterationInstruction);
        } else {
            stack.push(parentInstruction);
        }
        return param;
    }


    @Override
    public Instruction copy() {
        return new LoopInstruction(strace, convergenceCriterion, parentInstruction.copy(), iterationInstruction.copy());
    }
}
