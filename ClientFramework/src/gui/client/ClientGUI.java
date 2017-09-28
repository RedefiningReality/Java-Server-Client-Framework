package gui.client;

import static framework.logging.MessageLevels.INFO;

import java.io.IOException;

import framework.logging.Logger;
import framework.properties.PropertiesFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class ClientGUI extends Application {
    
    private static Logger logger;
    
    private static String defaultHostName = "localhost";
    private static int defaultPort = 12345;
    
    private static boolean propertiesFile;
    
    public static void initialize() throws IOException {
    	initialize(true);
    }
    
    public static void initialize(boolean createPropertiesFile) throws IOException {
    	logger = new Logger("ClientGUI");
    	
    	ClientGUI.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String propFileName) throws IOException {
    	logger = new Logger("ClientGUI");
    	
    	propertiesFile = true;
	    initializePropertiesFile(propFileName);
	    initializeProperties();
    }
    
    public static void initialize(String propFileName, String propFileLocation) throws IOException {
    	logger = new Logger("ClientGUI");
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName, propFileLocation);
    	initializeProperties();
    }
    
    public static void initialize(boolean verbose, int messageLevel) throws IOException {
    	initialize(true, verbose, messageLevel);
    }
    
    public static void initialize(boolean createPropertiesFile, boolean verbose, int messageLevel) 
    		throws IOException {
    	logger = new Logger("ClientGUI", verbose, messageLevel);
    	
    	ClientGUI.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String propFileName, boolean verbose, int messageLevel) 
    		throws IOException {
    	logger = new Logger("ClientGUI", verbose, messageLevel);
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName);
    	initializeProperties();
    }
    
    public static void initialize(String propFileName, String propFileLocation, boolean verbose,
    		int messageLevel) throws IOException {
    	logger = new Logger("ClientGUI", verbose, messageLevel);
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName, propFileLocation);
    	initializeProperties();
    }
    
    public static void initialize(String defaultHostName, int defaultPort) throws IOException {
    	initialize(defaultHostName, defaultPort, true);
    }
    
    public static void initialize(String defaultHostName, int defaultPort, boolean createPropertiesFile) 
    		throws IOException {
    	logger = new Logger("ClientGUI");
    	
    	ClientGUI.defaultHostName = defaultHostName;
    	ClientGUI.defaultPort = defaultPort;
    	
    	ClientGUI.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String propFileName, String defaultHostName, int defaultPort) 
    		throws IOException {
    	logger = new Logger("ClientGUI");
    	
    	ClientGUI.defaultHostName = defaultHostName;
    	ClientGUI.defaultPort = defaultPort;
    	
    	ClientGUI.propertiesFile = true;
    	if(propertiesFile) {
	    	initializePropertiesFile(propFileName);
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String propFileName, String propFileLocation, String defaultHostName, 
    		int defaultPort) throws IOException {
    	logger = new Logger("ClientGUI");
    	
    	ClientGUI.defaultHostName = defaultHostName;
    	ClientGUI.defaultPort = defaultPort;
    	
    	ClientGUI.propertiesFile = true;
    	if(propertiesFile) {
	    	initializePropertiesFile(propFileName, propFileLocation);
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String defaultHostName, int defaultPort, boolean verbose, 
    		int messageLevel) throws IOException {
    	initialize(defaultHostName, defaultPort, true, verbose, messageLevel);
    }
    
    public static void initialize(String defaultHostName, int defaultPort, boolean createPropertiesFile,
    		boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ClientGUI", verbose, messageLevel);
    	
    	ClientGUI.defaultHostName = defaultHostName;
    	ClientGUI.defaultPort = defaultPort;
    	
    	ClientGUI.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String propFileName, String defaultHostName, int defaultPort, 
    		boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ClientGUI", verbose, messageLevel);
    	
    	ClientGUI.defaultHostName = defaultHostName;
    	ClientGUI.defaultPort = defaultPort;
    	
    	ClientGUI.propertiesFile = true;
    	if(propertiesFile) {
	    	initializePropertiesFile(propFileName);
	    	initializeProperties();
    	}
    }
    
    public static void initialize(String propFileName, String propFileLocation, String defaultHostName, 
    		int defaultPort, boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ClientGUI", verbose, messageLevel);
    	
    	ClientGUI.defaultHostName = defaultHostName;
    	ClientGUI.defaultPort = defaultPort;
    	
    	ClientGUI.propertiesFile = true;
    	if(propertiesFile) {
	    	initializePropertiesFile(propFileName, propFileLocation);
	    	initializeProperties();
    	}
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void run() throws IOException {
        ClientGUI.launch();
        logger.printMessage("Closing Graphical User Interface...", INFO);
        
        logger.printMessage("Saving current properties to .properties file if .properties "
        		+ "file exists...", INFO);
        PropertiesFile.saveToFile();
    }
    
    private static void initializePropertiesFile() throws IOException {
    	if(!PropertiesFile.isInitialized())
    		PropertiesFile.initialize("config.properties", logger.getVerbose(), 
    				logger.getMessageLevel());
    }
    
    private static void initializePropertiesFile(String propFileName) throws IOException {
    	if(!PropertiesFile.isInitialized())
	        PropertiesFile.initialize(propFileName, logger.getVerbose(), 
	        		logger.getMessageLevel());
    }
    
    private static void initializePropertiesFile(String propFileName, String propFileLocation) 
    		throws IOException {
    	if(!PropertiesFile.isInitialized())
	        PropertiesFile.initialize(propFileName, propFileLocation, logger.getVerbose(), 
	                logger.getMessageLevel());
    }
    
    private static void initializeProperties() throws IOException {
		PropertiesFile.addProperty("hostName", defaultHostName);
    	PropertiesFile.addProperty("PORT", String.valueOf(defaultPort));
    }
    
    public static void setDefaultHostName(String defaultHostName) throws IOException {
    	ClientGUI.defaultHostName = defaultHostName;
    	
    	if(propertiesFile)
    		PropertiesFile.setProperty("hostName", defaultHostName);
    }
    
    public static String getDefaultHostName() {
    	return defaultHostName;
    }
    
    public static void setDefaultPort(int defaultPort) throws IOException {
    	ClientGUI.defaultPort = defaultPort;
    	
    	if(propertiesFile)
    		PropertiesFile.setProperty("PORT", String.valueOf(defaultPort));
    }
    
    public static int getDefaultPort() {
    	return defaultPort;
    }
    
    public static void setVerbose(boolean verbose) {
    	logger.setVerbose(verbose);
    }
    
    public static boolean getVerbose() {
    	return logger.getVerbose();
    }
    
    public static void setMessageLevel(int messageLevel) {
    	logger.setMessageLevel(messageLevel);
    }
    
    public static int getMessageLevel() {
    	return logger.getMessageLevel();
    }
    
    public static void createPropertiesFile() throws IOException {
    	propertiesFile = true;
    	
		initializePropertiesFile();
		initializeProperties();
    }
    
    public static void removePropertiesFile() throws IOException {
    	propertiesFile = false;
    	
		PropertiesFile.removeFile();
    }
    
    public static boolean usingPropertiesFile() {
    	return propertiesFile;
    }
    
}
