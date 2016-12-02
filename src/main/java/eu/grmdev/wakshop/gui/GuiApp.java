package eu.grmdev.wakshop.gui;

import java.net.URL;

import com.guigarage.flatterfx.FlatterFX;

import eu.grmdev.wakshop.core.IWakshop;
import eu.grmdev.wakshop.core.Wakshop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GuiApp extends Application {
	
	private IWakshop wakshop;
	private Stage currentStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.wakshop = new Wakshop();
		this.currentStage = primaryStage;
		URL loginViewUrl = getClass().getResource("/views/LoginView.fxml");
		Parent loginView = FXMLLoader.load(loginViewUrl);
		Scene loginScene = new Scene(loginView);
		currentStage.setScene(loginScene);
		currentStage.setTitle("Wakshop - Manage your workshops freely");
		currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo_s.png")));
		currentStage.show();
		FlatterFX.style();
	}
	
	public static void run() {
		new Thread(() -> launch()).start();
	}
	
}
