package cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.Arrays;
import java.util.List;

public class Partial {
    double[] sum;
    int count;

    public Partial(int dimension) {
        sum = new double[dimension];
        Arrays.fill(sum, 0d);
        count = 0;
    }

    public void add(Point point) {
        List<Double> values = point.getValues();
        for (int i = 0; i < values.size(); i++) {
            sum[i] += values.get(i);
        }
        count++;
    }

    public void add(Partial other) {
        count += other.count;
        for (int i = 0; i < sum.length; i++) {
            sum[i] += other.sum[i];
        }
    }
}
