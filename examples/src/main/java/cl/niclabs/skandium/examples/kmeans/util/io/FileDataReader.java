package cl.niclabs.skandium.examples.kmeans.util.io;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileDataReader implements DataReader {

    @Override
    public List<Point> read(String sourceAddress, int d, int n) throws IOException {
        final Path path = Paths.get(sourceAddress);
        return read(path, d, n);
    }

    public List<Point> read(Path sourcePath, int d, int n) throws IOException {
        final BufferedReader bufferedReader = readFromFile(sourcePath);
        final List<Point> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            final String line = bufferedReader.readLine();
            final String[] split = line.split(",");
            final List<Double> values = new ArrayList<>(d);
            for (int j = 0; j < d; j++) {
                values.add(Double.valueOf(split[j]));
            }
            result.add(new Point(values));
        }
        return result;
    }

    private BufferedReader readFromFile(Path sourcePath) throws IOException {
        final InputStream in = Files.newInputStream(sourcePath, StandardOpenOption.READ);
        final InputStreamReader reader = new InputStreamReader(in);
        return new BufferedReader(reader);
    }
}
