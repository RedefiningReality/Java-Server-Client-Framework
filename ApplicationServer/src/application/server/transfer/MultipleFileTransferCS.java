/*
 * Copyright (c) 2014, John Ford. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of John Ford or the names of any
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package application.server.transfer;

import static application.messaging.Messages.*;
import static application.protocol.DataProtocol.ABORT;
import java.io.*;

/**
 *
 * @author User
 */
public class MultipleFileTransferCS {
    private int numberOfFiles;
    private String filePath;
    private String[] fileNames;
    private int[] fileSizes;
    private byte[] fileData[];
    private FileOutputStream fileOutputStreams[];
    private BufferedOutputStream bufferedOutputStreams[];
    
    private InputStream socketInputStream;
    private InputStreamReader socketInputStreamReader;
    private BufferedReader in;
    
    private boolean verbose;
    private int messageLevel;
    
    public MultipleFileTransferCS(InputStream socketInputStream) throws IOException {
        this.filePath = "";
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MultipleFileTransferCS(InputStream socketInputStream, boolean verbose, 
            int messageLevel) throws IOException {
        this.filePath = "";
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MultipleFileTransferCS(InputStream socketInputStream, String filePath) 
            throws IOException {
        this.filePath = filePath;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MultipleFileTransferCS(InputStream socketInputStream, String filePath,
            boolean verbose, int messageLevel) throws IOException {
        this.filePath = filePath;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public void retrieveNumberOfFiles() throws IOException {
        String numberOfFilesValue = in.readLine();
        
        try {
        this.numberOfFiles = Integer.parseInt(numberOfFilesValue);
        } catch (NumberFormatException ex) {
            if (numberOfFilesValue.equals(ABORT)) {
                printMessage("line 112: The client has encountered an error: The"
                        + " client was most likely attempting to send a file "
                        + "which does not exist: Terminating...", ERROR);
            } else {
                printMessage("line 116: " + ex + ": Terminating...", ERROR);
            }
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
    
    public void retrieveFileNames() throws IOException {
        printMessage("Requesting file names from client...", HIGHEST_PRIORITY);
        
        for (int i = 0; i < numberOfFiles; i++) {
            this.fileNames[i] = in.readLine();
            if (fileNames == null) {
                printMessage("line 100: fileNames is equal to null: "
                       + "Unable to retrieve file names.", ERROR);
            }
            
            this.fileOutputStreams[i] = 
                    new FileOutputStream(filePath + fileNames[i]);
            this.bufferedOutputStreams[i] = 
                    new BufferedOutputStream(fileOutputStreams[i]);
        }
        
        printMessage("File names retrieved successfully.", HIGHEST_PRIORITY);
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
    
    public void retrieveFileSizes() throws IOException {
        printMessage("Requesting file sizes from client...", HIGHEST_PRIORITY);
        
        for (int i = 0; i < numberOfFiles; i++) {
            String fileSizeValue = in.readLine();
            try {
                this.fileSizes[i] = Integer.parseInt(fileSizeValue);
            } catch (NumberFormatException ex) {
                printMessage("line 165: The file size being sent from the client"
                        + " is too large to handle: The file size must be less "
                        + "than 2.147483647 Gigabytes: Terminating...", ERROR);
            }
        }
        
        printMessage("File size retrieved successfully.", HIGHEST_PRIORITY);
    }
    
    public void setFileSizes(int[] fileSizes) {
        this.fileSizes = fileSizes;
    }
    
    public int[] getFileSizes() {
        return fileSizes;
    }
    
    public void retrieveFileData() throws IOException {
        printMessage("Requesting file data from client...", HIGHEST_PRIORITY);
        
        for (int i = 0; i < numberOfFiles; i++) {
            fileData[i] = new byte[fileSizes[i]];
            int fileSize = fileSizes[i];
            
            int byteEnd;
            for (int byteStart = 0; byteStart < fileSize; 
                    byteStart += byteEnd) {
                byteEnd = 65536;
                if (byteStart + byteEnd > fileSize) {
                    byteEnd = fileSize - byteStart;
                }
                
                socketInputStream.read(fileData[i], byteStart, byteEnd);
            }
        }
        
        printMessage("File data retrieved successfully.", HIGHEST_PRIORITY);
    }
    
    public void setFileData(byte[] fileData[]) {
        this.fileData = fileData;
    }
    
    public byte[][] getFileData() {
        return fileData;
    }
    
    public void writeToFiles() throws IOException {
        for (int i = 0; i < numberOfFiles; i++) {
            printMessage("Writing " + fileSizes[i] + " bytes to file \"" 
                    + filePath + fileNames[i] + "\"...", HIGHEST_PRIORITY);
            bufferedOutputStreams[i].write(fileData[i], 0, fileData[i].length);
            bufferedOutputStreams[i].flush();
            printMessage("Successfully wrote bytes to file \"" + 
                    filePath + fileNames[i] + "\".", HIGHEST_PRIORITY);
        }
    }
    
    public void setInputStream(InputStream socketInputStream) {
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
    }
    
    public InputStream getInputStream() {
        return socketInputStream;
    }
    
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public boolean getVerbose() {
        return verbose;
    }
    
    public void setMessageLevel(int messageLevel) {
        this.messageLevel = messageLevel;
    }
    
    public int getMessageLevel() {
        return messageLevel;
    }
    
    protected void printMessage(String message, int level) {
        String stringLevel;
        if (level == ERROR) {
            stringLevel = "ERROR";
        } else if (level == WARNING) {
            stringLevel = "WARNING";
        } else if (level == INFO) {
            stringLevel = "INFO";
        } else if (level == DATA) {
            stringLevel = "DATA";
        } else {
            stringLevel = "HIGHEST_PRIORITY";
        }
        
        if ((verbose && messageLevel >= level) && level != HIGHEST_PRIORITY) {
            System.out.println("MultipleFileTransfer: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("MultipleFileTransfer: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}