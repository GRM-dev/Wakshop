package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	@FXML
	private BorderPane borderPane;
	@FXML
	private VBox vBox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MenuViewController c = new MenuViewController();
		vBox.getChildren().add(c);
		borderPane.setCenter(vBox);
	}
	
	@FXML
	private void logoutButton_Click(ActionEvent e) {
		System.out.println("Sign out");
		GuiApp.getInstance().changeViewTo(ViewType.LOGIN);
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
