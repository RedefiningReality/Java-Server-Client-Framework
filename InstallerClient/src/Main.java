import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.INFO;

import java.io.IOException;

import framework.client.ClientFramework;
import framework.logging.Logger;
import framework.properties.PropertiesFile;
import framework.transfer.DataTransfers;

public class Main {
	
	private static Logger logger;
    
    private static String hostName = "localhost";
    private static int port = 12345;

	public static void main(String[] args) throws IOException, InterruptedException {
		Logger.setLogFile("log.txt");
		initialize("config.properties");
		
		DataTransfers.addTransfer("File Transfer", new FileTransfer("", true, DATA));
		ClientFramework client = new ClientFramework("localhost", 12345, true, DATA);
		client.addTransfer("File Transfer");
		client.executeTransfers();
		client.disconnect();
		client.close();
		
		close();
	}
	
	private static void initialize(String propFileName) throws IOException {
		logger = new Logger(true, DATA);
		
		initializePropertiesFile(propFileName);
    	initializeProperties();
	}
	
	private static void initializePropertiesFile(String propFileName) throws IOException {
    	if(!PropertiesFile.isInitialized())
	        PropertiesFile.initialize(propFileName, logger.getVerbose(), 
	        		logger.getMessageLevel());
    }
	
	private static void initializeProperties() throws IOException {
		PropertiesFile.addProperty("hostName", hostName);
    	PropertiesFile.addProperty("PORT", String.valueOf(port));
    }
	
	private static void close() throws IOException {
		logger.printMessage("Closing Graphical User Interface...", INFO);
        
        logger.printMessage("Saving current properties to .properties file if .properties "
        		+ "file exists...", INFO);
        PropertiesFile.saveToFile();
	}

}
