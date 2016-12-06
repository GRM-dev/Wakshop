package eu.grmdev.wakshop.core.net;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface WakConnection extends Remote {
	
	Server getServer(Client client) throws RemoteException;
	
	void sendDisconnectRequestToServer(UUID id) throws RemoteException;;
	
}
