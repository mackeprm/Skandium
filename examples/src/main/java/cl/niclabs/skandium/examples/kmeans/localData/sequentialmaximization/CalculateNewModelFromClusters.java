package cl.niclabs.skandium.examples.kmeans.localData.sequentialmaximization;

import cl.niclabs.skandium.examples.kmeans.localData.ClusteredModel;
import cl.niclabs.skandium.examples.kmeans.localData.Model;
import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculateNewModelFromClusters implements Execute<ClusteredModel, Model> {
    @Override
    public Model execute(ClusteredModel param) throws Exception {
        List<Point> newCentroids = new ArrayList<>();
        List<Point> data = new ArrayList<>();
        for (Map.Entry<Integer, List<Point>> clusters : param.getEntries()) {
            newCentroids.add(MaximizationSteps.calculateMeanOf(clusters.getValue(), clusters.getValue().get(0).getDimension()));
            data.addAll(clusters.getValue());
        }
        return new Model(data, newCentroids);
    }
}
