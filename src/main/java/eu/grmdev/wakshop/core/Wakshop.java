package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.api.ConfigApi;
import eu.grmdev.wakshop.core.model.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;
import eu.grmdev.wakshop.core.net.WakNetHandler;
import eu.grmdev.wakshop.utils.Dialogs;
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
		closeConnections();
		try {
			wakNetHandler = new WakNetHandler(port);
			wakNetHandler.startThread();
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Problem while starting server");
		}
	}
	
	@Override
	public void connectToServer(String host, int port) {
		closeConnections();
		try {
			wakNetHandler = new WakNetHandler(host, port);
			wakNetHandler.startThread();
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Could not create server!");
		}
	}
	
	@Override
	public void closeConnections() {
		if (wakNetHandler != null) {
			wakNetHandler.close();
			wakNetHandler = null;
		}
	}
	
	public static synchronized IWakshop getInstance() {
		if (instance == null) {
			instance = new Wakshop();
		}
		return instance;
	}
}
