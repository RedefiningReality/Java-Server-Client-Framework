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
import application.server.properties.ServerProperties;
import application.server.thread.MultiServerThread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author User
 */
public class MessageReceiver extends Thread {
    private boolean acceptStringTransfer;
    private String receiverName;
    
    private InputStream socketInputStream;
    private InputStreamReader socketInputStreamReader;
    private BufferedReader in;
    
    private OutputStream systemOutputStream;
    private PrintWriter out;
    
    ServerProperties serverProperties = new ServerProperties();
    
    private boolean loop;
    
    private boolean verbose;
    private int messageLevel;
    
    public MessageReceiver(InputStream socketInputStream) throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = serverProperties.getProperty("senderName");
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = System.out;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageReceiver(InputStream socketInputStream, boolean verbose, 
            int messageLevel) throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = serverProperties.getProperty("senderName");
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = System.out;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageReceiver(OutputStream systemOutputStream, 
            InputStream socketInputStream) throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = serverProperties.getProperty("senderName");
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = systemOutputStream;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageReceiver(OutputStream systemOutputStream, 
            InputStream socketInputStream, boolean verbose, int messageLevel) 
            throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = serverProperties.getProperty("senderName");
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = systemOutputStream;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageReceiver(String receiverName, InputStream socketInputStream) 
            throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = receiverName;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = System.out;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageReceiver(String receiverName, InputStream socketInputStream, 
            boolean verbose, int messageLevel) throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = receiverName;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = System.out;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageReceiver(String receiverName, OutputStream systemOutputStream, 
            InputStream socketInputStream) throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = receiverName;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = systemOutputStream;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageReceiver(String receiverName, OutputStream systemOutputStream, 
            InputStream socketInputStream, boolean verbose, int messageLevel) 
            throws IOException {
        String acceptStringTransferString = serverProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = receiverName;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = systemOutputStream;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageReceiver(String receiverName, OutputStream systemOutputStream, 
            InputStream socketInputStream, boolean acceptStringTransfer, 
            boolean verbose, int messageLevel) throws IOException {
        this.acceptStringTransfer = acceptStringTransfer;
        this.receiverName = receiverName;
        
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.systemOutputStream = systemOutputStream;
        this.out = new PrintWriter(systemOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    @Override
    public void run() {
        this.loop = true;
        if (acceptStringTransfer) {
            while (loop) {
                try {
                    String message = in.readLine();

                    if (message != null) {
                        if (message.contains("/")) {
                            checkCommands(message);
                        } else
                            out.println(message);
                    }
                } catch (IOException ex) {
                    out.println("Client ended connection. Exiting chat...");
                    break;
                }
            }
        } else
            printMessage("line 226: acceptStringTransfer is set to false: "
                    + "Permission was not given to transfer strings.", ERROR);
    }
    
    private void checkCommands(String message) {
        if (message.toLowerCase().contains("/exit")) {
            out.println("Exiting from chat...");
            printMessage("Recieved end connection request: Exiting chat...", 
                    INFO);
            synchronized (this) {
                this.notifyAll();
                this.loop = false;
            }
        }
    }
    
    public void setAcceptStringTransfer(boolean acceptStringTransfer) {
        this.acceptStringTransfer = acceptStringTransfer;
    }
    
    public boolean getAcceptStringTransfer() {
        return acceptStringTransfer;
    }
    
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    
    public String getReceiverName() {
        return receiverName;
    }
    
    public void setSystemOutputStream(OutputStream systemOutputStream) {
        this.systemOutputStream = systemOutputStream;
        this.out = new PrintWriter(systemOutputStream);
    }
    
    public OutputStream getSystemOutputStream() {
        return systemOutputStream;
    }
    
    public void setSocketInputStream(InputStream socketInputStream) {
        this.socketInputStream = socketInputStream;
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
    }
    
    public InputStream getSocketInputStream() {
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
            out.println("MessageReceiver: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            out.println("MessageReceiver: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}
