package de.mackeprm.skandium.kmeans.implementations.lloyd.partialmerge;

import de.mackeprm.skandium.kmeans.model.modelutils.Partial;
import de.mackeprm.skandium.kmeans.model.modelutils.Range;

public class RangeWithPartials extends Range {
    public Partial[] partials;

    public RangeWithPartials(int left, int right, Partial[] partials) {
        super(left, right);
        this.partials = partials;
    }
}
