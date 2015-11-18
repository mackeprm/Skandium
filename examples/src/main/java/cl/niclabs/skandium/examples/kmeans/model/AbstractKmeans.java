package cl.niclabs.skandium.examples.kmeans.model;

public abstract class AbstractKmeans {
    public String name;
    public int numberOfThreads = Runtime.getRuntime().availableProcessors();
    public int numberOfClusterCenters = 2;
    public int numberOfIterations = 10;
    public int dimension = 3;
    public int numberOfValues = 50_000;
    public long seed = 4711l;


    public AbstractKmeans(String name, String[] args) {
        this.name = name;
        if (args.length != 0) {
            numberOfThreads = Integer.parseInt(args[0]);
            numberOfValues = Integer.parseInt(args[1]);
            numberOfClusterCenters = Integer.parseInt(args[2]);
            dimension = Integer.parseInt(args[3]);
            numberOfIterations = Integer.parseInt(args[4]);
        }
    }

    abstract public void run() throws Exception;

    @Override
    public String toString() {
        return name + ":{" +
                "Threads=" + numberOfThreads +
                ", n=" + numberOfValues +
                ", k=" + numberOfClusterCenters +
                ", d=" + dimension +
                ", i=" + numberOfIterations +
                '}';
    }
}
