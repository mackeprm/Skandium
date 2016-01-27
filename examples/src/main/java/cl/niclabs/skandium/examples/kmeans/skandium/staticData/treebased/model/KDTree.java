package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

public class KDTree {
    private final int dimensionality;
    private Node root;

    public KDTree(int dimensionality) {
        this.dimensionality = dimensionality;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "kdtree: empty";
        }

        StringBuilder sb = new StringBuilder();
        buildString(sb, root);
        return sb.toString();
    }

    private void buildString(StringBuilder sb, Node node) {
        if (node == null) {
            return;
        }

        Node left = node.getBelow();
        Node right = node.getAbove();

        if (left != null) {
            buildString(sb, left);
        }
        sb.append(node.toString()).append("\n");
        if (right != null) {
            buildString(sb, right);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node newRoot) {
        root = newRoot;
        if (root == null) {
            return;
        }

        Hypercube region = new Hypercube(dimensionality);
        region.initializeEmpty();
        root.propagate(region);
    }
}
