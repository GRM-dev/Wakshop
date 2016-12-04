package eu.grmdev.wakshop.core.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class WakNetHandler {
	private Registry registry;
	private WakConnection wakConnection;
	private InetAddress myHost;
	private String label = "wakConnector";
	
	private WakNetHandler() throws UnknownHostException {
		myHost = InetAddress.getLocalHost();
	}
	
	public WakNetHandler(int port) throws Exception {
		this();
		registry = LocateRegistry.createRegistry(port);
		wakConnection = new WakConnectionImpl(port);
		registry.bind(label, wakConnection);
		System.out.println("Server " + myHost.getHostName() + " listening on: " + myHost.getHostAddress() + ":" + port);
	}
	
	public WakNetHandler(String host, int port) throws Exception {
		this();
		registry = LocateRegistry.getRegistry(host, port);
		wakConnection = (WakConnection) registry.lookup(label);
		System.out.println("Server " + myHost.getHostName() + " listening on: " + host + ":" + port);
	}
	
	public void close() {
		if (registry != null) {
			try {
				registry.unbind(label);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (wakConnection != null) {
			try {
				wakConnection.requestClose();
				UnicastRemoteObject.unexportObject(wakConnection, true);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
