package framework.csv;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.INFO;
import static framework.logging.MessageLevels.WARNING;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import framework.logging.Logger;

public class CSVFile {
	
	private static boolean isInitialized = false;

	private static String csvFileName;
	private static String csvFileLocation;

	private static List<List<String>> data;

	private static Logger logger;

	public static void initialize() throws FileNotFoundException, IOException {
		csvFileName = "data.csv";
		csvFileLocation = "";
		loadFile();

		logger = new Logger("CSVFile");

		isInitialized = true;
	}

	public static void initialize(String csvFileName) throws IOException {
		CSVFile.csvFileName = csvFileName;
		csvFileLocation = "";
		loadFile();

		logger = new Logger("CSVFile");

		isInitialized = true;
	}

	public static void initialize(String csvFileName, String csvFileLocation) throws IOException {
		CSVFile.csvFileName = csvFileName;
		CSVFile.csvFileLocation = csvFileLocation;
		loadFile();

		logger = new Logger("CSVFile");

		isInitialized = true;
	}

	public static void initialize(String csvFileName, boolean verbose, int messageLevel) throws IOException {
		CSVFile.csvFileName = csvFileName;
		csvFileLocation = "";
		loadFile();

		logger = new Logger("CSVFile", verbose, messageLevel);

		isInitialized = true;
	}

	public static void initialize(String csvFileName, String csvFileLocation, boolean verbose, int messageLevel)
			throws IOException {
		CSVFile.csvFileName = csvFileName;
		CSVFile.csvFileLocation = csvFileLocation;
		loadFile();

		logger = new Logger("CSVFile", verbose, messageLevel);

		isInitialized = true;
	}

	public static boolean isInitialized() {
		return isInitialized;
	}
	
	public static boolean addRow() {
		return addRow(new String[] {});
	}
	
	public static boolean addRow(String label) {
		return addRow(new String[] {label});
	}
	
	public static boolean addRow(String label, int position) {
		return addRow(new String[] {label}, position);
	}
	
	public static boolean addRow(String[] row) {
		return addRow(row, data.size());
	}
	
	public static boolean addRow(String[] row, int position) {
		if(isInitialized && position >= 0 && position <= data.size()) {
			data.add(position, prepareRow(row));
			return true;
		} else
			return false;
	}
	
	private static List<String> prepareRow(String[] row) {
		List<String> rowList = new ArrayList<String>(Arrays.asList(row));
		
		int length = 0;
		if(!data.isEmpty())
			length = data.get(0).size();
		
		if(length < row.length)
			for(int i = 0; i < data.size(); i++)
				for(int j = length; j < row.length; j++)
					data.get(i).add("");
		else
			for(int i = row.length; i < length; i++)
				rowList.add("");
		
		return rowList;
	}
	
	public static boolean addColumn() {
		return addColumn(new String[] {});
	}
	
	public static boolean addColumn(String label) {
		return addColumn(new String[] {label});
	}
	
	public static boolean addColumn(String label, int position) {
		return addColumn(new String[] {label}, position);
	}
	
	public static boolean addColumn(String[] col) {
		return addColumn(col, data.isEmpty() ? 0 : data.get(0).size());
	}
	
	public static boolean addColumn(String[] col, int position) {
		if(isInitialized && position >= 0) {
			if((!data.isEmpty() && position > data.get(0).size())
					|| (data.isEmpty() && position != 0))
				return false;
			
			List<String> colList = prepareColumn(col);
			
			for(int i = 0; i < colList.size(); i++)
				data.get(i).add(position, colList.get(i));
			return true;
		} else
			return false;
	}
	
	private static List<String> prepareColumn(String[] col) {
		List<String> colList = new ArrayList<String>(Arrays.asList(col));
		
		if(data.size() < col.length) {
			int blankSize = 0;
			if(data.size() > 0)
				blankSize = data.get(0).size();
			
			for(int i = data.size(); i < col.length; i++) {
				String[] blank = new String[blankSize];
				data.add(new ArrayList<String>(Arrays.asList(blank)));
			}
		} else
			for(int i = col.length; i < data.size(); i++)
				colList.add("");
		
		return colList;
	}
	
	public static String[] getRow(String label) {
		return getRow(getRowPosition(label));
	}
	
	public static int getRowPosition(String label) {
		for(int i = 0; i < data.size(); i++)
			if(data.get(i).get(0).equals(label))
				return i;
		
		return -1;
	}
	
	public static String[] getRow(int position) {
		if(position < 0 || position >= data.size())
			return null;
		
		return data.get(position).toArray(new String[data.get(position).size()]);
	}
	
	public static boolean setRow(String[] row) {
		if(row.length == 0)
			return false;
		
		return setRow(getRowPosition(row[0]), row);
	}
	
	public static boolean setRow(int position, String[] row) {
		if(isInitialized && position >= 0 && position <= data.size()) {
			data.set(position, prepareRow(row));
			return true;
		} else
			return false;
	}
	
	public static String[] getColumn(String label) {
		return getColumn(getColumnPosition(label));
	}
	
	public static int getColumnPosition(String label) {
		if(data.isEmpty())
			return -1;
		
		for(int i = 0; i < data.get(0).size(); i++)
			if(data.get(0).get(i).equals(label))
				return i;
		
		return -1;
	}
	
	public static String[] getColumn(int position) {
		if(position < 0 || data.isEmpty() || position >= data.get(0).size())
			return null;
		
		String[] col = new String[data.size()];
		for(int i = 0; i < col.length; i++)
			col[i] = data.get(i).get(position);
		
		return col;
	}
	
	public static boolean setColumn(String label, String[] col) {
		return setColumn(getColumnPosition(label), col);
	}
	
	public static boolean setColumn(int position, String[] col) {
		if(isInitialized && position >= 0) {
			if((!data.isEmpty() && position > data.get(0).size()) || position != 0)
				return false;
			
			List<String> colList = prepareColumn(col);
			
			for(int i = 0; i < colList.size(); i++)
				data.get(i).set(position, colList.get(i));
			return true;
		} else
			return false;
	}
	
	public static boolean setElement(int row, int col, String val) {
		if(checkLocation(row, col)) {
			data.get(row).set(col, val);
			return true;
		} else
			return false;
	}
	
	public static String getElement(int row, int col) {
		if(checkLocation(row, col))
			return data.get(row).get(col);
		else
			return null;
	}
	
	private static boolean checkLocation(int row, int col) {
		return isInitialized && row >= 0 && row < data.size()
				&& col >= 0 && !data.isEmpty() && col < data.get(0).size();
	}

	public static void saveToFile() throws IOException {
		if (isInitialized) {
			logger.printMessage("Removing existing csv file...", INFO);
			logger.printMessage("Path: " + csvFileLocation + csvFileName, DATA);
			try {
				Files.deleteIfExists(Paths.get(csvFileLocation + csvFileName));
			} catch (IOException ex) {
				logger.printMessage("Unable to save to csv file. " + "It may be in use by another process.",
						WARNING);
				return;
			}
			logger.printMessage("File removed successfully.", INFO);

			FileWriter writer = new FileWriter(csvFileLocation + csvFileName);

			logger.printMessage("Saving csv data to file...", INFO);
			for (List<String> row : data) {
			    writer.append(String.join(",", row));
			    writer.append("\n");
			}

			writer.flush();
			writer.close();
			logger.printMessage("Csv data saved successfully.", INFO);
		}
	}

	public static void removeFile() throws IOException {
		if (isInitialized) {
			logger.printMessage("Removing existing csv file...", INFO);
			logger.printMessage("Path: " + csvFileLocation + csvFileName, DATA);
			try {
				Files.deleteIfExists(Paths.get(csvFileLocation + csvFileName));
			} catch (IOException ex) {
				logger.printMessage("Unable to remove csv file. " + "It may be in use by another process.",
						WARNING);
				return;
			}
			logger.printMessage("File removed successfully.", INFO);

			isInitialized = false;
		}
	}
	
	private static void loadFile() throws IOException {
		data = new ArrayList<List<String>>();
		if (new File(csvFileLocation + csvFileName).exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(csvFileLocation + csvFileName));
			
			String row;
			while ((row = reader.readLine()) != null)
			    data.add(new ArrayList<String>(Arrays.asList(row.split(","))));
			
			reader.close();
		}
	}

	public static void setCSVFileName(String csvFileName) throws IOException {
		CSVFile.csvFileName = csvFileName;
		loadFile();
	}

	public static String getCSVFileName() {
		return csvFileName;
	}

	public static void setCSVFileLocation(String csvFileLocation) throws IOException {
		CSVFile.csvFileLocation = csvFileLocation;
		loadFile();
	}

	public static String getCSVFileLocation() {
		return csvFileLocation;
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
