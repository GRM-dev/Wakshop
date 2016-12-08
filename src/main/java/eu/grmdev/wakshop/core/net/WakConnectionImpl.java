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
	public synchronized Client addClient(Client client) throws RemoteException {
		try {
			if ((client = server.addClient(client)) == null) { throw new Exception("Adding your client failed"); }
			return client;
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "False client was trying to connect!");
			return null;
		}
	}
	
	@Override
	public synchronized void sendDisconnectRequestToServer(UUID id) {
		server.disconnectClient(id);
	}
}
