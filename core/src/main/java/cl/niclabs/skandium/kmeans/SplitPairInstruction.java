package cl.niclabs.skandium.kmeans;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.muscles.Condition;

import java.util.List;
import java.util.Stack;

public class SplitPairInstruction extends AbstractInstruction {


    private Condition convergenceCriterion;
    private Instruction iterationInstruction;

    public SplitPairInstruction(StackTraceElement[] strace, Condition convergenceCriterion, Instruction iterationInstruction) {
        super(strace);
        this.convergenceCriterion = convergenceCriterion;
        this.iterationInstruction = iterationInstruction;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if (param instanceof Pair) {
            //when param is an instance of pair we are being called from the kmeans loop => kmeans iterations are done.
            //in this case we return the last computed model:
            return ((Pair) param).getSecond();
        } else {
            //When Param is a Model only, then we start the K-Means loop:
            stack.push(new LoopInstruction(strace,
                    convergenceCriterion,
                    copy(),
                    iterationInstruction));
            return new Pair<>(param, param);
        }
    }

    @Override
    public Instruction copy() {
        return new SplitPairInstruction(strace, convergenceCriterion, iterationInstruction.copy());
    }
}
