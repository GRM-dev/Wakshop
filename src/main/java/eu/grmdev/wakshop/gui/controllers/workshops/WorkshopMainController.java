package eu.grmdev.wakshop.gui.controllers.workshops;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WorkshopMainController implements Focusable {
	private IWakshop wakshop;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wakshop = Wakshop.getInstance();
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void exitWorkshopsButton_Click(ActionEvent e) {
		System.out.println("Exit Workshops");
		wakshop.closeConnections();
		GuiApp.getInstance().changeViewTo(ViewType.MAIN);
	}
}
