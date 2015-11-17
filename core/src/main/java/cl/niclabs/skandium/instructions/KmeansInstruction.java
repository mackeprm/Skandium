package cl.niclabs.skandium.instructions;

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
        /*if(convergenceCriterion.condition(param)){
            Stack<Instruction> newStack = new Stack<>();
            newStack.add(this.copy());
            children.add(newStack);
        }*/
        //TODO hier muss alles mit vergleich und iteration passieren
        if(param instanceof Pair) {
            return ((Pair) param).getSecond();
        } else {
            stack.push(new KmeansLoopInst(strace,convergenceCriterion,split,expectationStep,merge,maximizationStep));
            return new Pair<P,P>(param, null);
        }
    }

    @Override
    public Instruction copy() {
        return new KmeansInstruction(convergenceCriterion, split, copyStack(expectationStep), merge, maximizationStep, strace);
    }
}
