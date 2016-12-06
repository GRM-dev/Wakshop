package eu.grmdev.wakshop.core.net;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.utils.Dialogs;
import lombok.Getter;

public class Client implements Serializable, Runnable {
	private static final long serialVersionUID = 1L;
	private static Client instance;
	private static final String LABEL = "wakConnector";
	private Server server;
	private Registry registry;
	private InetAddress myHost;
	private WakConnection wakConnection;
	@Getter
	private UUID id;
	private String host;
	private int port;
	
	private Client(String host, int port) throws UnknownHostException {
		this.host = host;
		this.port = port;
		myHost = InetAddress.getLocalHost();
		id = UUID.randomUUID();
	}
	
	@Override
	public void run() {
		try {
			registry = LocateRegistry.getRegistry(host, port);
			wakConnection = (WakConnection) registry.lookup(LABEL);
			server = wakConnection.getServer(this);
			System.out.println("Client " + myHost.getHostName() + " connected to: " + server.getHost() + ":" + server.getPort());
		}
		catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Problem while starting client to server connection!");
		}
	}
	
	public static Client createClient(String host, int port) throws UnknownHostException {
		if (instance != null) {
			instance.close();
			instance = null;
		}
		instance = new Client(host, port);
		return instance;
	}
	
	public void close() {
		if (registry != null) {
			try {
				registry.unbind(LABEL);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (wakConnection != null) {
			try {
				wakConnection.requestClose();
				// UnicastRemoteObject.unexportObject(wakConnection, true);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getUsername() {
		return Wakshop.getInstance().getConfigApi().getConfig().getUsername();
	}
}
