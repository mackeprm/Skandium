package cl.niclabs.skandium.instructions;

import cl.niclabs.skandium.kmeans.Pair;
import cl.niclabs.skandium.muscles.Execute;

import java.util.List;
import java.util.Stack;

public class KmeansMaximizationInst extends AbstractInstruction {

    private Execute maximizationStep;
    private Object oldParam;

    public <P> KmeansMaximizationInst(StackTraceElement[] strace, Execute maximizationStep, P oldParam) {
        super(strace);
        this.maximizationStep = maximizationStep;
        this.oldParam = oldParam;
    }

    @Override
    public <P> Object interpret(P param, Stack<Instruction> stack, List<Stack<Instruction>> children) throws Exception {
        //TODO Anscheinend geht hier was schief. Pair ist immer mit den gleichen Models besetzt. siehe blockkommentar unten
        Object newParam = maximizationStep.execute(param);
        return new Pair<>(oldParam, newParam);
    }

    /*
    Cluster-Center Entwicklung:

    Start:
    Vector: [0.4129866361405867, 0.35989757852507265, 0.602481089005911]
    Vector: [0.5874905512317445, 0.3544855862254701, 0.6041949103268359]

    2nd
    Vector: [0.4129866361405867, 0.35989757852507265, 0.602481089005911]
    Vector: [0.5874905512317445, 0.3544855862254701, 0.6041949103268359]

    Vector: [0.26978945231306817, 0.46859536369205607, 0.4469596606714127]
    Vector: [0.7410327759236162, 0.49088874672865696, 0.6269420016515447]

    Vector: [0.4129866361405867, 0.35989757852507265, 0.602481089005911]
    Vector: [0.5874905512317445, 0.3544855862254701, 0.6041949103268359]

    Vector: [0.26978945231306817, 0.46859536369205607, 0.4469596606714127]
    Vector: [0.7410327759236162, 0.49088874672865696, 0.6269420016515447]

    Vector: [0.4129866361405867, 0.35989757852507265, 0.602481089005911]
    Vector: [0.5874905512317445, 0.3544855862254701, 0.6041949103268359]

    Vector: [0.26978945231306817, 0.46859536369205607, 0.4469596606714127]
    Vector: [0.7410327759236162, 0.49088874672865696, 0.6269420016515447]

    Vector: [0.4129866361405867, 0.35989757852507265, 0.602481089005911]
    Vector: [0.5874905512317445, 0.3544855862254701, 0.6041949103268359]

    Vector: [0.26978945231306817, 0.46859536369205607, 0.4469596606714127]
    Vector: [0.7410327759236162, 0.49088874672865696, 0.6269420016515447]

    Vector: [0.4129866361405867, 0.35989757852507265, 0.602481089005911]
    Vector: [0.5874905512317445, 0.3544855862254701, 0.6041949103268359]

    Vector: [0.26978945231306817, 0.46859536369205607, 0.4469596606714127]
    Vector: [0.7410327759236162, 0.49088874672865696, 0.6269420016515447]

     */

    @Override
    public Instruction copy() {
        return null;
    }
}
