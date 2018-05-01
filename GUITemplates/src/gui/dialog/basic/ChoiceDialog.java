package gui.dialog.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert.AlertType;

public class ChoiceDialog extends BasicDialog {

	protected List<String> choices;

	public ChoiceDialog() {
		super(AlertType.NONE);
		this.choices = new ArrayList<String>();
	}

	public ChoiceDialog(String contentText) {
		super(contentText, AlertType.NONE);
		this.choices = new ArrayList<String>();
	}

	public ChoiceDialog(String title, String contentText) {
		super(title, contentText, AlertType.NONE);
		this.choices = new ArrayList<String>();
	}

	public ChoiceDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText, AlertType.NONE);
		this.choices = new ArrayList<String>();
	}

	public ChoiceDialog(List<String> choices) {
		super(AlertType.NONE);
		this.choices = choices;
	}

	public ChoiceDialog(String contentText, List<String> choices) {
		super(contentText, AlertType.NONE);
		this.choices = choices;
	}

	public ChoiceDialog(String title, String contentText, List<String> choices) {
		super(title, contentText, AlertType.NONE);
		this.choices = choices;
	}

	public ChoiceDialog(String title, String headerText, String contentText, List<String> choices) {
		super(title, headerText, contentText, AlertType.NONE);
		this.choices = choices;
	}

	public ChoiceDialog(String[] choices) {
		super(AlertType.NONE);
		setChoices(choices);
	}

	public ChoiceDialog(String contentText, String[] choices) {
		super(contentText, AlertType.NONE);
		setChoices(choices);
	}

	public ChoiceDialog(String title, String contentText, String[] choices) {
		super(title, contentText, AlertType.NONE);
		setChoices(choices);
	}

	public ChoiceDialog(String title, String headerText, String contentText, String[] choices) {
		super(title, headerText, contentText, AlertType.NONE);
		setChoices(choices);
	}

	@Override
	public String start() {
		javafx.scene.control.ChoiceDialog<String> dialog;
		dialog = new javafx.scene.control.ChoiceDialog<String>("b", choices);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);

		Optional<String> result = dialog.showAndWait();
		return result.isPresent() ? result.get() : null;
	}

	public boolean addChoices(List<String> choices) {
		for (String choice : choices)
			if (!this.choices.add(choice))
				return false;

		return true;
	}

	public boolean addChoices(String[] choices) {
		for (String choice : choices)
			if (!this.choices.add(choice))
				return false;

		return true;
	}

	public List<String> setChoices(List<String> choices) {
		List<String> result = this.choices;
		this.choices = choices;
		return result;
	}

	public String[] setChoices(String[] choices) {
		String[] result = getChoicesArray();

		this.choices = new ArrayList<String>();
		for (String choice : choices)
			this.choices.add(choice);

		return result;
	}

	public List<String> getChoices() {
		return choices;
	}

	public String[] getChoicesArray() {
		String[] result = new String[this.choices.size()];
		for (int i = 0; i < this.choices.size(); i++)
			result[i] = this.choices.get(i);

		return result;
	}

	public boolean addChoice(String choice) {
		return this.choices.add(choice);
	}

	public String setChoice(int index, String choice) {
		return this.choices.set(index, choice);
	}

	public String getChoice(int index) {
		return this.choices.get(index);
	}

}
