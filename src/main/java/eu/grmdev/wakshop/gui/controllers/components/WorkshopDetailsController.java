package eu.grmdev.wakshop.gui.controllers.components;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.controllers.WorkshopManageController;
import eu.grmdev.wakshop.utils.Focusable;
import eu.grmdev.wakshop.utils.Messages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import lombok.Setter;

public class WorkshopDetailsController extends GridPane implements Focusable {
	private Workshop workshop;
	@FXML
	private TextField tfTitle;
	@FXML
	private CheckBox cbChat, cbFile;
	@Setter
	private TitledPane parentPane;
	private WorkshopManageController controller;
	
	public WorkshopDetailsController(Workshop workshop, WorkshopManageController controller) throws IOException {
		this.workshop = workshop;
		this.controller = controller;
		String path = "/views/components/WorkshopDetailsView.fxml";
		URL viewUrl = GuiApp.class.getResource(path);
		if (viewUrl == null) { throw new IOException("Can't find file: " + path); }
		FXMLLoader loader = new FXMLLoader(viewUrl);
		loader.setRoot(this);
		loader.setController(this);
		Parent view = loader.load();
		view.autosize();
		onFocus(this);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@Override
	public void onFocus(Object requestingObject) {
		tfTitle.setText(workshop.getTitle());
		cbChat.setSelected(workshop.isChatEnabled());
		cbFile.setSelected(workshop.isFileTransferEnabled());
	}
	
	@FXML
	private void saveButton_Click(ActionEvent e) {
		System.out.println("Workshop - Save: " + workshop.getTitle());
		workshop.setTitle(tfTitle.getText());
		workshop.setChatEnabled(cbChat.isSelected());
		workshop.setFileTransferEnabled(cbFile.isSelected());
		workshop = Main.getWakshop().getWorkshopApi().save(workshop);
		parentPane.setText(workshop.getTitle());
	}
	
	@FXML
	private void removeButton_Click(ActionEvent e) {
		System.out.println("Workshop - Remove: " + workshop.getTitle());
		Optional<ButtonType> dialog = Messages.showConfirmationDialog("Workshops", "Removing workshops: " + workshop.getTitle(), "Are you sure to remove this workshops?", ButtonType.YES, ButtonType.NO);
		if (dialog.isPresent() && dialog.get() == ButtonType.YES) {
			Main.getWakshop().getWorkshopApi().remove(workshop);
			this.controller.removeWorkshop(workshop);
		}
	}
}
