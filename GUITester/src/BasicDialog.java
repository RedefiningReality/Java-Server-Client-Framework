

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class BasicDialog extends Dialog {
	
	protected final String headerText;
	protected final String contentText;
	
	protected final AlertType alertType;
	
	public BasicDialog(AlertType alertType) {
		super();
		this.headerText = null;
		this.contentText = "";
		this.alertType = alertType;
	}
	
	public BasicDialog(String contentText, AlertType alertType) {
		super();
		this.headerText = null;
		this.contentText = contentText;
		this.alertType = alertType;
	}
	
	public BasicDialog(String title, String contentText, AlertType alertType) {
		super(title);
		this.headerText = null;
		this.contentText = contentText;
		this.alertType = alertType;
	}
	
	public BasicDialog(String title, String headerText, String contentText, AlertType alertType) {
		super(title);
		this.headerText = headerText;
		this.contentText = contentText;
		this.alertType = alertType;
	}
	
	public String start() {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		return alert.showAndWait().get().getText();
	}
	
}