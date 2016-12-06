package eu.grmdev.wakshop.core.net;

public class LocalNetHandler {
	private ConnectionComponent connectionComponent;
	private Server server;
	private Client client;
	private Thread runningThread;
	
	private LocalNetHandler() {
		
	}
	
	public LocalNetHandler(int port) throws Exception {
		this();
		connectionComponent = ConnectionComponent.SERVER;
		server = Server.createInstance(port);
		runningThread = new Thread(server, "Server Connection Thread");
	}
	
	public LocalNetHandler(String host, int port) throws Exception {
		this();
		connectionComponent = ConnectionComponent.CLIENT;
		client = Client.createClient(host, port);
		runningThread = new Thread(client, "Client Connection Thread");
	}
	
	public void startThread() {
		if (!runningThread.isAlive()) {
			runningThread.start();
		}
	}
	
	public void closeNetConnection() {
		switch (connectionComponent) {
			case CLIENT :
				if (client != null) {
					client.closeConnectionWithServer();
				}
				break;
			case SERVER :
				if (server != null) {
					server.closeServer();
				}
				break;
			default :
				break;
		}
	}
}
