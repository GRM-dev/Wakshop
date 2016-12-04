package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuController extends BorderPane implements Focusable {
	@FXML
	private Button manageButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void manageButton_Click(ActionEvent e) {
		System.out.println("Manage");
		GuiApp.getMainViewInstance().changeViewTo(ViewType.WORKSHOP_MANAGE);
	}
	
	@FXML
	private void createButton_Click(ActionEvent e) {
		System.out.println("Create");
	}
	
	@FXML
	private void joinButton_Click(ActionEvent e) {
		System.out.println("Join");
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		manageButton.requestFocus();
	}
}
