package eu.grmdev.wakshop.core.net;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class WakConnectionImpl extends UnicastRemoteObject implements WakConnection {
	private static final long serialVersionUID = 1L;
	
	protected WakConnectionImpl(int port) throws RemoteException {
		super(port);
	}
	
	@Override
	public void requestClose() {
		// TODO Auto-generated method stub
		
	}
	
}
