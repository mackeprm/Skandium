package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class KDTreeGenerator {

    public static final int LEAF_THREASHOLD = 1;

    public KDTree generate(List<Point> input) {
        if (input.size() == 0) {
            return null;
        }
        final KDTree tree = new KDTree();
        tree.setRoot(generateNode(input, 0));
        return tree;
    }

    private KDNode generateNode(List<Point> input, int currentDepth) {
        if (input.size() == 0) {
            return null;
        }
        final int currentDimension = currentDepth % input.get(0).getDimension();
        //leaf condition:
        if (input.size() <= LEAF_THREASHOLD) {
            return new KDNode(currentDimension, input.get(0).getValues().get(currentDimension), input);
        }

        input.sort((o1, o2) -> Double.compare(o1.getValues().get(currentDimension), o2.getValues().get(currentDimension)));
        final int median = input.size() / 2;
        final KDNode result = new KDNode(currentDimension, input.get(median).getValues().get(currentDimension), input);
        final List<Point> belowList = input.subList(0, median);
        final List<Point> aboveList = input.subList(median, input.size());

        result.setBelow(generateNode(belowList, (currentDepth + 1)));
        result.setAbove(generateNode(aboveList, (currentDepth + 1)));

        return result;
    }
}
