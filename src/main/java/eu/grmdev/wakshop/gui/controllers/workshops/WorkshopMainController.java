package eu.grmdev.wakshop.gui.controllers.workshops;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.core.model.Workshop;
import eu.grmdev.wakshop.core.net.ClientService;
import eu.grmdev.wakshop.core.net.ConnectionComponent;
import eu.grmdev.wakshop.core.net.ConnectionMember;
import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.gui.controllers.components.UserPreviewBoxController;
import eu.grmdev.wakshop.utils.Dialogs;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

public class WorkshopMainController implements Focusable {
	private IWakshop wakshop;
	@FXML
	private Label lblTitle, lblHostType;
	@FXML
	private TitledPane tpUsers;
	private GridPane gp;
	private Map<ViewType, Focusable> innerControllers = new HashMap<>();
	private Map<ConnectionMember, Focusable> usersMap = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wakshop = Wakshop.getInstance();
		Workshop workshop = wakshop.getCurrentlyRunningWorkshop();
		String title = workshop.getTitle();
		boolean amIHost = wakshop.whatAmIOnCurrentWorkshops() == ConnectionComponent.SERVER;
		lblHostType.setText(amIHost ? "Hosting: " : "Attending: ");
		lblTitle.setText(title);
		initUserList(amIHost);
	}
	
	private void initUserList(boolean amIHost) {
		try {
			if (amIHost) {
				ConnectionMember host = wakshop.getCurrentlyRunningWorkshop().getHost();
				UserPreviewBoxController node = initView(ViewType.WORKSHOP_MAIN_USER_PREVIEW, host);
				node.onFocus(this);
				usersMap.put(host, node);
			}
			List<ClientService> members = wakshop.getCurrentlyRunningWorkshop().getMembers();
			for (Iterator<ClientService> iterator = members.iterator(); iterator.hasNext();) {
				ClientService c = iterator.next();
				UserPreviewBoxController node = initView(ViewType.WORKSHOP_MAIN_USER_PREVIEW, (ConnectionMember) c);
				node.onFocus(this);
				
				usersMap.put((ConnectionMember) c, node);
			}
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e);
		}
		gp = new GridPane();
		gp.setVgap(4);
		for (Iterator<Focusable> it = usersMap.values().iterator(); it.hasNext();) {
			UserPreviewBoxController node = (UserPreviewBoxController) it.next();
			gp.getChildren().add(node);
		}
		tpUsers.setContent(gp);
	}
	
	private UserPreviewBoxController initView(ViewType viewType, ConnectionMember client) throws InstantiationException, IllegalAccessException {
		if (!innerControllers.containsKey(viewType)) {
			try {
				Focusable c = viewType.newFor(client);
				innerControllers.put(viewType, c);
				GuiApp.getNode(viewType, c);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (UserPreviewBoxController) innerControllers.get(viewType);
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
