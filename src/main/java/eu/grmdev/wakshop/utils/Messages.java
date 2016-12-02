package eu.grmdev.wakshop.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Messages {
	public static void ShowWarningMessage(String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
