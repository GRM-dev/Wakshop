package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.utils.Dialogs;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class WorkshopJoinController extends BorderPane implements Focusable {
	private IWakshop wakshop;
	@FXML
	private TextField tfHost, tfPort;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wakshop = Wakshop.getInstance();
		tfHost.setText("localhost");
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		tfPort.setText(wakshop.getConfigApi().getConfig().getDefaultPort() + "");
	}
	
	@FXML
	private void connectButton_Click(ActionEvent e) {
		String hostS = tfHost.getText();
		String portS = tfPort.getText();
		int port = 0;
		try {
			port = Integer.parseInt(portS);
			if (port <= 0) { throw new NumberFormatException("Port is 0 or less! " + portS); }
			
			if (hostS.length() > 0 && port > 0) {
				try {
					wakshop.connectToServer(hostS, port);
					System.out.println("Connected");
				}
				catch (Exception ex) {
					ex.printStackTrace();
					Dialogs.showExceptionDialog(ex, "Could not connect to server!");
				}
			}
			else {
				Dialogs.showWarningDialog("Wrong input", "Wrong/Empty host and/or port");
			}
		}
		catch (NumberFormatException ex) {
			Dialogs.showExceptionDialog(ex, "Wrong port! " + portS);
			return;
		}
	}
}
