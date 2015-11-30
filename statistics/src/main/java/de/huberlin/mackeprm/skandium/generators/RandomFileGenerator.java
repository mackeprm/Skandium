package de.huberlin.mackeprm.skandium.generators;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RandomFileGenerator {
    private final Random random;
    private int dimension;
    private int numberOfValues;

    public RandomFileGenerator(Random random, int dimension, int numberOfValues) {
        this.random = random;
        this.dimension = dimension;
        this.numberOfValues = numberOfValues;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("RandomDataGenerator:" + Arrays.toString(args));
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Path outputFolder = Paths.get(System.getProperty("user.dir"));
        int dimension = 3;
        int numberOfValues = 10;
        if (args != null && args.length > 0) {
            dimension = Integer.valueOf(args[0]);
            numberOfValues = Integer.valueOf(args[1]);
            outputFolder = Paths.get(args[2]);
        }
        RandomFileGenerator generator = new RandomFileGenerator(new Random(4711), dimension, numberOfValues);
        generator.writeToFile(outputFolder);
        System.out.println("exiting");
    }

    public void writeToFile(Path folder) throws IOException {
        System.out.println("Generating File");
        String targetFileName = DataSetNamingStrategy.randomValues(dimension, numberOfValues);
        Path path = Paths.get(folder.toString(), targetFileName);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream outputStream = Files.newOutputStream(path);
        for (int i = 0; i < numberOfValues; i++) {
            if (i % 1000 == 0) {
                System.out.println("Wrote:" + i + "/" + numberOfValues);
            }
            final List<Double> randomDoubles = getRandomDoubles();
            final StringJoiner stringJoiner = new StringJoiner(",");
            for (Double randomDouble : randomDoubles) {
                stringJoiner.add(randomDouble.toString());
            }
            final String line = stringJoiner.toString() + "\n";
            outputStream.write(line.getBytes(Charset.defaultCharset()));
            outputStream.flush();
        }
        System.out.println("Generated File:" + path + " wrote " + numberOfValues + " Values");
    }

    private List<Double> getRandomDoubles() {
        final List<Double> result = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            result.add(random.nextDouble());
        }
        return result;
    }
}
