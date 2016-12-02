package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable {
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private TextField tfName;
	
	@FXML
	private void enterButton_Click(ActionEvent e) {
		String name = tfName.getText().trim();
		int l = name.length();
		if (l > 0) {
			if (l < 5) {
				Messages.ShowWarningMessage("Wrong Username!", "Your username is to short!\nIt should be longer than 5 characters");
			}
			else {
				System.out.println("Sign in");
				GuiApp.getInstance().changeView(ViewType.MAIN);
			}
		}
		else {
			Messages.ShowWarningMessage("Wrong Username!", "No username provided!");
		}
	}
}
