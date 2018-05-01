import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static framework.logging.MessageLevels.*;

import framework.logging.Logger;
import framework.transfer.Transfer;
import gui.dialog.progress.ProgressDialog;
import javafx.concurrent.Task;

public class FileTransfer extends Transfer {
	private Logger logger;
	
	private int numberOfFiles;
	private String filePath;
    private String[] fileNames;
    private int[] fileSizes;
    private byte[][] fileData;
    
    private FileOutputStream[] fileOutputStreams;
    private BufferedOutputStream[] bufferedOutputStreams;
    
    public FileTransfer(String filePath, boolean verbose, int messageLevel) throws FileNotFoundException {
        this.filePath = filePath;
        
        this.logger = new Logger("FileTransfer", verbose, messageLevel);
    }
    
    @Override
	public void start(InputStream inStream, OutputStream outStream) throws IOException {
		BufferedReader inReader = new BufferedReader(new InputStreamReader(inStream));
		
		retrieveNumberOfFiles(inReader);
		retrieveFileNames(inReader);
		retrieveFileSizes(inReader);
		retrieveFileData(inStream);
		writeToFiles();
	}
    
    public void retrieveNumberOfFiles(BufferedReader in) throws IOException {
        String numberOfFilesValue = in.readLine();
        
        try {
        	this.numberOfFiles = Integer.parseInt(numberOfFilesValue);
        } catch (NumberFormatException ex) {
            logger.printMessage("line 116: " + ex + ": Terminating...", ERROR);
        }
        
        this.fileNames = new String[numberOfFiles];
        this.fileSizes = new int[numberOfFiles];
        this.fileData = new byte[numberOfFiles][];
        this.fileOutputStreams = new FileOutputStream[numberOfFiles];
        this.bufferedOutputStreams = new BufferedOutputStream[numberOfFiles];
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void retrieveFileNames(BufferedReader in) throws IOException {
        logger.printMessage("Requesting file names from server...", HIGHEST_PRIORITY);
        
        for (int i = 0; i < numberOfFiles; i++) {
            this.fileNames[i] = in.readLine();
            if (fileNames == null) {
                logger.printMessage("line 100: fileNames is equal to null: "
                       + "Unable to retrieve file names.", ERROR);
            }
            
            this.fileOutputStreams[i] = 
                    new FileOutputStream(filePath + fileNames[i]);
            this.bufferedOutputStreams[i] = 
                    new BufferedOutputStream(fileOutputStreams[i]);
        }
        
        logger.printMessage("File names retrieved successfully.", HIGHEST_PRIORITY);
    }
    
    public void setFileNames(String[] fileNames) throws IOException {
        for (int i = 0; i < numberOfFiles; i++) {
            this.fileNames[i] = fileNames[i];
            
            this.fileOutputStreams[i] = 
                    new FileOutputStream(filePath + fileNames[i]);
            this.bufferedOutputStreams[i] = 
                    new BufferedOutputStream(fileOutputStreams[i]);
        }
    }
    
    public String[] getFileNames() {
        return fileNames;
    }
    
    public void retrieveFileSizes(BufferedReader in) throws IOException {
        logger.printMessage("Requesting file sizes from server...", HIGHEST_PRIORITY);
        
        for (int i = 0; i < numberOfFiles; i++) {
            String fileSizeValue = in.readLine();
            try {
                this.fileSizes[i] = Integer.parseInt(fileSizeValue);
            } catch (NumberFormatException ex) {
                logger.printMessage("line 165: The file size being sent from the server"
                        + " is too large to handle: The file size must be less "
                        + "than 2.147483647 Gigabytes: Terminating...", ERROR);
            }
        }
        
        logger.printMessage("File size retrieved successfully.", HIGHEST_PRIORITY);
    }
    
    public void setFileSizes(int[] fileSizes) {
        this.fileSizes = fileSizes;
    }
    
    public int[] getFileSizes() {
        return fileSizes;
    }
    
    public void retrieveFileData(InputStream in) throws IOException {
        logger.printMessage("Requesting file data from server...", HIGHEST_PRIORITY);
        
        Task<Void> task = new Task<Void>() {
        	@Override
			protected Void call() throws Exception {
        		int totalSize = 0;
                for (int size : fileSizes)
                	totalSize += size;
                
                int currentBytes = 0;
                for (int i = 0; i < numberOfFiles; i++) {
                	ProgressDialog.setText("      Downloading file " + (i+1) + " out of " + numberOfFiles + "...");
                	
                    fileData[i] = new byte[fileSizes[i]];
                    int fileSize = fileSizes[i];
                    
                    int byteEnd;
                    for (int byteStart = 0; byteStart < fileSize; 
                            byteStart += byteEnd) {
                        byteEnd = 65536;
                        if (byteStart + byteEnd > fileSize) {
                            byteEnd = fileSize - byteStart;
                        }
                        
                        ProgressDialog.setInfoText("      " + (currentBytes + byteStart)/1024 + " out of " + (totalSize/1024) + " KB downloaded.\n"
                    			+ "      " + (totalSize - (currentBytes + byteStart))/1024 + " KB remaining.");
                        updateProgress(currentBytes + byteStart, totalSize);
                        
                        in.read(fileData[i], byteStart, byteEnd);
                    }
                    
                    currentBytes += fileSize;
                }
                
                updateProgress(1, 1);
                ProgressDialog.setInfoText("      " + (totalSize/1024) + " out of " + (totalSize/1024) + " KB downloaded.\n"
                		+ "      0 KB remaining.");
                ProgressDialog.setText("      Download Complete.");
                
				return null;
			}
		};
        
        ProgressDialog.setTitle("File Transfer");
        ProgressDialog.setBarPrefHeight(30);
        ProgressDialog.setBarPrefWidth(500);
        ProgressDialog.start(task, true, DATA);
        
        logger.printMessage("File data retrieved successfully.", HIGHEST_PRIORITY);
    }
    
    public void setFileData(byte[] fileData[]) {
        this.fileData = fileData;
    }
    
    public byte[][] getFileData() {
        return fileData;
    }
    
    public void writeToFiles() throws IOException {
        for (int i = 0; i < numberOfFiles; i++) {
            logger.printMessage("Writing " + fileSizes[i] + " bytes to file \"" 
                    + filePath + fileNames[i] + "\"...", HIGHEST_PRIORITY);
            bufferedOutputStreams[i].write(fileData[i], 0, fileData[i].length);
            bufferedOutputStreams[i].flush();
            logger.printMessage("Successfully wrote bytes to file \"" + 
                    filePath + fileNames[i] + "\".", HIGHEST_PRIORITY);
        }
    }
}