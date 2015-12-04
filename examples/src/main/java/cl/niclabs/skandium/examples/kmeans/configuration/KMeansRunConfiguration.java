package cl.niclabs.skandium.examples.kmeans.configuration;

import com.beust.jcommander.Parameter;

public class KMeansRunConfiguration {

    @Parameter(names = "-out")
    public String outputDB = "output.db";

    @Parameter(names = "-in")
    public String inputFile = "/tmp/randomPoints-d3-n10000000.csv";

    @Parameter(names = "-live")
    public boolean writeOutput;

    @Parameter(names = "-n")
    public int numberOfValues = 100;

    @Parameter(names = "-k")
    public int numberOfClusterCenters = 10;

    @Parameter(names = "-d")
    public int dimension = 3;

    @Parameter(names = "-i")
    public int numberOfIterations = 10;

    @Parameter(names = "-p")
    public int numberOfThreads = 2;

    @Parameter(names = "-t")
    public String taskset;

    @Parameter(names = "-f", required = true)
    public String flavour;

    @Parameter(names = "-s")
    public long seed = 4711l;

}
