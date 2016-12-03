package eu.grmdev.wakshop.utils;

import javafx.fxml.Initializable;

public interface Focusable extends Initializable {
	public void onFocus(Object requestingObject);
}
