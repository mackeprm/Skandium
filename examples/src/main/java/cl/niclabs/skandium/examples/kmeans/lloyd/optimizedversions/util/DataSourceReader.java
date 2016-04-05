package cl.niclabs.skandium.examples.kmeans.lloyd.optimizedversions.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DataSourceReader {

    public static double[][] initializeFromFileSource(String inputFile, int numberOfValues, int dimension) throws IOException {
        final double[][] datapoints = new double[numberOfValues][];
        final Path path = Paths.get(inputFile);
        final BufferedReader bufferedReader = readFromFile(path);
        for (int i = 0; i < numberOfValues; i++) {
            final String line = bufferedReader.readLine();
            final String[] split = line.split(",");
            datapoints[i] = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                datapoints[i][j] = Double.parseDouble(split[j]);
            }
        }

        return datapoints;
    }

    private static BufferedReader readFromFile(Path sourcePath) throws IOException {
        final InputStream in = Files.newInputStream(sourcePath, StandardOpenOption.READ);
        final InputStreamReader reader = new InputStreamReader(in);
        return new BufferedReader(reader);
    }
}
