package cl.niclabs.skandium.examples.kmeans.treebased.model;

public class KDTree {
    private KDNode root;

    @Override
    public String toString() {
        if (root == null) {
            return "kdtree: empty";
        }

        StringBuilder sb = new StringBuilder();
        buildString(sb, root);
        return sb.toString();
    }

    private void buildString(StringBuilder sb, KDNode KDNode) {
        if (KDNode == null) {
            return;
        }

        KDNode left = KDNode.getBelow();
        KDNode right = KDNode.getAbove();

        if (left != null) {
            buildString(sb, left);
        }
        sb.append(KDNode.toString()).append("\n");
        if (right != null) {
            buildString(sb, right);
        }
    }

    public KDNode getRoot() {
        return root;
    }

    public void setRoot(KDNode newRoot) {
        root = newRoot;
    }
}
