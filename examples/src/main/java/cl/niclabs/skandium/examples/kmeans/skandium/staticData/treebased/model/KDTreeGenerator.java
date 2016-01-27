package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.List;

public class KDTreeGenerator {

    public KDTree generate(List<Point> input) {
        if (input.size() == 0) {
            return null;
        }
        int dimension = input.get(0).getDimension();
        final KDTree tree = new KDTree(dimension);
        //tree.setRoot(generateNode(0, dimension, 0, input.size() - 1, input));
        tree.setRoot(generateNode(input, 0));
        return tree;
    }

    private Node generateNode(List<Point> input, int currentDepth) {
        if (input.size() == 0) {
            return null;
        }
        //leaf conditions:
        final int currentDimension = currentDepth % input.get(0).getDimension();
        if (input.size() == 1) {
            return new Node(currentDimension, input.get(0));
        }
        input.sort((o1, o2) -> Double.compare(o1.getValues().get(currentDimension), o2.getValues().get(currentDimension)));
        final int median = input.size() / 2;
        Node result = new Node(currentDimension, input.get(median));
        result.setBelow(generateNode(input.subList(0, median), (currentDepth + 1)));
        result.setAbove(generateNode(input.subList(median + 1, input.size()), (currentDepth + 1)));
        return result;
    }
}
