package eu.grmdev.wakshop.core.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WakConnection extends Remote {
	
	void requestClose() throws RemoteException;
	
	Server getServer(Client client) throws RemoteException;
	
}
