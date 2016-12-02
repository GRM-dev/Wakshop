package eu.grmdev.wakshop.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.guigarage.flatterfx.FlatterFX;

import eu.grmdev.wakshop.Main;
import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Getter;

public class GuiApp extends Application {
	
	private IWakshop wakshop;
	@Getter
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
		currentStage.setOnCloseRequest(event -> {
			Main.close();
		});
		currentStage.setTitle("Wakshop - Manage your workshops freely");
		currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo_s.png")));
		currentStage.setMinHeight(380);
		currentStage.setMinWidth(300);
		changeView(ViewType.LOGIN);
		currentStage.show();
		FlatterFX.style();
	}
	
	public static void run() {
		if (!started) {
			new Thread(() -> launch()).start();
		}
	}
	
	public void changeView(ViewType viewType) {
		try {
			if (!views.containsKey(viewType)) {
				URL loginViewUrl = getClass().getResource(viewType.getPath());
				Parent loginView = FXMLLoader.load(loginViewUrl);
				Scene scene = new Scene(loginView);
				scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					
					@Override
					public void handle(KeyEvent t) {
						if (t.getCode() == KeyCode.ESCAPE) {
							Main.close();
						}
					}
				});
				views.put(viewType, scene);
			}
			currentStage.setScene(views.get(viewType));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
