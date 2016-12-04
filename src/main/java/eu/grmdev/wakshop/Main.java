package eu.grmdev.wakshop;

import eu.grmdev.wakshop.gui.GuiApp;
import javafx.application.Platform;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Welcome to Wakshop!");
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
			gui.getWakshop().getDatabase().close();
		});
		t.setName("Closing thread");
		t.start();
	}
}
