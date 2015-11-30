package de.huberlin.mackeprm.skandium.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Run {
    private long measure;
    private int n;
    private int k;
    private int d;
    private int i;
    private int partitions;
    private int cpus;
    private String flavour;
    private String system;
    private String taskSet;
    private long timestamp;

    public Run(long measure, int n, int k, int d, int i, int partitions, int cpus, String flavour, String system, String taskset, long timestamp) {
        this.measure = measure;
        this.n = n;
        this.k = k;
        this.d = d;
        this.i = i;
        this.partitions = partitions;
        this.cpus = cpus;
        this.flavour = flavour;
        this.system = system;
        this.taskSet = taskset;
        this.timestamp = timestamp;
    }

    public Run(ResultSet resultSet) throws SQLException {
        this.measure = resultSet.getLong("measure");
        this.n = resultSet.getInt("n");
        this.k = resultSet.getInt("k");
        this.d = resultSet.getInt("d");
        this.i = resultSet.getInt("i");
        this.partitions = resultSet.getInt("partitions");
        this.cpus = resultSet.getInt("cpus");
        this.flavour = resultSet.getString("flavour");
        this.system = resultSet.getString("system");
        this.taskSet = resultSet.getString("taskset");
        this.timestamp = resultSet.getLong("timestamp");
    }


    public long getMeasure() {
        return measure;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public int getD() {
        return d;
    }

    public int getI() {
        return i;
    }

    public int getPartitions() {
        return partitions;
    }

    public int getCpus() {
        return cpus;
    }

    public String getFlavour() {
        return flavour;
    }

    public String getSystem() {
        return system;
    }

    public String getTaskSet() {
        return taskSet;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Run{" +
                "measure=" + measure +
                ", n=" + n +
                ", k=" + k +
                ", d=" + d +
                ", i=" + i +
                ", partitions=" + partitions +
                ", cpus=" + cpus +
                ", flavour='" + flavour + '\'' +
                ", system='" + system + '\'' +
                ", taskSet='" + taskSet + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}