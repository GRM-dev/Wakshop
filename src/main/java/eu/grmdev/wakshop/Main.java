package eu.grmdev.wakshop;

import eu.grmdev.wakshop.core.Wakshop;
import eu.grmdev.wakshop.gui.GuiApp;
import javafx.application.Platform;
import lombok.Getter;

public class Main {
	@Getter
	private static Wakshop wakshop;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Wakshop!");
		wakshop = new Wakshop();
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
			wakshop.getDatabase().close();
		});
		t.setName("Closing thread");
		t.start();
	}
}
