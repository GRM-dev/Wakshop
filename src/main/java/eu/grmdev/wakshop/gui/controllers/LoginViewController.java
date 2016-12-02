package eu.grmdev.wakshop.gui.controllers;

import eu.grmdev.wakshop.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginViewController {
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
				System.out.println("Logged in");
				
			}
		}
		else {
			Messages.ShowWarningMessage("Wrong Username!", "No username provided!");
		}
	}
}
