import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import framework.logging.Logger;
import framework.logging.MessageLevels;
import framework.transfer.Transfer;

public class ExampleTransfer extends Transfer {

	@Override
	public void start(BufferedReader in, PrintWriter out) throws IOException {
		String word = in.readLine();
		
		Logger logger = new Logger("ExampleTransfer");
		logger.printMessage("Word: " + word, MessageLevels.HIGHEST_PRIORITY);
	}

}
