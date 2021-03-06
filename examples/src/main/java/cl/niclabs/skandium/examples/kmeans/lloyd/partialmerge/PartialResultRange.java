package cl.niclabs.skandium.examples.kmeans.lloyd.partialmerge;

import cl.niclabs.skandium.examples.kmeans.lloyd.Range;

import java.util.Map;

public class PartialResultRange extends Range {
    private Map<Integer, Partial> partials;

    public PartialResultRange(int left, int right) {
        super(left, right);
    }

    public Map<Integer, Partial> getPartials() {
        return partials;
    }

    public void setPartials(Map<Integer, Partial> partials) {
        this.partials = partials;
    }
}
