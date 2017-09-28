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
package application.client.transfer;

import static application.messaging.Messages.*;
import java.io.*;

/**
 *
 * @author User
 */
public class FileTransferSC {
    private String filePath;
    private String fileName;
    private int fileSize;
    private byte[] fileData;
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    
    private InputStream socketInputStream;
    private InputStreamReader socketInputStreamReader;
    private BufferedReader in;
    
    private boolean verbose;
    private int messageLevel;
    
    public FileTransferSC(InputStream socketInputStream) 
            throws FileNotFoundException {
        this.filePath = "";
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public FileTransferSC(InputStream socketInputStream, boolean verbose, 
            int messageLevel) throws FileNotFoundException {
        this.filePath = "";
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public FileTransferSC(InputStream socketInputStream, String filePath) 
            throws FileNotFoundException {
        this.filePath = filePath;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public FileTransferSC(InputStream socketInputStream, String filePath, 
            boolean verbose, int messageLevel) throws FileNotFoundException {
        this.filePath = filePath;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public void retrieveFileName() throws IOException {
        printMessage("Requesting file name from server...", HIGHEST_PRIORITY);
        this.fileName = in.readLine();
        if (fileName == null) {
            printMessage("line 107: fileNames is equal to null: Unable to "
                    + "retrieve file names.", ERROR);
        }
        
        this.fileOutputStream = new FileOutputStream(filePath + fileName);
        this.bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        printMessage("File name retrieved successfully. File name is " + 
                fileName, HIGHEST_PRIORITY);
    }
    
    public void setFileName() throws IOException {
        this.fileName = in.readLine();
        
        this.fileOutputStream = new FileOutputStream(filePath + fileName);
        this.bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }
    
    public void retrieveFileSize() throws IOException {
        printMessage("Requesting file size from server...", HIGHEST_PRIORITY);
        String fileSizeValue = in.readLine();
        try {
            this.fileSize = Integer.parseInt(fileSizeValue);
        } catch (NumberFormatException ex) {
            printMessage("line 130: The file size being sent from the server "
                    + "is too large to handle: The file size must be less "
                    + "than 2.147483647 Gigabytes: Terminating...", ERROR);
        }
        printMessage("File size retrieved successfully. File size is " + 
                fileSize + " bytes.", HIGHEST_PRIORITY);
    }
    
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    
    public int getFileSize() {
        return fileSize;
    }
    
    public void retrieveFileData() throws IOException, InterruptedException {
        printMessage("Requesting file data from server...", HIGHEST_PRIORITY);
        
        fileData = new byte[fileSize];
        int byteEnd;
        for (int byteStart = 0; byteStart < fileSize; 
                byteStart += byteEnd) {
            byteEnd = 65536;
            if (byteStart + byteEnd > fileSize) {
                byteEnd = fileSize - byteStart;
            }

            socketInputStream.read(fileData, byteStart, byteEnd);
        }
        
        printMessage("File data retrieved successfully.", HIGHEST_PRIORITY);
    }
    
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
    
    public byte[] getFileData() {
        return fileData;
    }
    
    public void writeToFile() throws IOException {
        printMessage("Writing " + fileSize + " bytes to file \"" + filePath + 
                fileName + "\"...", HIGHEST_PRIORITY);
        bufferedOutputStream.write(fileData, 0, fileData.length);
        bufferedOutputStream.flush();
        printMessage("Successfully wrote bytes to file \"" + filePath +
                fileName + "\".", HIGHEST_PRIORITY);
    }
    
    public void setFilePath(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        this.fileOutputStream = new FileOutputStream(filePath);
        this.bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }
    
    public String getFilePath() {
        return filePath;
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
            System.out.println("FileTransferSC: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("FileTransferSC: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}