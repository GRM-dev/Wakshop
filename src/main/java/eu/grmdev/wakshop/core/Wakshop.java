package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.api.ConfigApi;
import eu.grmdev.wakshop.core.model.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;
import eu.grmdev.wakshop.core.net.LocalNetHandler;
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
	private LocalNetHandler wakNetHandler;
	
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
		closeAllNetConnections();
		try {
			wakNetHandler = new LocalNetHandler(port);
			wakNetHandler.startThread();
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Problem while starting server");
		}
	}
	
	@Override
	public void connectToServer(String host, int port) {
		closeAllNetConnections();
		try {
			wakNetHandler = new LocalNetHandler(host, port);
			wakNetHandler.startThread();
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Could not create server!");
		}
	}
	
	@Override
	public void closeAllNetConnections() {
		if (wakNetHandler != null) {
			wakNetHandler.closeNetConnection();
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
