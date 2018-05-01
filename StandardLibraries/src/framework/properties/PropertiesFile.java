package framework.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import framework.logging.Logger;
import static framework.logging.MessageLevels.INFO;
import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.WARNING;

public class PropertiesFile {

	private static boolean isInitialized = false;

	private static String propFileName;
	private static String propFileLocation;

	private static Properties properties;
	private static FileInputStream inputStream;
	private static FileOutputStream outputStream;

	private static Logger logger;

	public static void initialize() throws FileNotFoundException, IOException {
		propFileName = "config.properties";
		propFileLocation = "";

		properties = new Properties();

		if (!new File(propFileLocation + propFileName).exists())
			outputStream = new FileOutputStream(propFileLocation + propFileName);
		else
			outputStream = new FileOutputStream(propFileLocation + propFileName, true);

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);

		logger = new Logger("PropertiesFile");

		isInitialized = true;
	}

	public static void initialize(String propFileName) throws IOException {
		PropertiesFile.propFileName = propFileName;
		propFileLocation = "";

		properties = new Properties();

		if (!new File(propFileLocation + propFileName).exists())
			outputStream = new FileOutputStream(propFileLocation + propFileName);
		else
			outputStream = new FileOutputStream(propFileLocation + propFileName, true);

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);

		logger = new Logger("PropertiesFile");

		isInitialized = true;
	}

	public static void initialize(String propFileName, String propFileLocation) throws IOException {
		PropertiesFile.propFileName = propFileName;
		PropertiesFile.propFileLocation = propFileLocation;

		properties = new Properties();

		if (!new File(propFileLocation + propFileName).exists())
			outputStream = new FileOutputStream(propFileLocation + propFileName);
		else
			outputStream = new FileOutputStream(propFileLocation + propFileName, true);

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);

		logger = new Logger("PropertiesFile");

		isInitialized = true;
	}

	public static void initialize(String propFileName, boolean verbose, int messageLevel) throws IOException {
		PropertiesFile.propFileName = propFileName;
		propFileLocation = "";

		properties = new Properties();

		if (!new File(propFileLocation + propFileName).exists())
			outputStream = new FileOutputStream(propFileLocation + propFileName);
		else
			outputStream = new FileOutputStream(propFileLocation + propFileName, true);

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);

		logger = new Logger("PropertiesFile", verbose, messageLevel);

		isInitialized = true;
	}

	public static void initialize(String propFileName, String propFileLocation, boolean verbose, int messageLevel)
			throws IOException {
		PropertiesFile.propFileName = propFileName;
		PropertiesFile.propFileLocation = propFileLocation;

		properties = new Properties();

		if (!new File(propFileLocation + propFileName).exists())
			outputStream = new FileOutputStream(propFileLocation + propFileName);
		else
			outputStream = new FileOutputStream(propFileLocation + propFileName, true);

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);

		logger = new Logger("PropertiesFile", verbose, messageLevel);

		isInitialized = true;
	}

	public static boolean isInitialized() {
		return isInitialized;
	}

	public static boolean addProperty(String property, String value) throws IOException {
		if (isInitialized)
			if (!properties.containsKey(property)) {
				properties.setProperty(property, value);
				return true;
			}

		return false;
	}

	public static String setProperty(String property, String value) throws IOException {
		if (isInitialized)
			return (String) properties.setProperty(property, value);

		return null;
	}

	public static String getProperty(String property) throws IOException {
		if (isInitialized)
			return properties.getProperty(property);

		return null;
	}

	public static void saveToFile() throws IOException {
		if (isInitialized) {
			inputStream.close();
			outputStream.close();

			logger.printMessage("Removing existing properties file...", INFO);
			logger.printMessage("Path: " + propFileLocation + propFileName, DATA);
			try {
				Files.deleteIfExists(Paths.get(propFileLocation + propFileName));
			} catch (IOException ex) {
				logger.printMessage("Unable to save to properties file. " + "It may be in use by another process.",
						WARNING);
				return;
			}
			logger.printMessage("File removed successfully.", INFO);

			outputStream = new FileOutputStream(propFileLocation + propFileName);

			logger.printMessage("Saving properties to file...", INFO);
			properties.store(outputStream,
					" The values here are edited accordingly " + " while running the Graphical User Interface (GUI)\n"
							+ " This file has been created for storing previous entries and for "
							+ " use by advanced users\n" + " DO NOT REMOVE ANY OF THE PROPERTIES DEFINED IN THIS FILE "
							+ " UNLESS YOU POSSESS KNOWLEDGE OF THE WORKINGS OF THE GUI!\n"
							+ " Doing so may cause errors when opening the GUI");
			logger.printMessage("Properties saved successfully.", INFO);
		}
	}

	public static void removeFile() throws IOException {
		if (isInitialized) {
			inputStream.close();
			outputStream.close();

			logger.printMessage("Removing existing properties file...", INFO);
			logger.printMessage("Path: " + propFileLocation + propFileName, DATA);
			try {
				Files.deleteIfExists(Paths.get(propFileLocation + propFileName));
			} catch (IOException ex) {
				logger.printMessage("Unable to remove properties file. " + "It may be in use by another process.",
						WARNING);
				return;
			}
			logger.printMessage("File removed successfully.", INFO);

			isInitialized = false;
		}
	}

	public static void setPropertyFileName(String propFileName) throws IOException {
		PropertiesFile.propFileName = propFileName;

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);
	}

	public static String getPropertyFileName() {
		return propFileName;
	}

	public static void setPropertyFileLocation(String propFileLocation) throws IOException {
		PropertiesFile.propFileLocation = propFileLocation;

		inputStream = new FileInputStream(propFileLocation + propFileName);
		properties.load(inputStream);
	}

	public static String getPropertyFileLocation() {
		return propFileLocation;
	}

	public void setVerbose(boolean verbose) {
		logger.setVerbose(verbose);
	}

	public boolean getVerbose() {
		return logger.getVerbose();
	}

	public void setMessageLevel(int messageLevel) {
		logger.setMessageLevel(messageLevel);
	}

	public int getMessageLevel() {
		return logger.getMessageLevel();
	}
}