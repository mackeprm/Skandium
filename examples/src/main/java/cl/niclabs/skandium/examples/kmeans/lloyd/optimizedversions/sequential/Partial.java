package cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.sequential;

import java.util.Arrays;

public class Partial {

    public int count;
    public double[] sum;

    public Partial(int dimension) {
        sum = new double[dimension];
        Arrays.fill(sum, 0d);
        count = 0;
    }

    public void add(double[] point) {
        add(point, 1);
    }

    public void add(double[] sum, int count) {
        this.count += count;
        for (int i = 0; i < this.sum.length; i++) {
            this.sum[i] += sum[i];
        }
    }

    @Override
    public String toString() {
        return "partial{" +
                "wghtC=" + Arrays.toString(sum) +
                ", count=" + count +
                '}';
    }
}
