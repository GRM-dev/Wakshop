package eu.grmdev.wakshop.utils;

import javafx.application.Platform;

public class GuiHelper {
	public static void runInFxThread(Runnable r) {
		if (Platform.isFxApplicationThread()) {
			r.run();
		}
		else {
			Platform.runLater(r);
		}
	}
}
