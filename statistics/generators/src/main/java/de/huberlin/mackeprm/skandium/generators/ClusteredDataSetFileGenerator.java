package de.huberlin.mackeprm.skandium.generators;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ClusteredDataSetFileGenerator {

    private final Random random;
    private final int dimension;
    private final int numberOfValues;
    private final int numberOfCentroids;

    public ClusteredDataSetFileGenerator(Random random, int dimension, int numberOfValues, int numberOfCentroids) {
        this.random = random;
        this.dimension = dimension;
        this.numberOfValues = numberOfValues;
        this.numberOfCentroids = numberOfCentroids;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("ClusteredDataSetFileGenerator:" + Arrays.toString(args));
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Path outputFolder = Paths.get(System.getProperty("user.dir"));
        int dimension = 2;
        int numberOfValues = 30;
        int numberOfCentroids = 3;
        if (args != null && args.length > 0) {
            dimension = Integer.valueOf(args[0]);
            numberOfValues = Integer.valueOf(args[1]);
            numberOfCentroids = Integer.valueOf(args[2]);
            outputFolder = Paths.get(args[3]);
        }
        ClusteredDataSetFileGenerator generator = new ClusteredDataSetFileGenerator(new Random(4711), dimension, numberOfValues, numberOfCentroids);
        generator.writeToFile(outputFolder);
        System.out.println("exiting");
    }

    private void writeToFile(Path folder) throws IOException {
        System.out.println("Generating File");
        String targetFileName = DataSetNamingStrategy.gaussValues(dimension, numberOfValues, numberOfCentroids);
        Path path = Paths.get(folder.toString(), targetFileName);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream outputStream = Files.newOutputStream(path);

        final int numberOfValuesPerChunk = (int) Math.ceil((double) numberOfValues / (double) numberOfCentroids);
        int currentCluster = -1;
        NormalDistribution normalDistribution = null;
        for (int i = 0; i < numberOfValues; i++) {
            if (i % numberOfValuesPerChunk == 0) {
                currentCluster++;
                System.out.println("i=" + i + ", cluster=" + currentCluster);
                double mean = random.nextDouble();
                System.out.println("mean: [" + currentCluster + "]" + mean);
                normalDistribution = new NormalDistribution(mean, random.nextDouble());
            }
            final List<Double> randomDoubles = getRandomDoubles(normalDistribution);
            final StringJoiner stringJoiner = new StringJoiner(",");
            for (Double randomDouble : randomDoubles) {
                stringJoiner.add(randomDouble.toString());
            }
            final String line = stringJoiner.toString() + "\n";
            outputStream.write(line.getBytes(Charset.defaultCharset()));
            outputStream.flush();
        }
    }

    private List<Double> getRandomDoubles(NormalDistribution normalDistribution) {
        List<Double> result = new ArrayList<>(dimension);
        for (int d = 0; d < dimension; d++) {
            result.add(normalDistribution.sample());
        }
        return result;
    }

}