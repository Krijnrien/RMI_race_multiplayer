package StatsServer;

import Shared.Stats.ScoreHandler;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StatsServer {
	private static final int portNr = 8086;
	private Registry registry = null;
	private ScoreHandler stats = null;

	public static void main(String[] args) throws RemoteException {
		StatsServer statsServer = new StatsServer();
	}

	public StatsServer() throws RemoteException {
		stats = new ScoreHandler();
		registry = LocateRegistry.createRegistry(portNr);
		registry.rebind("finishRegistry", stats);
	}


}