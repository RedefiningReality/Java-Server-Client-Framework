package gui.dialog.basic;

import javafx.scene.control.Alert.AlertType;

public class ErrorDialog extends BasicDialog {

	public ErrorDialog() {
		super(AlertType.ERROR);
	}
	
	public ErrorDialog(String contentText) {
		super(contentText, AlertType.ERROR);
	}
	
	public ErrorDialog(String title, String contentText) {
		super(title, contentText, AlertType.ERROR);
	}
	
	public ErrorDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText, AlertType.ERROR);
	}
	
}
