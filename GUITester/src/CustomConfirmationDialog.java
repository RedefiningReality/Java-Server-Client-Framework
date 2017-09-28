

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class CustomConfirmationDialog extends ConfirmationDialog {
	
	protected final String[] buttons;

	public CustomConfirmationDialog(String[] buttons) {
		super();
		this.buttons = buttons;
	}
	
	public CustomConfirmationDialog(String contentText, String[] buttons) {
		super(contentText);
		this.buttons = buttons;
	}
	
	public CustomConfirmationDialog(String title, String contentText, String[] buttons) {
		super(title, contentText);
		this.buttons = buttons;
	}
	
	public CustomConfirmationDialog(String title, String headerText, String contentText, String[] buttons) {
		super(title, headerText, contentText);
		this.buttons = buttons;
	}
	
	@Override
	public String start() {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		for(String button : buttons)
			alert.getButtonTypes().add(new ButtonType(button));

		return alert.showAndWait().get().getText();
	}
	
}
