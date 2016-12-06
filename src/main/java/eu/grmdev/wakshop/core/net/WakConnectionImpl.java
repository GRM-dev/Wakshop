package eu.grmdev.wakshop.core.net;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import eu.grmdev.wakshop.utils.Dialogs;

public class WakConnectionImpl extends UnicastRemoteObject implements WakConnection {
	private static final long serialVersionUID = 1L;
	private Server server;
	
	protected WakConnectionImpl(Server server, int port) throws RemoteException {
		super(port);
		this.server = server;
	}
	
	@Override
	public Server getServer(Client client) throws RemoteException {
		try {
			server.addClient(client);
			return server;
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "False client was trying to connect!");
			return null;
		}
	}
	
	@Override
	public void requestClose() {
		for (Client c : server.getConnectedClients().values()) {
			c.close();
		}
	}
	
}
