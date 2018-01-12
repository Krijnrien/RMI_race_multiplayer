package PositionServer;

import Shared.IupdateCar;
import Shared.carVector;
import PositionServer.publisher.RemotePublisher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class updateCar extends UnicastRemoteObject implements IupdateCar, Serializable {

    private RemotePublisher remotePublisher;


    updateCar(RemotePublisher publisher) throws RemoteException {
        remotePublisher = publisher;
        remotePublisher.registerProperty("car");
    }


    public updateCar() throws RemoteException {
    }

    public void sendCar(carVector _carVector) throws RemoteException {
        System.out.println("Server sendCar: " + _carVector.getName());
        remotePublisher.inform("car", null, _carVector);

    }
}

