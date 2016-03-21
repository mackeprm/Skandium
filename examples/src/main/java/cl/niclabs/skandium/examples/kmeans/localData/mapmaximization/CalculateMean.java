package cl.niclabs.skandium.examples.kmeans.localData.mapmaximization;


import cl.niclabs.skandium.examples.kmeans.model.MaximizationSteps;
import cl.niclabs.skandium.muscles.Execute;

public class CalculateMean implements Execute<Cluster, ClusterWithMean> {
    @Override
    public ClusterWithMean execute(Cluster param) throws Exception {
        return new ClusterWithMean(param.getPoints(), MaximizationSteps.calculateMeanOf(param.getPoints(), param.getPoints().get(0).getDimension()));
    }
}
