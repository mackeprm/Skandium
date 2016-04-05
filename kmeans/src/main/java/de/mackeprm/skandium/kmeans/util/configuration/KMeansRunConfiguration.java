package de.mackeprm.skandium.kmeans.util.configuration;

import com.beust.jcommander.Parameter;

public class KMeansRunConfiguration {

    @Parameter(names = "-out")
    public String outputDB = "output.db";

    @Parameter(names = "-in")
    //public String inputFile = "/tmp/randomPoints-d3-n1000000.csv";
    public String inputFile = "/tmp/randomPoints-d8-n800000.csv";

    @Parameter(names = "-live")
    public boolean writeOutput;

    @Parameter(names = "-n")
    public int numberOfValues = 10_000;

    @Parameter(names = "-k")
    public int numberOfClusterCenters = 8;

    @Parameter(names = "-d")
    public int dimension = 8;

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
