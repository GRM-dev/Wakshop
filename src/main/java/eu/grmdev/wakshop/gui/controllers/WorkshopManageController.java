package eu.grmdev.wakshop.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.controllers.components.WorkshopDetailsController;
import eu.grmdev.wakshop.utils.Focusable;
import eu.grmdev.wakshop.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WorkshopManageController extends BorderPane implements Focusable {
	@FXML
	private Accordion accordion;
	private Map<Integer, Pair<Workshop, TitledPane>> workshops;
	private static WorkshopManageController instance;
	private Stage newWorkshopsStage;
	private IWakshop wakshop;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		wakshop = Wakshop.getInstance();
		workshops = new HashMap<>();
		reloadWorkshops();
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		
	}
	
	private void reloadWorkshops() {
		Map<Integer, Workshop> workshops = wakshop.getWorkshopApi().getAllWorkshops();
		for (Iterator<Integer> it = workshops.keySet().iterator(); it.hasNext();) {
			int i = it.next();
			if (!this.workshops.containsKey(i)) {
				Workshop workshop = workshops.get(i);
				try {
					TitledPane titledPane = createPane(workshop);
					this.workshops.put(i, new ImmutablePair<>(workshop, titledPane));
					accordion.getPanes().add(titledPane);
				}
				catch (IOException e) {
					e.printStackTrace();
					Messages.showExceptionDialog(e, "Cannot create Workshop pane: " + workshop.getTitle());
				}
			}
		}
		for (Iterator<Integer> it = this.workshops.keySet().iterator(); it.hasNext();) {
			int i = it.next();
			if (!workshops.containsKey(i)) {
				Pair<Workshop, TitledPane> pair = this.workshops.get(i);
				TitledPane titledPane = pair.getValue();
				accordion.getPanes().remove(titledPane);
			}
		}
	}
	
	public void removeWorkshop(Workshop w) {
		Pair<Workshop, TitledPane> pair = this.workshops.get(w.getId());
		accordion.getPanes().remove(pair.getValue());
		this.workshops.remove(pair);
	}
	
	private TitledPane createPane(Workshop workshop) throws IOException {
		WorkshopDetailsController grid = new WorkshopDetailsController(workshop, this);
		TitledPane titledPane = new TitledPane();
		titledPane.setText(workshop.getTitle());
		titledPane.setContent(grid);
		grid.setParentPane(titledPane);
		return titledPane;
	}
	
	@FXML
	private void createButton_Click(ActionEvent e) {
		System.out.println("Create New Workshop");
		URL url = getClass().getResource("/views/CreateNewWorkshopView.fxml");
		FXMLLoader l = new FXMLLoader(url);
		try {
			Parent view = l.load();
			Scene scene = new Scene(view);
			if (newWorkshopsStage != null) {
				newWorkshopsStage.close();
				newWorkshopsStage = null;
			}
			newWorkshopsStage = new Stage();
			newWorkshopsStage.setScene(scene);
			newWorkshopsStage.initOwner(GuiApp.getInstance().getCurrentStage());
			newWorkshopsStage.initModality(Modality.WINDOW_MODAL);
			newWorkshopsStage.setMinWidth(500);
			newWorkshopsStage.setMinHeight(500);
			newWorkshopsStage.setTitle("Wakshop - Create new workshops");
			newWorkshopsStage.getIcons().add(GuiApp.getIcon());
			newWorkshopsStage.showAndWait();
		}
		catch (IOException e1) {
			e1.printStackTrace();
			Messages.showExceptionDialog(e1, "Cannot open 'New Workshops' dialog!");
		}
	}
	
	public static void closeOwnedStage() {
		instance.newWorkshopsStage.close();
		instance.reloadWorkshops();
	}
}
