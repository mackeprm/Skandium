package de.huberlin.mackeprm.skandium.statistics;

import java.sql.*;

public class SqliteRunRepository implements AutoCloseable {
    private final String filePath;
    private Connection dbConnection;

    public SqliteRunRepository(String filePath) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.filePath = filePath;
        this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        System.out.println("Opened database successfully");
    }

    public static void main(String args[]) throws Exception {
        if ("initalize".equals(args[0])) {
            try (SqliteRunRepository runRepository = new SqliteRunRepository("output.db")) {
                runRepository.initializeDatabase();
            }
        } else if ("select".equals(args[0])) {
            try (SqliteRunRepository runRepository = new SqliteRunRepository("output.db")) {
                Statement select = runRepository.dbConnection.createStatement();
                ResultSet resultSet = select.executeQuery("SELECT * FROM runs;");
                while (resultSet.next()) {
                    Run retrievedRun = new Run(resultSet);
                    System.out.println(retrievedRun);
                }
                resultSet.close();
            }
        } else {
            System.out.println("Usage: <command>");
        }
    }

    public void initializeDatabase() throws SQLException {
        Statement createTable = dbConnection.createStatement();
        String sql = ("CREATE TABLE runs (measure, n, k, d, i, partitions, cpus, flavour, system, taskset, timestamp);");
        createTable.executeUpdate(sql);
        createTable.close();
    }

    //Copy paste from http://javabeginners.de/Datenbanken/SQLite-Datenbank.php
    public synchronized void dump(Run run) throws SQLException {
        PreparedStatement insertRun = dbConnection
                .prepareStatement("INSERT INTO runs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        insertRun.setLong(1, run.getMeasure());
        insertRun.setInt(2, run.getN());
        insertRun.setInt(3, run.getK());
        insertRun.setInt(4, run.getD());
        insertRun.setInt(5, run.getI());
        insertRun.setInt(6, run.getPartitions());
        insertRun.setInt(7, run.getCpus());
        insertRun.setString(8, run.getFlavour());
        insertRun.setString(9, run.getSystem());
        insertRun.setString(10, run.getTaskSet());
        insertRun.setLong(11, run.getTimestamp());
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
