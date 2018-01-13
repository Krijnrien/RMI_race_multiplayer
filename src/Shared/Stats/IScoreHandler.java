package Shared.Stats;

import StatsServer.ScoreObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IScoreHandler extends Remote {
	void addScore(ScoreObject _rmiObject) throws RemoteException;
}
