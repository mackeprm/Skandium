package cl.niclabs.skandium.kmeans.mapmaximization;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Condition;

import java.util.List;
import java.util.Stack;

public class MMKmeansInstruction extends AbstractInstruction {
    Condition convergenceCriterion;
    Stack<Instruction> expectationStep;
    Stack<Instruction> maximizationStep;

    public MMKmeansInstruction(Condition convergenceCriterion, Stack<Instruction> expectationStep, Stack<Instruction> maximizationStep, StackTraceElement[] strace) {
        super(strace);
        this.convergenceCriterion = convergenceCriterion;
        this.expectationStep = expectationStep;
        this.maximizationStep = maximizationStep;
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
            stack.push(new KmeansLoopInst(strace, convergenceCriterion, expectationStep, maximizationStep));
            return new Pair<>(param, param);
        }
    }

    @Override
    public Instruction copy() {
        return new MMKmeansInstruction(convergenceCriterion, copyStack(expectationStep), copyStack(expectationStep), strace);
    }
}
