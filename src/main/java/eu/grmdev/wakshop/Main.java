package eu.grmdev.wakshop;

import eu.grmdev.wakshop.gui.GuiApp;
import javafx.application.Platform;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Welcome to Wakshop!");
		GuiApp.run();
		System.out.println("Window created");
	}
	
	public static void Close() {
		Platform.exit();
	}
	
}
