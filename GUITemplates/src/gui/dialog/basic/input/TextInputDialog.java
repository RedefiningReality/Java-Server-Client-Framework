package gui.dialog.basic.input;

import java.util.Optional;

import gui.dialog.basic.BasicDialog;

public class TextInputDialog extends BasicDialog {
	
	protected String defaultValue;
	protected String promptText;
	
	public TextInputDialog() {
		super();
		this.promptText = "";
		this.defaultValue = "";
	}
	
	public TextInputDialog(String contentText) {
		super(contentText);
		this.promptText = "";
		this.defaultValue = "";
	}
	
	public TextInputDialog(String title, String contentText) {
		super(title, contentText);
		this.promptText = "";
		this.defaultValue = "";
	}
	
	public TextInputDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText);
		this.promptText = "";
		this.defaultValue = "";
	}
	
	public TextInputDialog(String title, String headerText, String contentText, String promptText) {
		super(title, headerText, contentText);
		this.promptText = promptText;
		this.defaultValue = "";
	}
	
	public TextInputDialog(String title, String headerText, String contentText, String promptText, 
			String defaultValue) {
		super(title, headerText, contentText);
		this.promptText = promptText;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String start() {
		javafx.scene.control.TextInputDialog dialog;
		dialog = new javafx.scene.control.TextInputDialog(defaultValue);
		
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		dialog.getEditor().setPromptText(promptText);
		
		Optional<String> result = dialog.showAndWait();
		return result.isPresent() ? result.get() : null;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setPromptText(String promptText) {
		this.promptText = promptText;
	}
	
	public String getPromptText() {
		return promptText;
	}

}