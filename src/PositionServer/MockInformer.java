package PositionServer;

import Shared.carVector;
import PositionServer.publisher.RemotePublisher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

class MockInformer extends UnicastRemoteObject implements Serializable {
    private RemotePublisher remotePublisher;

    MockInformer(RemotePublisher publisher) throws RemoteException {
        remotePublisher = publisher;
        remotePublisher.registerProperty("car");
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                mockCarVector(new carVector("username", 15, 20 - 30, 80));
                i++;
            }
        }, 0, 100);
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

