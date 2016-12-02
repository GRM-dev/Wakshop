package eu.grmdev.wakshop;

import eu.grmdev.wakshop.gui.GuiApp;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Welcome to Wakshop!");
		GuiApp.run();
		System.out.println("Window created");
	}
	
	public static void close() {
		GuiApp gui = GuiApp.getInstance();
		if (gui != null) {
			gui.getCurrentStage().close();
		}
	}
	
}
