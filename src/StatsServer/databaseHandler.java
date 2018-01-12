package StatsServer;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

class databaseHandler {

	public Connection connection;
	private Statement statement;

	public ResultSet executeStatement(String query) {
		dbOpenConnection();
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(query);
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return rs;
	}

	void dropDatabase() {
		try {
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete local database?", "Warning", JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION) {
				Files.deleteIfExists(Paths.get("race.db"));
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}

	void createTablesIfNotExist() {
		try {
			dbOpenConnection();
			statement.executeUpdate("CREATE ABLE ..... ");//todo

		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			dbCloseConnection();
		}
	}

	void dbOpenConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:race.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	void dbCloseConnection() {
		try {
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}


}
