package PositionServer;

import PositionServer.publisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MockServer {
    private static final int portNr = 8087;

    public static void main(String[] args) throws RemoteException {
        System.out.println("Server started");
        MockServer mockServer = new MockServer();
    }

    private MockServer() throws RemoteException {
        RemotePublisher publisher = new RemotePublisher();
        MockInformer informer = new MockInformer(publisher);
        Registry registry = LocateRegistry.createRegistry(portNr);
        registry.rebind("carUpdateRegistry", informer);
    }
}

