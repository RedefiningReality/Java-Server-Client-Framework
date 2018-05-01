import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import framework.properties.PropertiesFile;
import framework.transfer.Transfer;
import gui.dialog.basic.InformationDialog;
import gui.dialog.basic.input.TextInputDialog;
import gui.dialog.userinput.PropertiesDialog;

public class ExampleTransfer extends Transfer {

	@Override
	public void start(InputStream inStream, OutputStream outStream) throws IOException {
		PrintWriter out = new PrintWriter(outStream, true);

		TextInputDialog textInput = new TextInputDialog("Please enter a word: ");
		textInput.setDefaultValue(PropertiesFile.getProperty("word"));
		String word = new PropertiesDialog(textInput, "word").start();

		out.println(word);

		InformationDialog info = new InformationDialog();
		info.setTitle("Client Word");
		info.setHeaderText("Here is the word: ");
		info.setContentText(word);
		info.start();
	}

}
