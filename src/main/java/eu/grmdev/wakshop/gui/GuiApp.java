package eu.grmdev.wakshop.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.guigarage.flatterfx.FlatterFX;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;

public class GuiApp extends Application {
	
	private IWakshop wakshop;
	private Stage currentStage;
	private static boolean started;
	@Getter
	private static GuiApp instance;
	private Map<ViewType, Scene> views;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		started = true;
		instance = this;
		currentStage = primaryStage;
		views = new HashMap<>();
		wakshop = new Wakshop();
		currentStage.setTitle("Wakshop - Manage your workshops freely");
		currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo_s.png")));
		changeView(ViewType.LOGIN);
		currentStage.show();
		FlatterFX.style();
	}
	
	public static void run() {
		if (!started) {
			new Thread(() -> launch()).start();
		}
	}
	
	public void changeView(ViewType viewType) throws IOException {
		if (!views.containsKey(viewType)) {
			URL loginViewUrl = getClass().getResource(viewType.getPath());
			Parent loginView = FXMLLoader.load(loginViewUrl);
			Scene view = new Scene(loginView);
			views.put(viewType, view);
		}
		currentStage.setScene(views.get(viewType));
	}
}
