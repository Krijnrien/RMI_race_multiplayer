package Shared.Stats;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

class databaseHandler {

    public Connection connection;
    public Statement statement;

    public ResultSet executeStatement(String query) {
        dbOpenConnection();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public void dropDatabase() {
        try {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete local database?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Files.deleteIfExists(Paths.get("Assets/race.db"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createTablesIfNotExist() {
        dbOpenConnection();
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS finish (id INTEGER PRIMARY KEY AUTOINCREMENT, time STRING);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            dbCloseConnection();
        }
    }

    public void dbOpenConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:race.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dbCloseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
