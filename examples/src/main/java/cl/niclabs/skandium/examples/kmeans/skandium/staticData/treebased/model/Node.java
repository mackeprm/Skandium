package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

public class Node {

    private final int currentDimension;
    private final Point point;
    private final double splittingHyperplane;

    private Node below;
    private Node above;
    private Hypercube region;

    public Node(int currentDimension, Point point) {
        this.currentDimension = currentDimension;
        this.point = point;
        splittingHyperplane = point.getValues().get(currentDimension);
        region = new Hypercube(point.getDimension());
        region.initializeEmpty();
    }

    public Node getBelow() {
        return below;
    }

    public void setBelow(Node node) {
        if (node == null) {
            this.below = null;
            return;
        }

        // Node belongs here, and we update node's region accordingly.
        if (((this.currentDimension + 1) == point.getDimension() && node.currentDimension == 0) ||
                (this.currentDimension + 1 == node.currentDimension)) {
            this.below = node;

            // we "close off" the 'above/right' area, since node is below.
            node.region = new Hypercube(region);
            node.region.setRight(currentDimension, splittingHyperplane);
            return;
        }

        throw new IllegalArgumentException("Can only set as children nodes whose dimensionality is one greater.");
    }

    public Node getAbove() {
        return above;
    }

    public void setAbove(Node node) {
        if (node == null) {
            this.above = null;
            return;
        }

        // Node belong here, and we update node's region accordingly.
        if (((this.currentDimension + 1) == point.getDimension() && node.currentDimension == 0) ||
                (this.currentDimension + 1 == node.currentDimension)) {
            this.above = node;

            // we "close off" the 'below/left' area, since node is above.
            node.region = new Hypercube(region);
            node.region.setLeft(currentDimension, splittingHyperplane);
            return;
        }

        throw new IllegalArgumentException("Can only set as children nodes whose dimensionality is one greater.");
    }

    public double getSplittingHyperplane() {
        return splittingHyperplane;
    }

    public Hypercube getRegion() {
        return region;
    }

    void propagate(Hypercube region) {
        this.region = region;

        // we "close off" the 'above/right' area, since node is below.
        if (below != null) {
            Hypercube child = new Hypercube(region);
            child.setRight(currentDimension, splittingHyperplane);
            below.propagate(child);
        }

        // we "close off" the 'below/left' area, since node is above.
        if (above != null) {
            Hypercube child = new Hypercube(region);
            child.setLeft(currentDimension, splittingHyperplane);
            above.propagate(child);
        }
    }

    @Override
    public String toString() {
        return "n{" +
                "d=" + currentDimension +
                ", p=" + point +
                '}';
    }
}
