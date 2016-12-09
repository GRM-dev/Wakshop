package eu.grmdev.wakshop.core.net;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import eu.grmdev.wakshop.core.model.Workshop;
import lombok.Getter;
import lombok.Setter;

public abstract class ConnectionMember implements Runnable, Serializable {
	private static final long serialVersionUID = 1L;
	protected static final String LABEL = "wakConnector";
	protected Registry registry;
	protected InetAddress myHost;
	protected WakConnection wakConnection;
	@Getter
	@Setter
	protected Workshop workshop;
	@Getter
	protected int port;
	
	protected ConnectionMember(int port) throws UnknownHostException {
		myHost = InetAddress.getLocalHost();
		this.port = port;
	}
	
	public abstract boolean isActive();
	
	public static ConnectionMember createNewInstance(ConnectionComponent comp, String host, int port, Workshop workshop) throws UnknownHostException, RemoteException {
		if (comp == ConnectionComponent.CLIENT) {
			return new Client(host, port);
		}
		else {
			return new Server(port, workshop);
		}
	}
}
