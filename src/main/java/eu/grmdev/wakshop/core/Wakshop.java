package eu.grmdev.wakshop.core;

import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.core.model.api.ConfigApi;
import eu.grmdev.wakshop.core.model.api.WorkshopApi;
import eu.grmdev.wakshop.core.model.database.Database;
import eu.grmdev.wakshop.core.net.ConnectionComponent;
import eu.grmdev.wakshop.core.net.ConnectionMember;
import eu.grmdev.wakshop.core.net.LocalNetHandler;
import eu.grmdev.wakshop.core.net.Server;
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
	public void startServer(int port, Workshop workshop) throws Exception {
		closeAllNetConnections();
		try {
			wakNetHandler = new LocalNetHandler(port, workshop);
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
	
	@Override
	public Workshop getCurrentlyRunningWorkshop() {
		if (isActive()) { return wakNetHandler.getCm().getWorkshop(); }
		return null;
	}
	
	@Override
	public boolean isActive() {
		
		if (wakNetHandler != null) {
			if (wakNetHandler.getCm() != null) {
				if (wakNetHandler.getConnectionComponent() == ConnectionComponent.CLIENT) {
					ConnectionMember c = wakNetHandler.getCm();
					return c.isActive() && c.getWorkshop() != null;
				}
				else if (wakNetHandler.getConnectionComponent() == ConnectionComponent.SERVER) {
					Server s = (Server) wakNetHandler.getCm();
					return s.isActive();
				}
			}
		}
		return false;
	}
	
	@Override
	public ConnectionComponent whatAmIOnCurrentWorkshops() {
		if (isActive()) { return wakNetHandler.getConnectionComponent(); }
		return null;
	}
	
	public static synchronized IWakshop getInstance() {
		if (instance == null) {
			instance = new Wakshop();
		}
		return instance;
	}
}
