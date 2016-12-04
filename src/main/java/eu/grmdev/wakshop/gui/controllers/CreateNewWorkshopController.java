package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.core.model.Workshop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateNewWorkshopController implements Initializable {
	@FXML
	private TextField tfTitle;
	@FXML
	private CheckBox cbChatEnabled, cbFileTransferEnabled;
	@FXML
	private Label lblMsg;
	private Wakshop wakshop;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wakshop = Main.getWakshop();
		tfTitle.requestFocus();
	}
	
	@FXML
	private void cancelButton_Click(ActionEvent e) {
		System.out.println("Create New Workshop - Cancel");
		WorkshopManageController.closeOwnedStage();
	}
	
	@FXML
	private void createButton_Click(ActionEvent e) {
		System.out.println("Create New Workshop - Create");
		String title = tfTitle.getText();
		if (title == null || title.length() == 0) {
			lblMsg.setText("Title must not be empty!");
			return;
		}
		if (wakshop.getWorkshopApi().exists(title)) {
			lblMsg.setText("Workshop with title '" + title + "' already exists!");
			return;
		}
		boolean chat = cbChatEnabled.isSelected();
		boolean file = cbFileTransferEnabled.isSelected();
		Workshop w = new Workshop();
		w.setTitle(title);
		w.setChatEnabled(chat);
		w.setFileTransferEnabled(file);
		wakshop.getWorkshopApi().save(w);
		WorkshopManageController.closeOwnedStage();
	}
}
