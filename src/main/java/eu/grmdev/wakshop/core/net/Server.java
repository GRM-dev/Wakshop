package eu.grmdev.wakshop.core.net;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Dialogs;

public class Server extends ConnectionMember {
	private static final long serialVersionUID = 1L;
	private Map<UUID, ClientService> connectedClients;
	
	Server(int port, Workshop workshop) throws UnknownHostException, RemoteException {
		super(port);
		this.workshop = workshop;
		connectedClients = new HashMap<>();
		wakConnection = new WakConnectionImpl(this, port);
		registry = LocateRegistry.createRegistry(port);
	}
	
	@Override
	public void run() {
		try {
			registry.rebind(LABEL, wakConnection);
			System.out.println("Server " + myHost.getHostName() + " listening on: " + myHost.getHostAddress() + ":" + port);
			GuiApp.getInstance().changeViewTo(ViewType.WORKSHOP_MAIN);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Exception while starting server");
		}
	}
	
	public boolean addClient(UUID cid, String name) {
		try {
			if (cid == null) { throw new NullPointerException("Incorrect client id was trying to connect!"); }
			if (connectedClients.containsKey(cid)) { throw new AlreadyBoundException("client with id: " + cid + " already connected."); }
			ClientService client = (ClientService) registry.lookup(cid + name);
			connectedClients.put(cid, client);
			System.out.println("Client connected: '" + cid + "'; Name: " + client.getUsername());
			client.setServer(this);
			client.setWorkshop(workshop);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized void disconnectClient(UUID id) {
		if (connectedClients.containsKey(id)) {
			ClientService c = connectedClients.get(id);
			try {
				c.closeConnectionWithServer();
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
			connectedClients.remove(id);
			System.out.println("Client disconnected: '" + id + "'");
		}
	}
	
	public void closeServer() {
		closeAllClientConnections();
		if (registry != null) {
			try {
				registry.unbind(LABEL);
			}
			catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		}
		if (wakConnection != null) {
			try {
				UnicastRemoteObject.unexportObject(wakConnection, true);
				wakConnection = null;
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void closeAllClientConnections() {
		for (ClientService c : connectedClients.values()) {
			try {
				c.closeConnectionWithServer();
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getHost() {
		return myHost.getHostAddress();
	}
	
	@Override
	public boolean isActive() {
		try {
			String[] list = registry.list();
			if (list == null || list.length == 0) { return false; }
			for (String s : list) {
				if (s.equals(LABEL)) { return wakConnection != null; }
			}
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
}
