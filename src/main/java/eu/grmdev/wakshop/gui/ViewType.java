package eu.grmdev.wakshop.gui;

import lombok.Getter;

public enum ViewType {
	LOGIN("/views/LoginView.fxml") ,
	MAIN("/views/MainView.fxml") ,
	MENU("/views/MenuView.fxml");
	
	@Getter
	private String path;
	
	private ViewType(String path) {
		this.path = path;
	}
}
