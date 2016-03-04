package cl.niclabs.skandium.examples.kmeans.configuration;

import com.beust.jcommander.Parameter;

public class KMeansRunConfiguration {

    @Parameter(names = "-out")
    public String outputDB = "output.db";

    @Parameter(names = "-in")
    //public String inputFile = "/tmp/randomPoints-d3-n1000000.csv";
    public String inputFile = "/home/maximilian/Dropbox/HU/Master/evaluation/datasets/birch1.txt";

    @Parameter(names = "-live")
    public boolean writeOutput;

    @Parameter(names = "-n")
    public int numberOfValues = 600;

    @Parameter(names = "-k")
    public int numberOfClusterCenters = 10;

    @Parameter(names = "-d")
    public int dimension = 2;

    @Parameter(names = "-i")
    public int numberOfIterations = 10;

    @Parameter(names = "-p")
    public int numberOfThreads = Runtime.getRuntime().availableProcessors();

    @Parameter(names = "-t")
    public String taskset;

    @Parameter(names = "-f", required = true)
    public String flavour;

    @Parameter(names = "-s")
    public long seed = 4711l;

}
