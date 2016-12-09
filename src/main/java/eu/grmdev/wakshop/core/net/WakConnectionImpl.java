package eu.grmdev.wakshop.core.net;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import eu.grmdev.wakshop.utils.Dialogs;

public class WakConnectionImpl extends UnicastRemoteObject implements WakConnection {
	private static final long serialVersionUID = 1L;
	private Server server;
	
	protected WakConnectionImpl(Server server, int port) throws RemoteException {
		super(port);
		this.server = server;
	}
	
	@Override
	public synchronized boolean addClient(ClientService client) throws RemoteException {
		try {
			if (!server.addClient(client.getId(), client.getUsername())) { throw new Exception("Adding your client failed"); }
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "False client was trying to connect!");
			return false;
		}
	}
	
	@Override
	public synchronized void sendDisconnectRequestToServer(UUID id) {
		server.disconnectClient(id);
	}
}
