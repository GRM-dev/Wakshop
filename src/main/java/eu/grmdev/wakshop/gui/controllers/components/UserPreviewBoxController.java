package eu.grmdev.wakshop.gui.controllers.components;

import java.net.URL;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.net.ConnectionMember;
import eu.grmdev.wakshop.core.net.Server;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UserPreviewBoxController extends HBox implements Focusable {
	@FXML
	private Label lblUsername;
	@FXML
	private Circle cColored;
	private ConnectionMember member;
	
	public UserPreviewBoxController(ConnectionMember member) {
		this.member = member;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (member instanceof Server) {
			cColored.setFill(Color.BLUE);
		}
		else {
			cColored.setFill(Color.GREEN);
		}
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		
	}
	
}
