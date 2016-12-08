package eu.grmdev.wakshop.gui;

import static eu.grmdev.wakshop.utils.GuiHelper.runInFxThread;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import com.guigarage.flatterfx.overlay.DefaultOverlay;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.gui.controllers.MainController;
import eu.grmdev.wakshop.utils.Dialogs;
import eu.grmdev.wakshop.utils.Focusable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;

public class GuiApp extends Application {
	@Getter
	private Stage currentStage;
	private static boolean started;
	@Getter
	private static GuiApp instance;
	@Getter
	private static MainController mainViewInstance;
	private static Map<ViewType, Scene> views;
	@Getter
	private static Image icon;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		started = true;
		instance = this;
		currentStage = primaryStage;
		views = new HashMap<>();
		currentStage.setOnCloseRequest(event -> {
			Main.close();
		});
		currentStage.setTitle("Wakshop - Manage your workshops freely");
		icon = new Image(getClass().getResourceAsStream("/images/logo_s.png"));
		currentStage.getIcons().add(icon);
		currentStage.setMinHeight(400);
		currentStage.setMinWidth(480);
		changeViewTo(ViewType.LOGIN);
		currentStage.show();
		FlatterFX.style(FlatterInputType.TOUCH);
	}
	
	public static void run() {
		if (!started) {
			new Thread(() -> launch()).start();
		}
	}
	
	public synchronized void changeViewTo(ViewType viewType) {
		runInFxThread(() -> {
			try {
				Scene view;
				if (!views.containsKey(viewType)) {
					view = createView(viewType);
				}
				else {
					view = views.get(viewType);
				}
				currentStage.setScene(view);
			}
			catch (IOException e) {
				e.printStackTrace();
				Dialogs.showExceptionDialog(e, "Changing View to: " + viewType.name() + " has failed");
			}
		});
	}
	
	public static Parent getNode(ViewType viewType, Focusable root) {
		try {
			if (!views.containsKey(viewType)) {
				createView(viewType, root);
			}
			Parent node = views.get(viewType).getRoot();
			return node;
		}
		catch (Exception e) {
			e.printStackTrace();
			Dialogs.showExceptionDialog(e, "Couldn't get node " + viewType.name());
			return null;
		}
	}
	
	private static synchronized Scene createView(ViewType viewType) throws IOException {
		return createView(viewType, null);
	}
	
	private static synchronized Scene createView(ViewType viewType, Focusable root) throws IOException {
		URL viewUrl = GuiApp.class.getResource(viewType.getPath());
		if (viewUrl == null) { throw new IOException("Can't find file: " + viewType.getPath()); }
		FXMLLoader loader = new FXMLLoader(viewUrl);
		if (root != null) {
			loader.setRoot(root);
			loader.setController(root);
		}
		Parent view = loader.load();
		Scene scene = new Scene(view);
		if (root != null) {
			view.addEventHandler(WindowEvent.WINDOW_SHOWN, (e) -> {
				root.onFocus(e);
			});
		}
		if (viewType == ViewType.MAIN) {
			DefaultOverlay overlay = new DefaultOverlay();
			FlatterFX.getInstance().setOverlayLayerForScene(scene, overlay);
		}
		scene.addEventHandler(KeyEvent.KEY_PRESSED, t -> {
			if (t.getCode() == KeyCode.ESCAPE) {
				Main.close();
			}
		});
		views.put(viewType, scene);
		return scene;
	}
	
	public static void setMainViewController(MainController controller) {
		if (controller != null && mainViewInstance == null) {
			mainViewInstance = controller;
		}
	}
}
