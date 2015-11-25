package cl.niclabs.skandium.examples.kmeans.model;

import de.huberlin.mackeprm.skandium.statistics.Run;
import de.huberlin.mackeprm.skandium.statistics.SqliteRunRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public abstract class AbstractKmeans {
    public static final String OUTPUT_DB = "output.db";
    public String flavour;
    public String system;
    public String taskset;

    public int numberOfThreads = Runtime.getRuntime().availableProcessors();
    public int partitions = Runtime.getRuntime().availableProcessors();
    public int numberOfClusterCenters = 3;
    public int numberOfIterations = 10;
    public int dimension = 3;
    public int numberOfValues = 50_000;
    public long seed = 4711l;


    public AbstractKmeans(String[] args) throws UnknownHostException {
        this.flavour = args[0];
        this.system = InetAddress.getLocalHost().getHostName();
        if (args.length > 1) {
            //<flavour> <n> <k> <d> <i> <threads> <partitions> <taskset>
            numberOfValues = Integer.parseInt(args[1]);
            numberOfClusterCenters = Integer.parseInt(args[2]);
            dimension = Integer.parseInt(args[3]);
            numberOfIterations = Integer.parseInt(args[4]);
            numberOfThreads = Integer.parseInt(args[5]);
            partitions = Integer.parseInt(args[6]);
            taskset = args[7];
        }
    }

    abstract public void run() throws Exception;

    public void storeMeasure(long measure) throws Exception {
        Run currentRun = new Run(measure,
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
        try (SqliteRunRepository runRepository = new SqliteRunRepository(OUTPUT_DB)) {
            runRepository.dump(currentRun);
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
