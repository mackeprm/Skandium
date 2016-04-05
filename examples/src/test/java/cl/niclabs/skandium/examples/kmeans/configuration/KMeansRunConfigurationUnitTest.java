package cl.niclabs.skandium.examples.kmeans.configuration;

import cl.niclabs.skandium.examples.kmeans.KMeansRunConfiguration;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class KMeansRunConfigurationUnitTest {

    @Test
    public void testSuccessfulDefaultCase() {
        String[] args = {
                "-in", "input.file",
                "-out", "output.file",
                "-n", "11",
                "-k", "12",
                "-d", "13",
                "-i", "14",
                "-p", "15",
                "-t", "16-17",
                "-f", "sd-hp",
                "-s", "18",
        };
        KMeansRunConfiguration config = new KMeansRunConfiguration();
        new JCommander(config, args);
        assertThat(config.inputFile).isEqualTo("input.file");
        assertThat(config.outputDB).isEqualTo("output.file");
        assertThat(config.numberOfValues).isEqualTo(11);
        assertThat(config.numberOfClusterCenters).isEqualTo(12);
        assertThat(config.dimension).isEqualTo(13);
        assertThat(config.numberOfIterations).isEqualTo(14);
        assertThat(config.numberOfThreads).isEqualTo(15);
        assertThat(config.taskset).isEqualTo("16-17");
        assertThat(config.flavour).isEqualTo("sd-hp");
        assertThat(config.seed).isEqualTo(18);
    }

    @Test
    public void testSuppressOutput() {
        String[] args = {"-live", "-f", "foo"};
        KMeansRunConfiguration config = new KMeansRunConfiguration();
        new JCommander(config, args);
        assertThat(config.writeOutput).isTrue();
    }

    @Test
    @Ignore
    public void testDefaultValues() {
        String[] args = {"-f", "foo"};
        KMeansRunConfiguration config = new KMeansRunConfiguration();
        new JCommander(config, args);
        assertThat(config.inputFile).isEqualTo("/tmp/randomPoints-d3-n10000000.csv");
        assertThat(config.outputDB).isEqualTo("output.db");
        assertThat(config.numberOfValues).isEqualTo(600);
        assertThat(config.numberOfClusterCenters).isEqualTo(10);
        assertThat(config.dimension).isEqualTo(3);
        assertThat(config.numberOfIterations).isEqualTo(10);
        assertThat(config.numberOfThreads).isEqualTo(Runtime.getRuntime().availableProcessors());
        assertThat(config.taskset).isEqualTo(null);
        assertThat(config.flavour).isEqualTo("foo");
        assertThat(config.seed).isEqualTo(4711);
        assertThat(config.writeOutput).isFalse();
    }

    @Test
    public void testFlavourRequired() {
        assertThatThrownBy(
                () -> {
                    String[] args = {""};
                    KMeansRunConfiguration config = new KMeansRunConfiguration();
                    new JCommander(config, args);
                }
        ).isInstanceOf(ParameterException.class).hasMessage("The following option is required: -f ");

    }

    @Test
    @Ignore("learning test")
    public void testLiveConfig() {
        String[] args = {"-in", "/dev/shm/randomPoints-d3-n10000000.csv", "-out", "initial.db", "-live", "-f", "sd-sm", "-n", "4800000", "-k", "10", "-d", "3", "-i", "10", "-p", "1", "-t", "69-69"};
        KMeansRunConfiguration config = new KMeansRunConfiguration();
        new JCommander(config, args);
        assertThat(config.inputFile).isEqualTo("/dev/shm/randomPoints-d3-n10000000.csv");
        assertThat(config.outputDB).isEqualTo("initial.db");
        assertThat(config.numberOfValues).isEqualTo(4800000);
        assertThat(config.numberOfClusterCenters).isEqualTo(10);
        assertThat(config.dimension).isEqualTo(3);
        assertThat(config.numberOfIterations).isEqualTo(10);
        assertThat(config.numberOfThreads).isEqualTo(1);
        assertThat(config.taskset).isEqualTo("69-69");
        assertThat(config.flavour).isEqualTo("sd-sm");
        assertThat(config.writeOutput).isTrue();
    }
}