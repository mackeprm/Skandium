package cl.niclabs.skandium.examples.kmeans.skandium.kmedian.staticData;

import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.Range;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SMKmedianMaximizationStep implements Execute<Range, Range> {
    private int numberOfClusterCenters;
    private List<Point> data;

    public SMKmedianMaximizationStep(int numberOfClusterCenters, List<Point> data) {
        this.numberOfClusterCenters = numberOfClusterCenters;
        this.data = data;
    }

    @Override
    public Range execute(Range param) throws Exception {
        final Map<Integer, List<Point>> clusters = new HashMap<>(numberOfClusterCenters);
        for (int i = 0; i < param.clusterIndices.size(); i++) {
            final Integer currentIndex = param.clusterIndices.get(i);
            if (clusters.get(currentIndex) == null) {
                clusters.put(currentIndex, new ArrayList<>());
            }
            clusters.get(currentIndex).add(data.get(i));
        }
        final List<Point> newClusterCenters = new ArrayList<>(numberOfClusterCenters);

        newClusterCenters.addAll(clusters.entrySet().stream().map(entry -> MaximizationSteps.calculateMedianOf(entry.getValue(), entry.getValue().get(0).getDimension())).collect(Collectors.toList()));
        final Range result = new Range(0, data.size());
        result.clusters = newClusterCenters;
        return result;
    }
}
