package eu.grmdev.wakshop.core.net;

import java.io.Serializable;
import java.util.TimerTask;

import eu.grmdev.wakshop.gui.GuiApp;
import eu.grmdev.wakshop.gui.ViewType;
import eu.grmdev.wakshop.utils.Dialogs;
import lombok.Getter;
import lombok.Setter;

public class StatusTimerTask extends TimerTask implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean wasClosed = false;
	@Setter
	@Getter
	private boolean closing;
	
	public StatusTimerTask() {}
	
	@Override
	public void run() {
		if (isClosing()) {
			if (!wasClosed) {
				wasClosed = true;
				Dialogs.showInformationDialog("Workshop.", "Thanks for attending");
				GuiApp.getInstance().changeViewTo(ViewType.MAIN);
			}
			else {
				this.cancel();
			}
		}
	}
	
}
