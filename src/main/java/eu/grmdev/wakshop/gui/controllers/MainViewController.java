package eu.grmdev.wakshop.gui.controllers;

import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainViewController {
	
	@FXML
	private void logoutButton_Click(ActionEvent e) {
		System.out.println("Sign out");
		GuiApp.getInstance().changeView(ViewType.LOGIN);
	}
	
	@FXML
	private void manageButton_Click(ActionEvent e) {
		System.out.println("Manage");
	}
	
	@FXML
	private void createButton_Click(ActionEvent e) {
		System.out.println("Create");
	}
	
	@FXML
	private void joinButton_Click(ActionEvent e) {
		System.out.println("Join");
	}
}
