package cl.niclabs.skandium.examples.kmeans.model;

import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.util.io.FileDataReader;
import com.beust.jcommander.JCommander;
import de.huberlin.mackeprm.skandium.statistics.Run;
import de.huberlin.mackeprm.skandium.statistics.SqliteRunRepository;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

public abstract class AbstractKmeans {
    public String outputDB;
    public boolean writeOutput;
    public String flavour;
    public String system;
    public String taskset;
    public int numberOfThreads;
    public int partitions;
    public int numberOfClusterCenters;
    public int numberOfIterations;
    public int dimension;
    public int numberOfValues;
    public long seed;
    private String inputFile;


    public AbstractKmeans(String[] args) throws UnknownHostException {
        KMeansRunConfiguration config = new KMeansRunConfiguration();
        new JCommander(config, args);
        this.flavour = config.flavour;
        this.system = InetAddress.getLocalHost().getHostName();
        this.numberOfValues = config.numberOfValues;
        this.numberOfClusterCenters = config.numberOfClusterCenters;
        this.dimension = config.dimension;
        this.numberOfIterations = config.numberOfIterations;
        this.numberOfThreads = config.numberOfThreads;
        this.partitions = config.numberOfThreads;
        this.taskset = config.taskset;
        this.inputFile = config.inputFile;
        this.outputDB = config.outputDB;
        this.writeOutput = config.writeOutput;
    }

    abstract public void run() throws Exception;

    public List<Point> getDataFromFile() throws IOException {
        FileDataReader reader = new FileDataReader();
        return reader.read(inputFile, dimension, numberOfValues);
    }

    public void storeMeasure(long measure, long totalTime) throws Exception {
        if (writeOutput) {
            Run currentRun = new Run(measure,
                    totalTime,
                    this.numberOfValues,
                    this.numberOfClusterCenters,
                    this.dimension,
                    this.numberOfIterations,
                    this.partitions,
                    this.numberOfThreads,
                    this.flavour,
                    this.system,
                    this.taskset,
                    new Date().getTime());
            try (SqliteRunRepository runRepository = new SqliteRunRepository(outputDB)) {
                runRepository.dump(currentRun);
            }
        } else {
            System.out.println("no measure stored");
        }
    }

    @Override
    public String toString() {
        return "Kmeans{" +
                "flavour='" + flavour + '\'' +
                ", system='" + system + '\'' +
                ", taskset='" + taskset + '\'' +
                ", numberOfThreads=" + numberOfThreads +
                ", partitions=" + partitions +
                ", numberOfClusterCenters=" + numberOfClusterCenters +
                ", numberOfIterations=" + numberOfIterations +
                ", dimension=" + dimension +
                ", numberOfValues=" + numberOfValues +
                ", seed=" + seed +
                '}';
    }
}
