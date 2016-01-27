package cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.model;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.util.Arrays;

/**
 * Represents a Hypercube in the n-dimensional Cartesian plane.
 * <p>
 * Note that it is an invariant that getLeft(d) &le; getRight(d) for all dimensions d
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Hypercube {

    /**
     * Numbers within this amount are considered to be the same.
     */
    public static final double epsilon = 1E-9;
    /**
     * Number of dimensions in this hypercube.
     */
    private final int dimension;
    /**
     * low values in each dimension.
     */
    private double lows[];
    /**
     * high values in each dimension.
     */
    private double highs[];

    /**
     * Construct an n-dimensional hypercube with origin coordinates.
     *
     * @param dimension the number of dimensions of the hypercube.
     */
    public Hypercube(int dimension) {
        this.dimension = dimension;

        this.lows = new double[dimension];
        this.highs = new double[dimension];
    }

    public Hypercube(Hypercube cube) {
        this.dimension = cube.dimensionality();

        this.lows = new double[dimension];
        this.highs = new double[dimension];

        for (int i = 0; i < dimension; i++) {
            this.lows[i] = cube.getLeft(i);
            this.highs[i] = cube.getRight(i);
        }
    }

    public Hypercube(double lows[], double highs[]) {
        dimension = lows.length;
        if (lows.length != highs.length) {
            throw new IllegalArgumentException("lows and highs arrays do not contain the same number of dimensions.");
        }

        if (dimension < 2) {
            throw new IllegalArgumentException("Hypercube can only be created with dimensions of 2 and higher.");
        }

        this.lows = new double[dimension];
        this.highs = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            this.lows[i] = lows[i];
            this.highs[i] = highs[i];
        }
    }

    public static boolean same(double d1, double d2) {
        // NaN numbers cannot be compared with '==' and must be treated separately.
        if (Double.isNaN(d1)) {
            return Double.isNaN(d2);
        }

        // this covers Infinite and NaN cases.
        if (d1 == d2) return true;

        // Infinity values can be compared with '==' as above
        if (Double.isInfinite(d1)) {
            return false;
        }


        // try normal value
        return value(d1 - d2) == 0;

    }

    public static double value(double x) {
        if ((x >= 0) && (x <= epsilon)) {
            return 0.0;
        }

        if ((x < 0) && (-x <= epsilon)) {
            return 0.0;
        }

        return x;
    }

    public int dimensionality() {
        return dimension;
    }

    public double getLeft(int d) {
        return lows[d];
    }

    public void setLeft(int d, double value) {
        lows[d] = value;
    }

    public double getRight(int d) {
        return highs[d];
    }

    public void setRight(int d, double value) {
        highs[d] = value;
    }

    /**
     * Meaningful hashcode function.
     */
    public int hashCode() {
        long bits = Double.doubleToLongBits(lows[0]);
        for (int i = 0; i < dimension; i++) {
            bits ^= Double.doubleToLongBits(lows[i]) * 31;
        }
        for (int i = 0; i < dimension; i++) {
            bits ^= Double.doubleToLongBits(highs[i]) * 31;
        }

        // use java.awt.2DPoint hashcode function
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * Determine equality by comparing coordinates on each dimension
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (o == null) return false;

        if (o instanceof Hypercube) {
            Hypercube ihc = (Hypercube) o;

            if (ihc.dimensionality() != dimension) {
                return false;
            }

            // Check each dimension, and leave right away on failure.
            for (int i = 0; i < dimension; i++) {

                double x = ihc.getLeft(i);
                if (!same(x, lows[i])) return false;

                x = ihc.getRight(i);
                if (!same(x, highs[i])) return false;
            }

            // everything passes!
            return true;
        }

        return false;
    }

    /**
     * Determine intersection among all point coordinates (in raw, optimized form).
     */
    public boolean intersects(double[] rawPoint) {
        if (rawPoint.length != dimension) {
            throw new IllegalArgumentException("Unable to determine intersection between Hypercube (dimension " +
                    dimension + ") and point (dimension " + rawPoint.length + ")");
        }

        // Check each dimension, and leave right away on failure.
        for (int i = 0; i < dimension; i++) {
            if (rawPoint[i] < lows[i] || rawPoint[i] > highs[i]) {
                return false;
            }
        }

        // passed all checks.
        return true;
    }

    /**
     * Determine intersection among all point coordinates.
     */
    public boolean intersects(Point p) {
        if (p.getDimension() != dimension) {
            throw new IllegalArgumentException("Unable to determine intersection between Hypercube (dimension " +
                    dimension + ") and point (dimension " + p.getDimension() + ")");
        }

        // Check each dimension, and leave right away on failure.
        for (int i = 0; i < dimension; i++) {
            double x = p.getValues().get(i);
            if (x < lows[i] || x > highs[i]) {
                return false;
            }
        }

        // passed all checks.
        return true;
    }

    /**
     * Determine if the hypercube wholly contains the given hypercube h.
     * <p>
     * The hypercube presents closed intervals on all dimensions. Note that if
     * -INF or +INF is present, then we can take care to ensure proper containment
     * even in face of boundless dimensions.
     *
     * @param h query hypercube
     */
    public boolean contains(Hypercube h) throws IllegalArgumentException {
        if (dimension != h.dimensionality()) {
            throw new IllegalArgumentException("Unable to check containment for hypercubes of different dimensions.");
        }

        // Check each dimension, and leave right away on failure.
        for (int i = 0; i < dimension; i++) {
            // find target bounds
            double innerLeft = h.getLeft(i);
            double innerRight = h.getRight(i);

            // is [innerLeft,innerRight] wholly contained within our [left,right]?
            if (getLeft(i) <= innerLeft &&
                    innerLeft <= innerRight &&
                    innerRight <= getRight(i)) {
                // try next dimension
            } else {
                return false;   // fails immediately
            }
        }

        // passed all checks.
        return true;
    }

    /**
     * Determine if the hypercube intersects the given hypercube h.
     * <p>
     * The hypercube presents closed intervals on all dimensions. Note that if
     * -INF or +INF is present, then we can take care to ensure proper containment
     * even in face of boundless dimensions.
     *
     * @param h query hypercube
     */
    public boolean intersects(Hypercube h) throws IllegalArgumentException {
        if (dimension != h.dimensionality()) {
            throw new IllegalArgumentException("Unable to check containment for hypercubes of different dimensions.");
        }

        // Check each dimension, and leave right away on success.
        for (int i = 0; i < dimension; i++) {
            // find target bounds
            double innerLeft = h.getLeft(i);
            double innerRight = h.getRight(i);

            // no intersection potential? try next dimension
            if (innerRight < getLeft(i)) continue;
            if (innerLeft > getRight(i)) continue;

            return true;
        }

        // failed to have a single intersection.
        return false;
    }

    /**
     * Reasonable toString method.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int d = 0; d < dimension; d++) {
            sb.append(getLeft(d)).append(",").append(getRight(d));
            if (d++ != dimension) {
                sb.append(" : ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public void initializeEmpty() {
        Arrays.fill(lows, Double.NEGATIVE_INFINITY);
        Arrays.fill(highs, Double.POSITIVE_INFINITY);
    }
}
