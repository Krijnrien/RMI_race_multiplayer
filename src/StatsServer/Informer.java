package StatsServer;

import PositionServer.publisher.RemotePublisher;
import Shared.carVector;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

class Informer extends UnicastRemoteObject implements Serializable {
    private RemotePublisher remotePublisher;

    Informer(RemotePublisher publisher) throws RemoteException {
        remotePublisher = publisher;
        remotePublisher.registerProperty("car");
        Timer timer = new Timer();
    }

    public void mockCarVector(carVector _carVector) {
        try {
            System.out.println("Sending from server!");
            remotePublisher.inform("car", null, _carVector);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

