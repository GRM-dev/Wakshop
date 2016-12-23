package eu.grmdev.wakshop.gui;

import java.lang.reflect.Constructor;

import eu.grmdev.wakshop.core.net.ConnectionMember;
import eu.grmdev.wakshop.gui.controllers.MenuController;
import eu.grmdev.wakshop.gui.controllers.SettingsController;
import eu.grmdev.wakshop.gui.controllers.WorkshopCreateController;
import eu.grmdev.wakshop.gui.controllers.WorkshopJoinController;
import eu.grmdev.wakshop.gui.controllers.WorkshopManageController;
import eu.grmdev.wakshop.gui.controllers.components.UserPreviewBoxController;
import eu.grmdev.wakshop.utils.Focusable;
import lombok.Getter;

public enum ViewType {
	LOGIN("/views/LoginView.fxml") ,
	MAIN("/views/MainView.fxml") ,
	MENU("/views/MenuView.fxml",MenuController.class) ,
	SETTINGS("/views/SettingsView.fxml",SettingsController.class) ,
	WORKSHOP_MANAGE("/views/WorkshopManageView.fxml",WorkshopManageController.class) ,
	WORKSHOP_CREATE("/views/WorkshopCreateView.fxml",WorkshopCreateController.class) ,
	WORKSHOP_JOIN("/views/WorkshopJoinView.fxml",WorkshopJoinController.class) ,
	WORKSHOP_MAIN("/views/workshops/WorkshopMainView.fxml") ,
	WORKSHOP_MAIN_USER_PREVIEW("/views/components/UserPreviewBox.fxml",UserPreviewBoxController.class);
	
	@Getter
	private String path;
	@Getter
	private Class<? extends Focusable> controllerClazz;
	
	private ViewType(String path) {
		this.path = path;
	}
	
	private ViewType(String path, Class<? extends Focusable> controllerClazz) {
		this.path = path;
		this.controllerClazz = controllerClazz;
	}
	
	public Focusable newFor(ConnectionMember client) throws Exception {
		Constructor<? extends Focusable> construct = controllerClazz.getDeclaredConstructor(ConnectionMember.class);
		return construct.newInstance(client);
	}
}
