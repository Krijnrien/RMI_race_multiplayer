package Server;

import Shared.carVector;
import publisher.RemotePublisher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

class Informer extends UnicastRemoteObject implements Serializable {
    private RemotePublisher remotePublisher;

    Informer(RemotePublisher publisher) throws RemoteException {
        remotePublisher = publisher;
        remotePublisher.registerProperty("car");
        Timer timer = new Timer();

        /*timer.schedule(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                mockCarVector(i);
                i++;
            }
        }, 0, 100);*/
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

