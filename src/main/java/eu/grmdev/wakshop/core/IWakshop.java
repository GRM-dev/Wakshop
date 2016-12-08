package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.core.model.api.ConfigApi;
import eu.grmdev.wakshop.core.model.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;

public interface IWakshop {
	void startServer(int port, Workshop w) throws Exception;
	
	void connectToServer(String host, int port);
	
	void closeAllNetConnections();

	public Database getDatabase();

	public WorkshopApi getWorkshopApi();

	public ConfigApi getConfigApi();

	boolean isActive();

	Workshop getCurrentlyRunningWorkshop();
}
