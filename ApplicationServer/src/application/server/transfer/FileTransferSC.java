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
import static application.protocol.DataProtocol.*;
import application.server.properties.ServerProperties;
import java.io.*;
import static java.lang.Thread.sleep;

/**
 *
 * @author User
 */
public class FileTransferSC {
    private boolean acceptFileTransfer;
    
    private File transferFile;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    
    private OutputStream socketOutputStream;
    private PrintWriter out;
    
    ServerProperties serverProperties = new ServerProperties();
    
    private boolean verbose;
    private int messageLevel;
    
    public FileTransferSC(OutputStream socketOutputStream) throws IOException {
        String acceptFileTransferString = serverProperties
                .getProperty("acceptFileTransfer");
        this.acceptFileTransfer = Boolean.valueOf(acceptFileTransferString);
        String transferFilePath = serverProperties.getProperty("transferFilePath");
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public FileTransferSC(OutputStream socketOutputStream, boolean verbose, 
            int messageLevel) throws IOException {
        String acceptFileTransferString = serverProperties
                .getProperty("acceptFileTransfer");
        this.acceptFileTransfer = Boolean.valueOf(acceptFileTransferString);
        String transferFilePath = serverProperties.getProperty("transferFilePath");
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public FileTransferSC(boolean acceptFileTransfer, String transferFilePath, 
            OutputStream socketOutputStream) throws IOException {
        this.acceptFileTransfer = acceptFileTransfer;
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public FileTransferSC(boolean acceptFileTransfer, String transferFilePath, 
            OutputStream socketOutputStream, boolean verbose, int messageLevel) 
            throws IOException {
        this.acceptFileTransfer = acceptFileTransfer;
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public void sendFileName() {
        if (acceptFileTransfer) {
            out.println(transferFile.getName());
        } else {
            printMessage("line 123: acceptFileTransfer is set to false: "
                    + "Permission was not given to transfer file.", ERROR);
        }
    }
    
    public void sendFileSize() throws IOException {
        if (acceptFileTransfer) {
            printMessage("Sending file size to client...", HIGHEST_PRIORITY);
            String transferFileValue = String.valueOf(transferFile.length());
            out.println(transferFileValue);
            printMessage("File size sent successfully.", HIGHEST_PRIORITY);
        } else {
            printMessage("line 100: acceptFileTransfer is set to false: "
                    + "Permission was not given to transfer file.", ERROR);
        }
    }
    
    public void transferFile() throws IOException, InterruptedException {
        if (acceptFileTransfer) {
            try {
                byte[] byteArray = new byte[(int) transferFile.length()];
                bufferedInputStream.read(byteArray, 0, byteArray.length);

                printMessage("Sending file to client...", HIGHEST_PRIORITY);
                
                int byteEnd;
                for (int byteStart = 0; byteStart < byteArray.length; 
                        byteStart += byteEnd) {
                    byteEnd = 65536;
                    if (byteStart + byteEnd > byteArray.length) {
                        byteEnd = byteArray.length - byteStart;
                    }
                    
                    sleep(1000);
                    socketOutputStream.write(byteArray, byteStart, byteEnd);
                    socketOutputStream.flush();
                }
                
                printMessage("File sent to client successfully.", HIGHEST_PRIORITY);
            } catch (NegativeArraySizeException ex) {
                sleep(1000);
            }
        } else {
            printMessage("line 156: acceptFileTransfer is set to false: "
                    + "Permission was not given to transfer file.", ERROR);
        }
    }
    
    public void setAcceptFileTransfer(boolean acceptFileTransfer) {
        this.acceptFileTransfer = acceptFileTransfer;
    }
    
    public boolean getAcceptFileTransfer() {
        return acceptFileTransfer;
    }
    
    public void setFilePath(String transferFilePath) throws FileNotFoundException {
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
    }
    
    public String getFilePath() {
        return transferFile.getAbsolutePath();
    }
    
    public void setOutputStream(OutputStream socketOutputStream) {
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream);
    }
    
    public OutputStream getOutputStream() {
        return socketOutputStream;
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
            System.out.println("FileTransfer: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("FileTransfer: " + message);
        }
        if (level == ERROR) {
            out.println(ABORT);
            System.exit(ERROR);
        }
    }
}