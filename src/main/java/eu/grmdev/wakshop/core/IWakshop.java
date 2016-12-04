package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.api.ConfigApi;
import eu.grmdev.wakshop.core.model.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;

public interface IWakshop {
	public Database getDatabase();
	
	public WorkshopApi getWorkshopApi();
	
	public ConfigApi getConfigApi();
	
	void startServer(int port) throws Exception;
	
	void connectToServer(String host, int port);
}
