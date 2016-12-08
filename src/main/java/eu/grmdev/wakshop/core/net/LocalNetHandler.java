package eu.grmdev.wakshop.core.net;

import java.util.Timer;

import eu.grmdev.wakshop.core.model.Workshop;
import lombok.Getter;

public class LocalNetHandler {
	@Getter
	private ConnectionComponent connectionComponent;
	@Getter
	private ConnectionMember cm;
	private Thread runningThread;
	private Timer timer;
	
	private LocalNetHandler() {}
	
	public LocalNetHandler(int port, Workshop workshop) throws Exception {
		this();
		connectionComponent = ConnectionComponent.SERVER;
		cm = ConnectionMember.createInstance(connectionComponent, null, port, workshop);
		runningThread = new Thread(cm, "Server Connection Thread");
	}
	
	public LocalNetHandler(String host, int port) throws Exception {
		this();
		connectionComponent = ConnectionComponent.CLIENT;
		cm = ConnectionMember.createInstance(connectionComponent, host, port, null);
		runningThread = new Thread(cm, "Client Connection Thread");
		timer = new Timer("Connection Status Timer", true);
	}
	
	public void startThread() {
		if (!runningThread.isAlive()) {
			runningThread.start();
			if (connectionComponent == ConnectionComponent.CLIENT) {
				StatusTimerTask statusTiemerTask = ((Client) cm).getStatusTimerTask();
				timer.scheduleAtFixedRate(statusTiemerTask, 1, 2000);
			}
		}
	}
	
	public void closeNetConnection() {
		if (cm == null) { return; }
		switch (connectionComponent) {
			case CLIENT :
				((Client) cm).closeConnectionWithServer();
				break;
			case SERVER :
				((Server) cm).closeServer();
				break;
			default :
				break;
		}
	}
}
