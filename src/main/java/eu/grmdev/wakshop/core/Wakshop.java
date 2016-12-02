package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.database.Database;

public class Wakshop implements IWakshop {
	
	private Database database;
	
	public Wakshop() {
		try {
			this.database = new Database();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
