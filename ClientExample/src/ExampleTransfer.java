import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import framework.properties.PropertiesFile;
import framework.transfer.Transfer;
import gui.dialog.basic.input.TextInputDialog;
import gui.dialog.userinput.PropertiesDialog;

public class ExampleTransfer extends Transfer {

	@Override
	public void start(BufferedReader in, PrintWriter out) throws IOException {
		TextInputDialog textInput = new TextInputDialog("Please enter a word: ");
		textInput.setDefaultValue(PropertiesFile.getProperty("word"));
		String word = new PropertiesDialog(textInput, "word").start();
		out.println(word);
	}
	
}
