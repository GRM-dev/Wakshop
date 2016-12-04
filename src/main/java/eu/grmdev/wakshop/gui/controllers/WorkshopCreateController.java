package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Focusable;
import eu.grmdev.wakshop.utils.Messages;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class WorkshopCreateController extends BorderPane implements Focusable {
	@FXML
	private ChoiceBox<String> comboBox;
	@FXML
	private TextField tfSessionName, tfPort;
	@FXML
	private Button startBtn;
	private IWakshop wakshop;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wakshop = Wakshop.getInstance();
		comboBox.setTooltip(new Tooltip("Select workshop to start"));
		comboBox.getSelectionModel().selectedItemProperty().addListener(e -> {
			comboBox_Selected(e);
		});
		tfSessionName.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			changeStartButtonState();
		});
		tfPort.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			changeStartButtonState();
		});
		tfPort.setText(wakshop.getConfigApi().getConfig().getDefaultPort() + "");
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		List<String> workshops = wakshop.getWorkshopApi().getAllWorkshopsTitles();
		ObservableList<String> ws = FXCollections.observableArrayList(workshops);
		comboBox.setItems(ws);
	}
	
	private void comboBox_Selected(Observable e) {
		ReadOnlyObjectProperty<?> p = (ReadOnlyObjectProperty<?>) e;
		String v = (String) p.getValue();
		if (v != null && tfSessionName.getText().trim().length() == 0) {
			tfSessionName.setText(v);
		}
		System.out.println("Selected: " + v);
		changeStartButtonState();
	}
	
	private void changeStartButtonState() {
		try {
			String s = tfSessionName.getText();
			String p = tfPort.getText();
			boolean sessionCorrect = s != null && s.length() > 0;
			boolean portCorrect = p != null && p.length() > 0 && Integer.parseInt(p) > 0;
			boolean selectCorrect = comboBox.getSelectionModel().getSelectedIndex() >= 0;
			if (sessionCorrect && portCorrect && selectCorrect) {
				if (startBtn.isDisabled()) {
					startBtn.setDisable(false);
				}
			}
			else if (!startBtn.isDisabled()) {
				startBtn.setDisable(true);
			}
		}
		catch (NumberFormatException e) {
			if (!startBtn.isDisabled()) {
				startBtn.setDisable(true);
			}
		}
	}
	
	@FXML
	private void startButton_Click(ActionEvent e) {
		System.out.println("Start Workshop");
		Thread t = new Thread(() -> {
			try {
				wakshop.startServer(Integer.parseInt(tfPort.getText()));
				System.out.println("After Connnection Establishment");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				Messages.showExceptionDialog(ex, "Could not create server!");
			}
		});
		t.start();
		GuiApp.getInstance().changeViewTo(ViewType.WORKSHOP_MAIN);
	}
}
