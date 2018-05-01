package gui.dialog.basic.confirmation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class CustomConfirmationDialog extends ConfirmationDialog {

	protected List<String> buttons;

	public CustomConfirmationDialog(List<String> buttons) {
		super();
		this.buttons = buttons;
	}

	public CustomConfirmationDialog(String contentText, List<String> buttons) {
		super(contentText);
		this.buttons = buttons;
	}

	public CustomConfirmationDialog(String title, String contentText, List<String> buttons) {
		super(title, contentText);
		this.buttons = buttons;
	}

	public CustomConfirmationDialog(String title, String headerText, String contentText, List<String> buttons) {
		super(title, headerText, contentText);
		this.buttons = buttons;
	}

	public CustomConfirmationDialog(String[] buttons) {
		super();
		setButtons(buttons);
	}

	public CustomConfirmationDialog(String contentText, String[] buttons) {
		super(contentText);
		setButtons(buttons);
	}

	public CustomConfirmationDialog(String title, String contentText, String[] buttons) {
		super(title, contentText);
		setButtons(buttons);
	}

	public CustomConfirmationDialog(String title, String headerText, String contentText, String[] buttons) {
		super(title, headerText, contentText);
		setButtons(buttons);
	}

	@Override
	public String start() {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		for (String button : buttons)
			alert.getButtonTypes().add(new ButtonType(button));

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() ? result.get().getText() : null;
	}

	public boolean addButtons(List<String> buttons) {
		for (String button : buttons)
			if (!this.buttons.add(button))
				return false;

		return true;
	}

	public boolean addButtons(String[] buttons) {
		for (String button : buttons)
			if (!this.buttons.add(button))
				return false;

		return true;
	}

	public List<String> setButtons(List<String> buttons) {
		List<String> result = this.buttons;
		this.buttons = buttons;
		return result;
	}

	public String[] setButtons(String[] buttons) {
		String[] result = getButtonsArray();

		this.buttons = new ArrayList<String>();
		for (String button : buttons)
			this.buttons.add(button);

		return result;
	}

	public List<String> getButtons() {
		return buttons;
	}

	public String[] getButtonsArray() {
		if (this.buttons == null)
			return null;

		String[] result = new String[this.buttons.size()];
		for (int i = 0; i < this.buttons.size(); i++)
			result[i] = this.buttons.get(i);

		return result;
	}

	public boolean addChoice(String button) {
		return this.buttons.add(button);
	}

	public String setChoice(int index, String button) {
		return this.buttons.set(index, button);
	}

	public String getChoice(int index) {
		return this.buttons.get(index);
	}

}
