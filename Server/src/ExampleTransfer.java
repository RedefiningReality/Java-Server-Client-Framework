import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import framework.logging.Logger;
import framework.logging.MessageLevels;
import framework.transfer.Transfer;

public class ExampleTransfer extends Transfer {

	@Override
	public void start(InputStream inStream, OutputStream outStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

		String word = in.readLine();

		Logger logger = new Logger("ExampleTransfer");
		logger.printMessage("Word: " + word, MessageLevels.HIGHEST_PRIORITY);
	}

}
