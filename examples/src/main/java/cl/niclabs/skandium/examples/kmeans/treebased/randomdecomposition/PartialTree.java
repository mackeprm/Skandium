package cl.niclabs.skandium.examples.kmeans.treebased.randomdecomposition;

import cl.niclabs.skandium.examples.kmeans.treebased.model.CandidateSet;
import cl.niclabs.skandium.examples.kmeans.treebased.model.KDTree;

public class PartialTree {
    private KDTree kdTree;
    private CandidateSet localCandidateSet;

    public PartialTree(KDTree kdTree, CandidateSet localCandidateSet) {
        this.kdTree = kdTree;
        this.localCandidateSet = new CandidateSet(localCandidateSet.getCentroids());
    }

    public KDTree getKdTree() {
        return kdTree;
    }

    public CandidateSet getLocalCandidateSet() {
        return localCandidateSet;
    }
}
