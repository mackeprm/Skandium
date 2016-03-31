package de.huberlin.mackeprm.skandium.statistics;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class SqliteRunRepository implements AutoCloseable {
    private static final String OUTPUT_DB = "output.db";

    private Connection dbConnection;

    public SqliteRunRepository(String filePath) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        if(Files.notExists(Paths.get(filePath))) {
            this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            initializeDatabase();
        }
        this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        System.out.println("Opened database successfully");
    }

    public static void main(String args[]) throws Exception {
        if ("initialize".equals(args[0])) {
            try (SqliteRunRepository runRepository = new SqliteRunRepository(OUTPUT_DB)) {
                runRepository.initializeDatabase();
            }
        } else {
            try (SqliteRunRepository runRepository = new SqliteRunRepository(OUTPUT_DB)) {
                Statement select = runRepository.dbConnection.createStatement();
                ResultSet resultSet = select.executeQuery("SELECT * FROM runs;");
                while (resultSet.next()) {
                    Run retrievedRun = new Run(resultSet);
                    System.out.println(retrievedRun);
                }
                resultSet.close();
            }
        }
    }

    public void initializeDatabase() throws SQLException {
        Statement createTable = dbConnection.createStatement();
        String sql = ("CREATE TABLE runs (measure, totalTime, gcCount, gcTime, n, k, d, i, partitions, cpus, flavour, system, taskset, timestamp);");
        createTable.executeUpdate(sql);
        createTable.close();
    }

    //Copy paste from http://javabeginners.de/Datenbanken/SQLite-Datenbank.php
    public synchronized void dump(Run run) throws SQLException {
        PreparedStatement insertRun = dbConnection
                .prepareStatement("INSERT INTO runs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        insertRun.setLong(1, run.getMeasure());
        insertRun.setLong(2, run.getTotalTime());
        insertRun.setLong(3, run.getGcCount());
        insertRun.setLong(4, run.getGcTime());
        insertRun.setInt(5, run.getN());
        insertRun.setInt(6, run.getK());
        insertRun.setInt(7, run.getD());
        insertRun.setInt(8, run.getI());
        insertRun.setInt(9, run.getPartitions());
        insertRun.setInt(10, run.getCpus());
        insertRun.setString(11, run.getFlavour());
        insertRun.setString(12, run.getSystem());
        insertRun.setString(13, run.getTaskSet());
        insertRun.setLong(14, run.getTimestamp());
        insertRun.addBatch();
        dbConnection.setAutoCommit(false);
        insertRun.executeBatch();
        dbConnection.setAutoCommit(true);
    }

    @Override
    public void close() throws Exception {
        this.dbConnection.close();
    }
}
