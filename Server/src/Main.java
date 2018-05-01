import java.io.IOException;

import framework.logging.Logger;
import framework.logging.MessageLevels;
import framework.properties.PropertiesFile;
import framework.server.ServerFramework;
import framework.transfer.DataTransfers;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args.length > 0)
			Logger.setLogFile(args[0]);

		int loggerLevel = MessageLevels.ERROR;
		if (args.length > 1) {
			if (args[1].equals("DATA"))
				loggerLevel = MessageLevels.DATA;
			else if (args[1].equals("INFO"))
				loggerLevel = MessageLevels.INFO;
			else if (args[1].equals("WARNING"))
				loggerLevel = MessageLevels.WARNING;
			else if (args[1].equals("HIGHEST_PRIORITY"))
				loggerLevel = MessageLevels.HIGHEST_PRIORITY;
		}

		PropertiesFile.initialize("config.properties");

		DataTransfers.addTransfer("Example", new ExampleTransfer());
		DataTransfers.addTransfer("File Transfer", new FileTransfer(true, true, loggerLevel));

		ServerFramework server = new ServerFramework(true, loggerLevel);
		server.start();
		server.acceptConnections();
	}

}
