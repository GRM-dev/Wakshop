package eu.grmdev.wakshop;

import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.gui.GuiApp;
import javafx.application.Platform;

public class Main {
	
	public static void main(String[] args) {
		Thread.currentThread().setName("Main App Thread");
		System.out.println("Welcome to Wakshop!");
		new Thread(() -> {
			Wakshop.getInstance();
			System.out.println("Core created");
		}).start();;
		GuiApp.run();
		System.out.println("Window created");
	}
	
	public static void close() {
		GuiApp gui = GuiApp.getInstance();
		Thread t = new Thread(() -> {
			Platform.runLater(() -> {
				if (gui != null) {
					gui.getCurrentStage().close();
				}
			});
			Wakshop.getInstance().closeAllExistingNetConnections();
			Wakshop.getInstance().getDatabase().close();
		}, "Closing thread");
		t.start();
	}
}
