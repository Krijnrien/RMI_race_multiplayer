package StatsServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObject extends UnicastRemoteObject implements IRemoteObject, Serializable {

    RemoteObject() throws RemoteException {
    }

    public void addScore(ScoreObject _rmiObject) throws RemoteException {
        System.out.println(_rmiObject.getFinishTime());
    }

}