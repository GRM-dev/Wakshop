package eu.grmdev.wakshop.core.net;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.core.model.misc.NotifyEvent;

public interface ClientService extends Remote {
	void setWorkshop(Workshop workshop) throws RemoteException;
	
	void setServer(Server server) throws RemoteException;
	
	UUID getId() throws RemoteException;
	
	String getUsername() throws RemoteException;
	
	void closeConnectionWithServer() throws RemoteException;
	
	void workshopUpdated(NotifyEvent event) throws RemoteException;
}