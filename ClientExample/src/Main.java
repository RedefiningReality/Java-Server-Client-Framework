import java.io.IOException;

import framework.logging.Logger;
import framework.logging.MessageLevels;
import framework.transfer.DataTransfers;
import gui.client.ClientGUI;

public class Main {

	public static void main(String[] args) throws IOException {
		Logger.setLogFile("log.txt");
		
		DataTransfers.addTransfer("Example", new ExampleTransfer());
		ClientGUI.initialize("localhost", 12345, true, MessageLevels.DATA);
		ClientGUI.run();
	}

}
