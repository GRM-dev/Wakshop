package eu.grmdev.wakshop.core.net;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.UUID;

import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Dialogs;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Client extends ConnectionMember implements Serializable, Runnable {
	private static final long serialVersionUID = 1L;
	@Getter
	private UUID id;
	@Setter(value = AccessLevel.PACKAGE)
	private Server server;
	private WakConnection wakConnector;
	private String host;
	@Getter
	private StatusTimerTask statusTimerTask;
	@Getter
	private boolean connected;
	@Getter
	private boolean closing;
	
	Client(String host, int port) throws UnknownHostException {
		super(port);
		this.host = host;
		this.closing = false;
		this.id = UUID.randomUUID();
		statusTimerTask = new StatusTimerTask();
	}
	
	@Override
	public void run() {
		try {
			registry = LocateRegistry.getRegistry(host, port);
			wakConnector = (WakConnection) registry.lookup(LABEL);
			Client c = wakConnector.addClient(this);
			this.setServer(c.server);
			this.setWorkshop(c.workshop);
			System.out.println("Client " + myHost.getHostName() + " connected to: " + server.getHost() + ":" + server.getPort());
			GuiApp.getInstance().changeViewTo(ViewType.WORKSHOP_MAIN);
		}
		catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Problem while starting client to server connection!");
		}
	}
	
	public void closeConnectionWithServer() {
		if (wakConnector != null) {
			try {
				if (!closing) {
					setClosing(true);
					wakConnector.sendDisconnectRequestToServer(this.id);
				}
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setClosing(boolean closing) {
		this.closing = closing;
		if (closing) {
			statusTimerTask.setClosing(true);
		}
	}
	
	public String getUsername() {
		return Wakshop.getInstance().getConfigApi().getConfig().getUsername();
	}
	
	@Override
	public boolean isActive() {
		return this.server != null;
	}
	
	@Override
	public Workshop getWorkshop() {
		if (server != null) { return server.getWorkshop(); }
		return null;
	}
}
