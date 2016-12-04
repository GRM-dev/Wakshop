package eu.grmdev.wakshop.gui;

import eu.grmdev.wakshop.gui.controllers.MenuController;
import eu.grmdev.wakshop.gui.controllers.SettingsController;
import eu.grmdev.wakshop.gui.controllers.WorkshopManageController;
import lombok.Getter;

public enum ViewType {
	LOGIN("/views/LoginView.fxml") ,
	MAIN("/views/MainView.fxml") ,
	MENU("/views/MenuView.fxml",MenuController.class) ,
	SETTINGS("/views/SettingsView.fxml",SettingsController.class) ,
	WORKSHOP_MANAGE("/views/WorkshopManageView.fxml",WorkshopManageController.class);
	
	@Getter
	private String path;
	@Getter
	private Class<?> controllerClazz;
	
	private ViewType(String path) {
		this.path = path;
	}
	
	private ViewType(String path, Class<?> controllerClazz) {
		this.path = path;
		this.controllerClazz = controllerClazz;
	}
}
