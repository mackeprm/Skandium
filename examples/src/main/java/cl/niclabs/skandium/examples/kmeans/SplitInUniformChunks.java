package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.util.XYPoint;
import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SplitInUniformChunks implements Split<List<XYPoint>,Collection<XYPoint>> {
    private int numberOfChunks;

    public SplitInUniformChunks(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<XYPoint>[] split(List<XYPoint> param) throws Exception {
        Collection<XYPoint>[] result;
        //TODO check inputs: greaterThanZero, param.size > numberOfChunks
        result = (List<XYPoint>[]) new ArrayList<?>[numberOfChunks];
        int chunkLength = Math.floorDiv(param.size(), numberOfChunks);
        int chunkIndex = 0;
        for(int i=0;i<param.size();i += chunkLength) {
            result[chunkIndex] = new ArrayList<>(param.subList(i, Math.min(param.size(), i + chunkLength)));
            chunkIndex++;
        }
        //TODO extra chunk handling
        return result;
    }
}
