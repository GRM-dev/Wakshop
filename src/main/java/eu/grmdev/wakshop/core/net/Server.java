package eu.grmdev.wakshop.core.net;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.grmdev.wakshop.utils.Dialogs;

public class Server implements Serializable, Runnable {
	private static final long serialVersionUID = 1L;
	private static Server instance;
	private static final String LABEL = "wakConnector";
	private Map<UUID, Client> connectedClients;
	private Registry registry;
	private InetAddress myHost;
	private WakConnection wakConnection;
	private int port;
	
	private Server(int port) throws UnknownHostException, RemoteException {
		connectedClients = new HashMap<>();
		this.port = port;
		myHost = InetAddress.getLocalHost();
		wakConnection = new WakConnectionImpl(this, port);
	}
	
	@Override
	public void run() {
		try {
			registry = LocateRegistry.createRegistry(port);
			registry.bind(LABEL, wakConnection);
			System.out.println("Server " + myHost.getHostName() + " listening on: " + myHost.getHostAddress() + ":" + port);
		}
		catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Exception while starting server");
		}
	}
	
	public void addClient(Client client) throws Exception {
		UUID cid = client.getId();
		if (client == null || cid == null) { throw new NullPointerException("Incorrect client type was trying to connect!"); }
		if (connectedClients.containsKey(cid)) { throw new AlreadyBoundException("client with id: " + cid + " already connected."); }
		connectedClients.put(cid, client);
		System.out.println("Client added: '" + cid + "'; Name: " + client.getUsername());
	}
	
	public static Server createInstance(int port) throws UnknownHostException, RemoteException {
		if (instance != null) {
			instance.closeServer();
			instance = null;
		}
		instance = new Server(port);
		return instance;
	}
	
	public void disconnectClient(UUID id) {
		if (connectedClients.containsKey(id)) {
			connectedClients.get(id).closeConnectionWithServer();
			connectedClients.remove(id);
		}
	}
	
	public void closeServer() {
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
				closeAllClientConnections();
				UnicastRemoteObject.unexportObject(wakConnection, true);
				registry = null;
				wakConnection = null;
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeAllClientConnections() {
		for (Client c : connectedClients.values()) {
			c.setClosing(true);
			c.closeConnectionWithServer();
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public String getHost() {
		return myHost.getHostAddress();
	}
}
