package cl.niclabs.skandium.kmeans.mapmaximization;

import cl.niclabs.skandium.instructions.AbstractInstruction;
import cl.niclabs.skandium.instructions.Instruction;
import cl.niclabs.skandium.kmeans.Pair;

import java.util.List;
import java.util.Stack;

public class KmeansExpectationInst extends AbstractInstruction {


    public KmeansExpectationInst(StackTraceElement[] strace) {
        super(strace);
    }

    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        if (param instanceof Pair) {
            return ((Pair) param).getSecond();
        } else {
            throw new IllegalStateException("tried to begin k-means iteration without model pair");
        }
    }

    @Override
    public Instruction copy() {
        return new KmeansExpectationInst(strace);
    }
}
