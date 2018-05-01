package gui.dialog.basic;

import java.util.Optional;

import gui.dialog.AlertDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public abstract class BasicDialog extends AlertDialog {

	protected String headerText;
	protected String contentText;

	protected AlertType alertType;

	public BasicDialog() {
		super();
		this.headerText = null;
		this.contentText = "";
		this.alertType = AlertType.NONE;
	}

	public BasicDialog(String contentText) {
		super();
		this.headerText = null;
		this.contentText = contentText;
		this.alertType = AlertType.NONE;
	}

	public BasicDialog(String title, String contentText) {
		super(title);
		this.headerText = null;
		this.contentText = contentText;
		this.alertType = AlertType.NONE;
	}

	public BasicDialog(String title, String headerText, String contentText) {
		super(title);
		this.headerText = headerText;
		this.contentText = contentText;
		this.alertType = AlertType.NONE;
	}

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

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() ? result.get().getText() : null;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getContentText() {
		return contentText;
	}

	public AlertType getAlertType() {
		return alertType;
	}

}