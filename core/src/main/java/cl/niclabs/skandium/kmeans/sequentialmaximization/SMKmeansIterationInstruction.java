package cl.niclabs.skandium.kmeans.sequentialmaximization;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.instructions.MergeInst;
import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;

import java.util.List;
import java.util.Stack;

public class SMKmeansIterationInstruction extends AbstractInstruction {
    Split split;
    Stack<Instruction> expectationStep;
    Merge merge;
    Execute maximizationStep;

    public SMKmeansIterationInstruction(StackTraceElement[] strace, Split split, Stack<Instruction> expectationStep, Merge merge, Execute maximizationStep) {
        super(strace);
        this.split = split;
        this.expectationStep = expectationStep;
        this.merge = merge;
        this.maximizationStep = maximizationStep;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if(param instanceof Pair) {
            Object[] params = split.split(((Pair) param).getSecond());

            for(int i=0;i<params.length;i++){
                children.add(copyStack(this.expectationStep));
            }

            //Treat pair.getSecond as old Model in Maximization Instruction
            stack.push(new SMKmeansMaximizationInst(strace, maximizationStep, ((Pair) param).getSecond()));
            stack.push(new MergeInst(merge, strace));

            return params;
        } else {
            throw new IllegalStateException("param expected to be pair. Was:" + param.getClass());
        }
    }

    @Override
    public Instruction copy() {
        return new SMKmeansIterationInstruction(strace, split, copyStack(expectationStep), merge, maximizationStep);
    }
}
