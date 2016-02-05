package cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.Arrays;
import java.util.List;

public class Partial {
    public double[] sum;
    public int count;

    public Partial(int dimension) {
        sum = new double[dimension];
        Arrays.fill(sum, 0d);
        count = 0;
    }

    public void add(Point point) {
        add(point, 1);
    }

    public void add(Partial other) {
        count += other.count;
        for (int i = 0; i < sum.length; i++) {
            sum[i] += other.sum[i];
        }
    }

    public void add(Point weightedPoint, int count) {
        List<Double> values = weightedPoint.getValues();
        for (int i = 0; i < values.size(); i++) {
            sum[i] += values.get(i);
        }
        this.count += count;
    }

    @Override
    public String toString() {
        return "partial{" +
                "wghtC=" + Arrays.toString(sum) +
                ", count=" + count +
                '}';
    }
}
