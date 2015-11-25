package cl.niclabs.skandium.examples.kmeans.util.io;

import cl.niclabs.skandium.examples.kmeans.util.RandomDataSetGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

public class RandomDataFileWriter extends RandomDataSetGenerator {

    public RandomDataFileWriter(int dimension) {
        super(dimension);
    }

    public RandomDataFileWriter(int dimension, long seed) {
        super(dimension, seed);
    }

    public static void main(String[] args) throws IOException {
        RandomDataFileWriter fileWriter = new RandomDataFileWriter(3);
        fileWriter.writePoints(40_000);
    }

    public void writePoints(int numberOfPoints) throws IOException {
        final Path path = Paths.get("points-" + this.getDimension() + "-" + numberOfPoints + ".csv");
        writePointsTo(path, numberOfPoints);
    }

    public void writePointsTo(Path path, int numberOfPoints) throws IOException {
        Files.deleteIfExists(path);
        Files.createFile(path);

        OutputStream outputStream = Files.newOutputStream(path);
        for (int i = 0; i < numberOfPoints; i++) {
            if (i % 1000 == 0) {
                System.out.println("Wrote:" + i + "/" + numberOfPoints);
            }
            final List<Double> randomDoubles = getRandomDoubles();
            final StringJoiner stringJoiner = new StringJoiner(",");
            for (Double randomDouble : randomDoubles) {
                stringJoiner.add(randomDouble.toString());
            }
            final String line = stringJoiner.toString() + "\n";
            outputStream.write(line.getBytes(Charset.defaultCharset()));
        }
        outputStream.flush();
        System.out.println("done");
    }

}
