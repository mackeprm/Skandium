package cl.niclabs.skandium.examples.kmeans.model;

import cl.niclabs.skandium.examples.kmeans.util.io.FileDataReader;
import de.huberlin.mackeprm.skandium.statistics.Run;
import de.huberlin.mackeprm.skandium.statistics.SqliteRunRepository;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

public abstract class AbstractKmeans {
    public static final String OUTPUT_DB = "sequential.db";
    private static final String DATA_POINT_FILE = "/tmp/randomPoints-d3-n10000000.csv";
    public static boolean WRITE_OUTPUT = false;
    public String flavour;
    public String system;
    public String taskset;

    public int numberOfThreads = Runtime.getRuntime().availableProcessors();
    public int partitions = Runtime.getRuntime().availableProcessors();
    public int numberOfClusterCenters = 10;
    public int numberOfIterations = 10;
    public int dimension = 3;
    public int numberOfValues = 600;
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

    public List<Point> getDataFromFile() throws IOException {
        FileDataReader reader = new FileDataReader();
        return reader.read(DATA_POINT_FILE, dimension, numberOfValues);
    }

    public void storeMeasure(long measure, long totalTime) throws Exception {
        if (WRITE_OUTPUT) {
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
            try (SqliteRunRepository runRepository = new SqliteRunRepository(OUTPUT_DB)) {
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
