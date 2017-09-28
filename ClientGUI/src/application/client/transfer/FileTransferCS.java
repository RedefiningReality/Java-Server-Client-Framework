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
import static application.protocol.DataProtocol.*;
import application.client.properties.ClientProperties;
import application.client.values.Values;
import java.io.*;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author User
 */
public class FileTransferCS extends Thread {
    private boolean acceptFileTransfer;
    
    private File transferFile;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    
    private OutputStream socketOutputStream;
    private PrintWriter out;
    
    private static ProgressBar progressBar;
    private static Button finishButton;
    
    private boolean verbose;
    private int messageLevel;
    
    public FileTransferCS(OutputStream socketOutputStream) throws IOException {
        String acceptFileTransferString = ClientProperties
                .getProperty("acceptFileTransfer");
        this.acceptFileTransfer = Boolean.valueOf(acceptFileTransferString);
        String transferFilePath = ClientProperties.getProperty("transferFilePath");
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public FileTransferCS(OutputStream socketOutputStream, boolean verbose, 
            int messageLevel) throws IOException {
        String acceptFileTransferString = ClientProperties
                .getProperty("acceptFileTransfer");
        this.acceptFileTransfer = Boolean.valueOf(acceptFileTransferString);
        String transferFilePath = ClientProperties.getProperty("transferFilePath");
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public FileTransferCS(boolean acceptFileTransfer, 
            OutputStream socketOutputStream, boolean verbose, int messageLevel)
            throws IOException {
        this.acceptFileTransfer = acceptFileTransfer;
        ClientProperties.setProperty("acceptFileTransfer", 
                String.valueOf(acceptFileTransfer));
        String transferFilePath = Values.getTransferFilePath();
        this.transferFile = new File(transferFilePath);
        this.fileInputStream = new FileInputStream(transferFile);
        this.bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public FileTransferCS(boolean acceptFileTransfer, String transferFilePath, 
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
    
    public FileTransferCS(boolean acceptFileTransfer, String transferFilePath, 
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
    
    @Override
    public void run() {
        try {
            sendFileName();
            sendFileSize();
            transferFile();
        } catch (IOException | InterruptedException ex) {
            printMessage("Server ended connection. Exiting file transfer...", 
                    INFO);
            System.exit(0);
        }
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
            printMessage("Sending file size to server...", HIGHEST_PRIORITY);
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

                printMessage("Sending file to server...", HIGHEST_PRIORITY);
                
                int byteEnd;
                if (byteArray.length/10 > 65536)
                    byteEnd = 65536;
                else
                    byteEnd = byteArray.length/10;
                
                for (int byteStart = 0; byteStart < byteArray.length; 
                        byteStart += byteEnd) {
                    
                    if (byteStart + byteEnd > byteArray.length) {
                        byteEnd = byteArray.length - byteStart;
                    }
                    
                    progressBar.setProgress(byteStart/(double) byteArray.length);
                    
                    sleep(100);
                    socketOutputStream.write(byteArray, byteStart, byteEnd);
                    socketOutputStream.flush();
                }
                
                printMessage("File sent to server successfully.", HIGHEST_PRIORITY);
            } catch (NegativeArraySizeException ex) {
                sleep(100);
            }
            
            progressBar.setProgress(1d);
            finishButton.setDisable(false);
            
        } else {
            printMessage("line 196: acceptFileTransfer is set to false: "
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
    
    public static void setProgressBar(ProgressBar progressBar) {
        FileTransferCS.progressBar = progressBar;
    }
    
    public static void setFinishButton(Button finishButton) {
        FileTransferCS.finishButton = finishButton;
    }
    
    public static void test() {
        progressBar.setProgress(0.5d);
        finishButton.setText("test");
        finishButton.setDisable(false);
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