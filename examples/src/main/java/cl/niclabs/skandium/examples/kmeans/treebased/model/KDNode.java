package cl.niclabs.skandium.examples.kmeans.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KDNode {

    private final int currentDimension;
    private final double splittingHyperplane;
    //kd-kmeans fields:
    private final int count;
    private final Point weightedCentroid;

    private KDNode below;
    private KDNode above;
    private BoundingBox cell;

    public KDNode(int currentDimension, double splittingHyperplane, List<Point> includedPoints) {
        this.currentDimension = currentDimension;
        this.splittingHyperplane = splittingHyperplane;
        this.cell = calculateBoundingBoxOf(includedPoints);
        this.count = includedPoints.size();
        this.weightedCentroid = sumUpAll(includedPoints);
    }

    private BoundingBox calculateBoundingBoxOf(List<Point> includedPoints) {
        final int dimension = includedPoints.get(0).getDimension();
        double lows[] = new double[dimension];
        double highs[] = new double[dimension];
        Arrays.fill(lows, Double.POSITIVE_INFINITY);
        Arrays.fill(highs, Double.NEGATIVE_INFINITY);
        for (final Point point : includedPoints) {
            for (int d = 0; d < dimension; d++) {
                final Double currentValue = point.getValues().get(d);
                if (currentValue < lows[d]) {
                    lows[d] = currentValue;
                }
                if (currentValue > highs[d]) {
                    highs[d] = currentValue;
                }
            }
        }
        return new BoundingBox(new Point(getAsBoxed(lows)), new Point(getAsBoxed(highs)));
    }

    private Point sumUpAll(List<Point> includedPoints) {
        if (includedPoints.size() == 1) {
            return includedPoints.get(0);
        }
        int maxDimension = includedPoints.get(0).getDimension();
        double sums[] = new double[maxDimension];
        for (Point p : includedPoints) {
            for (int d = 0; d < maxDimension; d++) {
                sums[d] += p.getValues().get(d);
            }
        }
        return new Point(getAsBoxed(sums));
    }

    private List<Double> getAsBoxed(double[] sums) {
        return Arrays.stream(sums).boxed().collect(Collectors.toList());
    }

    public KDNode getBelow() {
        return below;
    }

    public void setBelow(KDNode node) {
        this.below = node;
    }

    public KDNode getAbove() {
        return above;
    }

    public void setAbove(KDNode node) {
        this.above = node;
    }

    public double getSplittingHyperplane() {
        return splittingHyperplane;
    }

    public BoundingBox getCell() {
        return cell;
    }

    public Point getWeightedCentroid() {
        return weightedCentroid;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        String result;
        if (this.count == 1) {
            result = "leaf: (" + weightedCentroid + ")\n";
        } else {
            result = "n: cell: (" + cell + ")" + " count: " + count + " wgtC:" + weightedCentroid + "\n";
        }
        return result;
    }

    //TODO belowIsNull and aboveIsNull?
    public boolean isLeaf() {
        return this.count == 1;
    }

    public Point getMean() {
        final Double[] centroid = new Double[weightedCentroid.getDimension()];
        for (int i = 0; i < weightedCentroid.getDimension(); i++) {
            centroid[i] = weightedCentroid.getValues().get(i) / (double) this.count;
        }
        return new Point(Arrays.asList(centroid));
    }
}
