package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.api.ConfigApi;
import eu.grmdev.wakshop.core.model.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;
import eu.grmdev.wakshop.core.net.WakNetHandler;
import eu.grmdev.wakshop.utils.Messages;
import lombok.Getter;

public class Wakshop implements IWakshop {
	private static IWakshop instance;
	@Getter
	private Database database;
	@Getter
	private WorkshopApi workshopApi;
	@Getter
	private ConfigApi configApi;
	private WakNetHandler wakNetHandler;
	
	private Wakshop() {
		Wakshop.instance = this;
		try {
			this.database = new Database();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.workshopApi = new WorkshopApi();
		this.configApi = new ConfigApi();
	}
	
	@Override
	public void startServer(int port) throws Exception {
		if (wakNetHandler != null) {
			closeConnections();
		}
		try {
			wakNetHandler = new WakNetHandler(port);
		}
		catch (Exception e) {
			e.printStackTrace();
			Messages.showExceptionDialog(e, "Problem while starting server");
		}
	}
	
	@Override
	public void connectToServer(String host, int port) {
		if (wakNetHandler != null) {
			closeConnections();
		}
		Thread t = new Thread(() -> {
			try {
				wakNetHandler = new WakNetHandler(host, port);
			}
			catch (Exception e) {
				e.printStackTrace();
				Messages.showExceptionDialog(e, "Could not create server!");
			}
		});
		t.start();
	}
	
	@Override
	public void closeConnections() {
		wakNetHandler.close();
		wakNetHandler = null;
	}
	
	public static synchronized IWakshop getInstance() {
		if (instance == null) {
			instance = new Wakshop();
		}
		return instance;
	}
}
