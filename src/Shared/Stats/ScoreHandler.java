package Shared.Stats;

import StatsServer.ScoreObject;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ScoreHandler extends UnicastRemoteObject implements IScoreHandler, Serializable {

    private databaseHandler databaseHandler; // = new databaseHandler() same for above
    private Statement statement;

    public ScoreHandler() throws RemoteException {
        databaseHandler = new databaseHandler();
    }

    public void addScore(ScoreObject _rmiObject){
        System.out.println("DOPE!" + _rmiObject.getUsername());
        try {
            databaseHandler.dbOpenConnection();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:race.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.execute("INSERT INTO finish VALUES(?, " + Long.toString(_rmiObject.getFinishTime()) + ")"); //TODO query

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            databaseHandler.dbCloseConnection();
        }
    }

}