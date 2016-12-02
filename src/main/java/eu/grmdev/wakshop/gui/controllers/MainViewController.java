package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainViewController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
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
	
	@FXML
	private void settingsButton_Click(ActionEvent e) {
		System.out.println("Settings");
	}
	
	@FXML
	private void exitButton_Click(ActionEvent e) {
		System.out.println("Exit");
		Main.close();
	}
}
