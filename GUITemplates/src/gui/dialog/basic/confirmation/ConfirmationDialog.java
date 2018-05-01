package gui.dialog.basic.confirmation;

import gui.dialog.basic.BasicDialog;
import javafx.scene.control.Alert.AlertType;

public class ConfirmationDialog extends BasicDialog {

	public ConfirmationDialog() {
		super(AlertType.CONFIRMATION);
	}

	public ConfirmationDialog(String contentText) {
		super(contentText, AlertType.CONFIRMATION);
	}

	public ConfirmationDialog(String title, String contentText) {
		super(title, contentText, AlertType.CONFIRMATION);
	}

	public ConfirmationDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText, AlertType.CONFIRMATION);
	}

}
