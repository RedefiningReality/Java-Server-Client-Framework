package gui.dialog.basic.input;

import static framework.logging.MessageLevels.ERROR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Optional;

import framework.logging.Logger;

public class NumericInputDialog<T extends Number> extends TextInputDialog {
	
	protected final Logger logger;
	
	public NumericInputDialog() {
		super();
		logger = new Logger("NumericInputDialog");
	}

	public NumericInputDialog(String contentText) {
		super(contentText);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(String title, String contentText) {
		super(title, contentText);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(String title, String headerText, String contentText) {
		super(title, headerText, contentText);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(String title, String headerText, String contentText, String promptText) {
		super(title, headerText, contentText, promptText);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(String title, String headerText, String contentText, String promptText, 
			String defaultValue) {
		super(title, headerText, contentText, promptText, defaultValue);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(boolean verbose, int messageLevel) {
		super();
		logger = new Logger("NumericInputDialog");
	}

	public NumericInputDialog(String contentText, boolean verbose, int messageLevel) {
		super(contentText);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(String title, String contentText, boolean verbose, int messageLevel) {
		super(title, contentText);
		logger = new Logger("NumericInputDialog");
	}
	
	public NumericInputDialog(String title, String headerText, String contentText, boolean verbose, 
			int messageLevel) {
		super(title, headerText, contentText);
		logger = new Logger("NumericInputDialog", verbose, messageLevel);
	}
	
	public NumericInputDialog(String title, String headerText, String contentText, String promptText, 
			boolean verbose, int messageLevel) {
		super(title, headerText, contentText, promptText);
		logger = new Logger("NumericInputDialog", verbose, messageLevel);
	}
	
	public NumericInputDialog(String title, String headerText, String contentText, String promptText, 
			String defaultValue, boolean verbose, int messageLevel) {
		super(title, headerText, contentText, promptText, defaultValue);
		logger = new Logger("NumericInputDialog", verbose, messageLevel);
	}
	
	@Override
	public String start() {
		String stringResult;
        Optional<String> result;
        boolean isNumber = false;
        
        do {
            javafx.scene.control.TextInputDialog dialog;
            dialog = new javafx.scene.control.TextInputDialog(defaultValue);
            
            dialog.setTitle(title);
            dialog.setHeaderText(headerText);
            dialog.setContentText(contentText);
            dialog.getEditor().setPromptText(promptText);

            result = dialog.showAndWait();
            stringResult = result.get();
            try {
            	if(checkNumber(stringResult))
            		isNumber = true;
            } catch (NumberFormatException ex) {
                if (stringResult.equals(""))
                    return defaultValue;
            } catch (NoSuchElementException ex) {
                if (!ex.getMessage().equals("No value present"))
                    logger.printMessage("line 74: Unidentifiable NoSuchElementException: "
                    		+ "Please check input was entered and that the input is the string "
                    		+ "equivalent of a valid numerical value: Terminating...", ERROR);
                else
                    isNumber = true;
            }
        } while (!isNumber);
        
        return result.isPresent() ? stringResult : defaultValue;
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkNumber(String stringResult) {
		T numResult = null;
		
		// I highly dislike this try-catch algorithm, but none other seems to work.
		// I will attempt to update this in future revisions
		try {
			numResult = (T)new Integer(stringResult);
		} catch(ClassCastException ex1) {
			try {
				numResult = (T)new Double(stringResult);
			} catch(ClassCastException ex2) {
				try {
					numResult = (T)new Short(stringResult);
				} catch(ClassCastException ex3) {
					try {
						numResult = (T)new Long(stringResult);
					} catch(ClassCastException ex4) {
						try {
							numResult = (T)new Float(stringResult);
						} catch(ClassCastException ex5) {
							try {
								numResult = (T)new Byte(stringResult);
							} catch(ClassCastException ex6) {
								try {
									numResult = (T)new BigDecimal(stringResult);
								} catch(ClassCastException ex7) {
									try {
										numResult = (T)new BigInteger(stringResult);
									} catch(ClassCastException ex8) {
										
									}
								}
							}
						}
					}
				}
			}
		}
		
		return numResult != null;
	}
}
