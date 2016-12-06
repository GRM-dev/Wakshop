package eu.grmdev.wakshop.core.net;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Dialogs;
import lombok.Getter;

public class Client implements Serializable, Runnable {
	private static final long serialVersionUID = 1L;
	private static Client instance;
	private static final String LABEL = "wakConnector";
	private Server server;
	private Registry registry;
	private InetAddress myHost;
	private WakConnection wakConnector;
	@Getter
	private UUID id;
	private String host;
	private int port;
	@Getter
	private boolean closing;
	
	private Client(String host, int port) throws UnknownHostException {
		this.host = host;
		this.port = port;
		this.closing = false;
		this.myHost = InetAddress.getLocalHost();
		this.id = UUID.randomUUID();
	}
	
	@Override
	public void run() {
		try {
			registry = LocateRegistry.getRegistry(host, port);
			wakConnector = (WakConnection) registry.lookup(LABEL);
			server = wakConnector.getServer(this);
			System.out.println("Client " + myHost.getHostName() + " connected to: " + server.getHost() + ":" + server.getPort());
		}
		catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Problem while starting client to server connection!");
		}
	}
	
	public static Client createClient(String host, int port) throws UnknownHostException {
		if (instance != null) {
			instance.closeConnectionWithServer();
			instance = null;
		}
		instance = new Client(host, port);
		return instance;
	}
	
	public void closeConnectionWithServer() {
		if (wakConnector != null) {
			try {
				if (!closing) {
					closing = true;
					wakConnector.sendDisconnectRequestToServer(this.id);
				}
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getUsername() {
		return Wakshop.getInstance().getConfigApi().getConfig().getUsername();
	}
	
	public void setClosing(boolean closing) {
		this.closing = closing;
		if (closing) {
			GuiApp.getInstance().changeViewTo(ViewType.WORKSHOP_JOIN);
			Dialogs.showInformationDialog("Host has finished workshop.", "Thanks for attending");
		}
	}
}
