import java.io.IOException;

import framework.logging.Logger;
import framework.logging.MessageLevels;
import framework.server.ServerFramework;
import framework.transfer.DataTransfers;

public class Main {

	public static void main(String[] args) throws IOException {
		Logger.setLogFile("log.txt");
		
		DataTransfers.addTransfer("Example", new ExampleTransfer());
		
		ServerFramework server = new ServerFramework(true, MessageLevels.DATA);
		server.start();
		server.connect();
	}

}
