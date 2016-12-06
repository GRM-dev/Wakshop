package eu.grmdev.wakshop.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.function.Consumer;

import eu.grmdev.wakshop.gui.GuiApp;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Dialogs {
	public static void showInformationDialog(String header, String content) {
		runInFxThread(() -> {
			Alert alert = createAlert("Warning", header, content, AlertType.INFORMATION);
			alert.showAndWait();
		});
	}
	
	public static void showWarningDialog(String header, String content) {
		runInFxThread(() -> {
			Alert alert = createAlert("Warning", header, content, AlertType.WARNING);
			alert.showAndWait();
		});
	}
	
	public static void showExceptionDialog(Exception ex, String... additionalInfo) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		
		runInFxThread(() -> {
			Label label = new Label("The exception stacktrace:");
			
			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);
			
			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);
			
			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);
			Alert alert = createAlert("Exception", "There was an Exception while processing request", String.join("\r\n", additionalInfo), AlertType.ERROR);
			alert.getDialogPane().setExpandableContent(expContent);
			alert.showAndWait();
		});
	}
	
	private static void runInFxThread(Runnable r) {
		if (Platform.isFxApplicationThread()) {
			r.run();
		}
		else {
			Platform.runLater(r);
		}
	}
	
	public static Optional<ButtonType> showConfirmationDialog(String title, String header, String content) {
		Alert alert = createAlert(title, header, content, AlertType.CONFIRMATION);
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}
	
	public static Optional<ButtonType> showConfirmationDialog(String title, String header, String content, ButtonType... buttons) {
		Alert alert = createAlert(title, header, content, AlertType.CONFIRMATION);
		alert.getButtonTypes().setAll(buttons);
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}
	
	private static Alert createAlert(String title, String header, String content, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}
	
	public static Optional<String> showInputDialog(String title, String header, String context) {
		return showInputDialog(title, header, context, "", null);
	}
	
	public static Optional<String> showInputDialog(String title, String header, String context, String defValue) {
		return showInputDialog(title, header, context, defValue, null);
	}
	
	public static Optional<String> showInputDialog(String title, String header, String context, String defValue, Consumer<? super String> consumer) {
		TextInputDialog dialog = new TextInputDialog(defValue);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(context);
		
		Optional<String> result = dialog.showAndWait();
		if (consumer != null) {
			result.ifPresent(consumer);
		}
		return result;
	}
	
	public static File showFilePicker(String title) {
		return showFilePicker(title, null);
	}
	
	public static File showFilePicker(String title, String initialDirectory) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		if (initialDirectory != null && initialDirectory.length() > 0) {
			File iniDir = new File(initialDirectory);
			if (iniDir.exists()) {
				fileChooser.setInitialDirectory(iniDir);
			}
		}
		File file = fileChooser.showOpenDialog(GuiApp.getInstance().getCurrentStage());
		return file;
	}
	
	public static File showDirectoryPicker(String title) {
		return showDirectoryPicker(title, null);
	}
	
	public static File showDirectoryPicker(String title, String initialDirectory) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(title);
		if (initialDirectory != null && initialDirectory.length() > 0) {
			File iniDir = new File(initialDirectory);
			if (iniDir.exists()) {
				directoryChooser.setInitialDirectory(iniDir);
			}
		}
		File dir = directoryChooser.showDialog(GuiApp.getInstance().getCurrentStage());
		return dir;
	}
}
