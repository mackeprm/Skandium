package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.randomdecomposition;

import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model.CandidateSet;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model.KDTree;

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
