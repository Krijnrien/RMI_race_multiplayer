package Server;

import publisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
	private static final int portNr = 8082;

	public static void main(String[] args) throws RemoteException {
		System.out.println("Server started");
		RMIServer rmiServer = new RMIServer();
	}

	private RMIServer() throws RemoteException {
		RemotePublisher publisher = new RemotePublisher();
		Informer informer = new Informer(publisher);
		Registry registry = LocateRegistry.createRegistry(portNr);
		registry.rebind("Effectenbeurs", informer);
		registry.rebind("publisher", publisher);
	}
}

