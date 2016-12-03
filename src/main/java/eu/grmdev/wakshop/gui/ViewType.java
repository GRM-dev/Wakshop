package eu.grmdev.wakshop.gui;

import eu.grmdev.wakshop.gui.controllers.MenuViewController;
import eu.grmdev.wakshop.gui.controllers.SettingsViewController;
import eu.grmdev.wakshop.gui.controllers.WorkshopManageViewController;
import lombok.Getter;

public enum ViewType {
	LOGIN("/views/LoginView.fxml") ,
	MAIN("/views/MainView.fxml") ,
	MENU("/views/MenuView.fxml",MenuViewController.class) ,
	SETTINGS("/views/SettingsView.fxml",SettingsViewController.class) ,
	WORKSHOP_MANAGE("/views/WorkshopManageView.fxml",WorkshopManageViewController.class);
	
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
