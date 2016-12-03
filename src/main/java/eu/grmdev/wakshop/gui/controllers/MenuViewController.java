package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MenuViewController extends BorderPane implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
