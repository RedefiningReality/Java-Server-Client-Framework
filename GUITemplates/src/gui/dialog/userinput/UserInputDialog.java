package gui.dialog.userinput;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.INFO;

import framework.logging.Logger;
import gui.dialog.AlertDialog;

public class UserInputDialog extends AlertDialog {
	
	protected AlertDialog dialog;
	protected String value;
	
	private Logger logger;
	
	public UserInputDialog(AlertDialog dialog) {
		this.dialog = dialog;
		this.value = "";
		
		this.logger = new Logger("ValueDialog");
	}
	
	public UserInputDialog(AlertDialog dialog, String defaultValue) {
		this.dialog = dialog;
		this.value = "";
		
		this.logger = new Logger("ValueDialog");
	}
	
	public UserInputDialog(AlertDialog dialog, boolean verbose, int messageLevel) {
		this.dialog = dialog;
		this.value = "";
		
		this.logger = new Logger("ValueDialog", verbose, messageLevel);
	}
	
	public UserInputDialog(AlertDialog dialog, String defaultValue, boolean verbose, int messageLevel) {
		this.dialog = dialog;
		this.value = "";
		
		this.logger = new Logger("ValueDialog", verbose, messageLevel);
	}
	
	@Override
	public String start() {
		logger.printMessage("Obtaining value from user.", INFO);
		value = dialog.start();
		
		logger.printMessage("Value obtained from user succesfully.", INFO);
		logger.printMessage("Value: " + value + ".", DATA);
		
		return value;
	}
	
	public void setDialog(AlertDialog dialog) {
		this.dialog = dialog;
	}
	
	public AlertDialog getDialog() {
		return dialog;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
