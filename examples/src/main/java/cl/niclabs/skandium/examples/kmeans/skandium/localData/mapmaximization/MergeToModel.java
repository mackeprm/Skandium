package cl.niclabs.skandium.examples.kmeans.skandium.localData.mapmaximization;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import cl.niclabs.skandium.examples.kmeans.skandium.localData.Model;
import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.List;

public class MergeToModel implements Merge<ClusterWithMean, Model> {
    @Override
    public Model merge(ClusterWithMean[] param) throws Exception {
        final List<Point> centroids = new ArrayList<>(param.length);
        final List<Point> data = new ArrayList<>(param[0].getPoints().size() * param.length);
        for (ClusterWithMean clusterWithMean : param) {
            centroids.add(clusterWithMean.getMean());
            data.addAll(clusterWithMean.getPoints());
        }
        return new Model(data, centroids);
    }
}
