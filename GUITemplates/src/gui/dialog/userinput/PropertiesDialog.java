package gui.dialog.userinput;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.ERROR;
import static framework.logging.MessageLevels.INFO;

import java.io.IOException;

import framework.logging.Logger;
import framework.properties.PropertiesFile;
import gui.dialog.AlertDialog;;

public class PropertiesDialog extends UserInputDialog {
	
	public String key;
	
	protected final Logger logger;
	
	public PropertiesDialog(AlertDialog dialog) {
		super(dialog);
		this.key = "";
		
		this.logger = new Logger("PropertiesDialog");
	}
	
	public PropertiesDialog(AlertDialog dialog, String key) {
		super(dialog);
		this.key = key;
		
		this.logger = new Logger("PropertiesDialog");
	}
	
	public PropertiesDialog(AlertDialog dialog, String key, String defaultValue) {
		super(dialog, defaultValue);
		this.key = key;
		
		this.logger = new Logger("PropertiesDialog");
	}
	
	public PropertiesDialog(AlertDialog dialog, boolean verbose, int messageLevel) {
		super(dialog, verbose, messageLevel);
		this.key = "";
		
		this.logger = new Logger("PropertiesDialog", verbose, messageLevel);
	}
	
	public PropertiesDialog(AlertDialog dialog, String key, boolean verbose, int messageLevel) {
		super(dialog, verbose, messageLevel);
		this.key = key;
		
		this.logger = new Logger("PropertiesDialog", verbose, messageLevel);
	}
	
	public PropertiesDialog(AlertDialog dialog, String key, String defaultValue, boolean verbose, 
			int messageLevel) {
		super(dialog, defaultValue, verbose, messageLevel);
		this.key = key;
		
		this.logger = new Logger("PropertiesDialog", verbose, messageLevel);
	}
	
	@Override
	public String start() {
		try {
			logger.printMessage("Obtaining value of '" + key + "' key from properties file.", INFO);
			String propertyValue = null;
			
			propertyValue = PropertiesFile.getProperty(key);
			
			if(propertyValue != null && !value.equals(propertyValue)) {
				logger.printMessage("Value found in properties file.", INFO);
				logger.printMessage("Value: " + value + ".", DATA);
				
				if(!value.equals(propertyValue)) {
					logger.printMessage("Setting value as default value.", INFO);
					value = propertyValue;
				} else
					logger.printMessage("Value in properties file already is the default value.", INFO);
			} else
				logger.printMessage("Value not found in properties file.", INFO);
			
			logger.printMessage("Obtaining final value of '" + key + "' key from user.", INFO);
			String result = dialog.start();
			
			logger.printMessage("Final value obtained from user succesfully.", INFO);
			logger.printMessage("Final value: " + result + ".", DATA);
			
			if (!result.equals(value)) {
				value = result;
	            PropertiesFile.setProperty(key, value);
			}
		} catch (IOException ex) {
			logger.printMessage("line 108: Failed to associate with properties file: " + ex.getMessage()
					+ ": Terminating...", ERROR);
		}
		
		return value;
	}
	
	public void setDialog(AlertDialog dialog) {
		this.dialog = dialog;
	}
	
	public AlertDialog getDialog() {
		return dialog;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
