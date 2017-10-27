package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IupdateCar extends Remote {
    void sendCar(carVector _carVector) throws RemoteException;
}
