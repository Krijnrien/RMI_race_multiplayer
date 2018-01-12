package StatsServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteObject extends Remote {
	void addScore(ScoreObject _rmiObject) throws RemoteException;
}
