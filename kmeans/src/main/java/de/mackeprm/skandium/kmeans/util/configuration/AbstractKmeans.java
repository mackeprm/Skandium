package de.mackeprm.skandium.kmeans.util.configuration;

import com.beust.jcommander.JCommander;
import de.huberlin.mackeprm.skandium.statistics.Run;
import de.huberlin.mackeprm.skandium.statistics.SqliteRunRepository;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

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
    protected String inputFile;


    public AbstractKmeans(String[] args) throws UnknownHostException {
        KMeansRunConfiguration config = new KMeansRunConfiguration();
        new JCommander(config, args);
        parseConfig(config);
    }

    public AbstractKmeans(KMeansRunConfiguration config) throws UnknownHostException {
        parseConfig(config);
    }

    private void parseConfig(KMeansRunConfiguration config) throws UnknownHostException {
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
        this.seed = config.seed;
    }

    abstract public void run() throws Exception;

    public void storeMeasure(long measure, long totalTime) throws Exception {
        if (writeOutput) {

            long totalGarbageCollections = 0;
            long garbageCollectionTime = 0;

            for (GarbageCollectorMXBean gc :
                    ManagementFactory.getGarbageCollectorMXBeans()) {

                long count = gc.getCollectionCount();

                if (count >= 0) {
                    totalGarbageCollections += count;
                }

                long time = gc.getCollectionTime();

                if (time >= 0) {
                    garbageCollectionTime += time;
                }
            }

            Run currentRun = new Run(measure,
                    totalTime,
                    totalGarbageCollections,
                    garbageCollectionTime,
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
