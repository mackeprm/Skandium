package de.mackeprm.skandium.kmeans.util.io;

public class Printer {

    public static void printClusterCenters(double[][] centroids, int dimension) {
        for (int i = 0; i < centroids.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < dimension; j++) {
                System.out.print(centroids[i][j] + ", ");
            }
            System.out.print("\n");
        }
    }
}
