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
	private Map<UUID, Client> connectedClients;
	
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
			registry.bind(LABEL, wakConnection);
			System.out.println("Server " + myHost.getHostName() + " listening on: " + myHost.getHostAddress() + ":" + port);
			GuiApp.getInstance().changeViewTo(ViewType.WORKSHOP_MAIN);
		}
		catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Exception while starting server");
		}
	}
	
	public Client addClient(Client client) {
		try {
			UUID cid;
			if (client == null || (cid = client.getId()) == null) { throw new NullPointerException("Incorrect client type was trying to connect!"); }
			if (connectedClients.containsKey(cid)) { throw new AlreadyBoundException("client with id: " + cid + " already connected."); }
			connectedClients.put(cid, client);
			System.out.println("Client added: '" + cid + "'; Name: " + client.getUsername());
			client.setServer(this);
			client.setWorkshop(workshop);
			return client;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void disconnectClient(UUID id) {
		if (connectedClients.containsKey(id)) {
			Client c = connectedClients.get(id);
			c.closeConnectionWithServer();
			connectedClients.remove(id);
			System.out.println("Client: '" + id + "' disconnected");
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
		for (Client c : connectedClients.values()) {
			c.closeConnectionWithServer();
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
