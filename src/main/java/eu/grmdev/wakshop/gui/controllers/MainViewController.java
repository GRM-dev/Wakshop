package eu.grmdev.wakshop.gui.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	@FXML
	private BorderPane borderPane;
	@FXML
	private VBox vBox;
	@FXML
	private Button homeButton;
	private Map<ViewType, Initializable> innerControllers = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GuiApp.setMainViewController(this);
		changeViewTo(ViewType.MENU);
	}
	
	public void changeViewTo(ViewType viewType) {
		try {
			if (!innerControllers.containsKey(viewType)) {
				Initializable c;
				c = (Initializable) viewType.getControllerClazz().newInstance();
				innerControllers.put(viewType, c);
				GuiApp.getNode(viewType, c);
			}
			Node node = (Node) innerControllers.get(viewType);
			vBox.getChildren().clear();
			homeButton.setDisable(viewType == ViewType.MENU);
			vBox.getChildren().add(node);
			borderPane.setCenter(vBox);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
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
