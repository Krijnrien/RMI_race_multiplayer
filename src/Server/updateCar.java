package Server;

import Shared.IupdateCar;
import Shared.carVector;
import publisher.RemotePublisher;

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
        remotePublisher.inform("car", null, new carVector(_carVector.getLocationX(), _carVector.getLocationY() - 30, _carVector.getDirection()));

    }
}

