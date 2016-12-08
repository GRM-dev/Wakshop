package eu.grmdev.wakshop.gui.controllers.workshops;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WorkshopMainController implements Focusable {
	private IWakshop wakshop;
	@FXML
	private Label lblTitle;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wakshop = Wakshop.getInstance();
		Workshop workshop = wakshop.getCurrentlyRunningWorkshop();
		String title = workshop.getTitle();
		lblTitle.setText(title);
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		
	}
	
	@FXML
	private void exitWorkshopsButton_Click(ActionEvent e) {
		System.out.println("Exit Workshops");
		wakshop.closeAllNetConnections();
		GuiApp.getInstance().changeViewTo(ViewType.MAIN);
	}
}
