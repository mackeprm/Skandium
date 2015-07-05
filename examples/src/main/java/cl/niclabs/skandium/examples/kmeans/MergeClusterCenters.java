package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.muscles.Merge;

import java.util.Arrays;
import java.util.List;

public class MergeClusterCenters implements Merge<XYPoint, List<XYPoint>> {
    @Override
    public List<XYPoint> merge(XYPoint[] param) throws Exception {
        return Arrays.asList(param);
    }
}
