package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.api.ConfigApi;
import eu.grmdev.wakshop.core.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;
import lombok.Getter;

public class Wakshop implements IWakshop {
	@Getter
	private Database database;
	@Getter
	private static Wakshop instance;
	@Getter
	private WorkshopApi workshopApi;
	@Getter
	private ConfigApi configApi;
	
	public Wakshop() {
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
	
}
