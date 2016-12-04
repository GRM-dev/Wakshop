package eu.grmdev.wakshop.gui.controllers;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.core.model.Config;
import eu.grmdev.wakshop.utils.Focusable;
import eu.grmdev.wakshop.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SettingsController extends BorderPane implements Focusable {
	@FXML
	private Label lblSaveDirPath, lblMsg;
	@FXML
	private CheckBox cbSaveName;
	private Config config;
	private Wakshop wakshop;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.wakshop = Wakshop.getInstance();
		this.config = wakshop.getConfigApi().getConfig();
		lblSaveDirPath.setText(config.getSaveDirPath());
		cbSaveName.setSelected(config.isSaveName());
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		
	}
	
	@FXML
	private void defaultsButton_Click(ActionEvent e) {
		System.out.println("Defaults");
		Optional<ButtonType> result = Messages.showConfirmationDialog("Confirmation", "Restore Config to default values", "Do you really want to restore default values?");
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Config config = Config.getDefaultConfig();
			config.setId(this.config.getId());
			wakshop.getConfigApi().save(config);
			initialize(null, null);
		}
	}
	
	@FXML
	private void saveButton_Click(ActionEvent e) {
		System.out.println("Save");
		config.setSaveDirPath(lblSaveDirPath.getText());
		config.setSaveName(cbSaveName.isSelected());
		wakshop.getConfigApi().save(config);
	}
	
	@FXML
	private void restoreButton_Click(ActionEvent e) {
		System.out.println("Restore");
		initialize(null, null);
	}
	
	@FXML
	private void changeSaveDirPathButton_Click(ActionEvent e) {
		System.out.println("Change save dir path");
		String path = lblSaveDirPath.getText();
		if (path == null || path.trim().length() <= 0 || !new File(path).exists()) {
			path = null;
		}
		File dir = Messages.showDirectoryPicker("Select directory to save app data", path);
		if (dir != null) {
			lblSaveDirPath.setText(dir.getPath());
		}
	}
	
	// @FXML
	// private void defaultsButton_Click(ActionEvent e) {
	// System.out.println("Defaults");
	// }
}
