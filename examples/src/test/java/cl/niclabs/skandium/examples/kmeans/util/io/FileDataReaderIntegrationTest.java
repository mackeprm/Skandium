package cl.niclabs.skandium.examples.kmeans.util.io;

import cl.niclabs.skandium.examples.kmeans.model.Point;
import de.huberlin.mackeprm.skandium.generators.DataSetNamingStrategy;
import de.huberlin.mackeprm.skandium.generators.RandomFileGenerator;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class FileDataReaderIntegrationTest {

    @Test
    public void generateAndReadFile() throws Exception {

        final Path userDir = Paths.get(System.getProperty("user.dir"));
        final Path targetFilePath = userDir.resolve(DataSetNamingStrategy.randomValues(5, 100));
        try {
            final RandomFileGenerator generator = new RandomFileGenerator(new Random(5), 5, 100);
            generator.writeToFile(userDir);

            final FileDataReader reader = new FileDataReader();
            final List<Point> points = reader.read(targetFilePath, 5, 100);
            assertThat(points).hasSize(100);
            assertThat(points.get(0).getDimension()).isEqualTo(5);
            assertThat(points.get(0).getValues()).hasSize(5);

            final List<Point> pointsWithLowerDimension = reader.read(targetFilePath, 3, 100);
            assertThat(pointsWithLowerDimension).hasSize(100);
            assertThat(pointsWithLowerDimension.get(0).getDimension()).isEqualTo(3);
            assertThat(pointsWithLowerDimension.get(0).getValues()).hasSize(3);

            final List<Point> fewerPoints = reader.read(targetFilePath, 3, 50);
            assertThat(fewerPoints).hasSize(50);
            assertThat(fewerPoints.get(0).getDimension()).isEqualTo(3);
            assertThat(fewerPoints.get(0).getValues()).hasSize(3);
        } finally {
            Files.delete(targetFilePath);
            System.out.println("deleted file:" + targetFilePath);
        }
    }

}