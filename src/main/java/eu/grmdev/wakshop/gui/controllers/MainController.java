package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Focusable;
import eu.grmdev.wakshop.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainController implements Focusable {
	@FXML
	private BorderPane borderPane;
	@FXML
	private VBox vBox;
	@FXML
	private Button homeButton, settingsButton;
	private Map<ViewType, Initializable> innerControllers = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiApp.setMainViewController(this);
		changeViewTo(ViewType.MENU);
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		
	}
	
	public void changeViewTo(ViewType viewType) {
		try {
			if (!innerControllers.containsKey(viewType)) {
				Focusable c = (Focusable) viewType.getControllerClazz().newInstance();
				innerControllers.put(viewType, c);
				GuiApp.getNode(viewType, c);
			}
			vBox.getChildren().clear();
			homeButton.setDisable(viewType == ViewType.MENU);
			settingsButton.setDisable(viewType == ViewType.SETTINGS);
			Node node = (Node) innerControllers.get(viewType);
			vBox.getChildren().add(node);
			borderPane.setCenter(vBox);
			((Focusable) node).onFocus(this);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			Messages.showExceptionDialog(e);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Messages.showExceptionDialog(e);
		}
	}
	
	@FXML
	private void logoutButton_Click(ActionEvent e) {
		System.out.println("Sign out");
		changeViewTo(ViewType.MENU);
		GuiApp.getInstance().changeViewTo(ViewType.LOGIN);
	}
	
	@FXML
	private void settingsButton_Click(ActionEvent e) {
		System.out.println("Settings");
		changeViewTo(ViewType.SETTINGS);
	}
	
	@FXML
	private void homeButton_Click(ActionEvent e) {
		System.out.println("Home Screen");
		changeViewTo(ViewType.MENU);
	}
	
	@FXML
	private void exitButton_Click(ActionEvent e) {
		System.out.println("Exit");
		Main.close();
	}
}
