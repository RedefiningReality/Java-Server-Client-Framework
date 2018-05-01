package gui.dialog.basic;

import javafx.scene.control.Alert.AlertType;

public class WarningDialog extends BasicDialog {

	public WarningDialog() {
		super(AlertType.WARNING);
	}

	public WarningDialog(String contentText) {
		super(contentText, AlertType.WARNING);
	}

	public WarningDialog(String title, String contentText) {
		super(title, contentText, AlertType.WARNING);
	}

	public WarningDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText, AlertType.WARNING);
	}

}
