package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.database.Database;
import lombok.Getter;

public class Wakshop implements IWakshop {
	@Getter
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
