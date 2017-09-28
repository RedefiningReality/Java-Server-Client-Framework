package gui.dialog.basic;

import javafx.scene.control.Alert.AlertType;

public class InformationDialog extends BasicDialog {
	
	public InformationDialog() {
		super(AlertType.INFORMATION);
	}
	
	public InformationDialog(String contentText) {
		super(contentText, AlertType.INFORMATION);
	}
	
	public InformationDialog(String title, String contentText) {
		super(title, contentText, AlertType.INFORMATION);
	}
	
	public InformationDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText, AlertType.INFORMATION);
	}
	
}
