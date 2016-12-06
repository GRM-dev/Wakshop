package eu.grmdev.wakshop.core.net;

public class WakNetHandler {
	private ConnectionComponent connectionComponent;
	private Server server;
	private Client client;
	private Thread runningThread;
	
	private WakNetHandler() {
		
	}
	
	public WakNetHandler(int port) throws Exception {
		this();
		connectionComponent = ConnectionComponent.SERVER;
		server = Server.createInstance(port);
		runningThread = new Thread(server);
	}
	
	public WakNetHandler(String host, int port) throws Exception {
		this();
		connectionComponent = ConnectionComponent.CLIENT;
		client = Client.createClient(host, port);
		runningThread = new Thread(client);
	}
	
	public void startThread() {
		if (!runningThread.isAlive()) {
			runningThread.start();
		}
	}

	public void close() {
		switch (connectionComponent) {
			case CLIENT :
				if (client != null) {
					client.close();
				}
				break;
			case SERVER :
				if (server != null) {
					server.close();
				}
				break;
			default :
				break;
		}
	}
}
