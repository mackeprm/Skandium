package de.huberlin.mackeprm.skandium.generators;

public class DataSetNamingStrategy {
    private DataSetNamingStrategy() {
    }

    public static String randomValues(int dimension, int numberOfValues) {
        return "randomPoints-d" + dimension + "-n" + numberOfValues + ".csv";
    }

    public static String gaussValues(int dimension, int numberOfValues, int clusters) {
        return "gaussPoints-d" + dimension + "-n" + numberOfValues + "-k" + clusters + ".csv";
    }
}
