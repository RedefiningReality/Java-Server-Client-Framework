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

import application.client.properties.ClientProperties;
import static application.messaging.Messages.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    
    private static Text chatText;
    private static Text connectionInfo;
    private static TextField chatTextField;
    
    private boolean verbose;
    private int messageLevel;
    
    public MessageReceiver(InputStream socketInputStream) throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = ClientProperties.getProperty("senderName");
        
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
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = ClientProperties.getProperty("senderName");
        
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
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = ClientProperties.getProperty("senderName");
        
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
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.receiverName = ClientProperties.getProperty("senderName");
        
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
        String acceptStringTransferString = ClientProperties
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
        String acceptStringTransferString = ClientProperties
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
        String acceptStringTransferString = ClientProperties
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
        String acceptStringTransferString = ClientProperties
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
        if (acceptStringTransfer) {
            while (true) {
                try {
                    String fullMessage = in.readLine();
                    System.out.println(fullMessage);
                    
                    String senderName = 
                            fullMessage.substring(0,
                                    fullMessage.indexOf(':'));
                    System.out.println(senderName);
                    
                    String message = 
                            fullMessage.substring(fullMessage.indexOf(':') + 2);
                    System.out.println(message);
                    
                    String currentText = chatText.getText();
                    System.out.println(currentText);
                    
                    String oldSender;
                    int lastNewline = currentText.lastIndexOf("\n\n");
                    int lastSenderIndex = currentText.indexOf(": ", lastNewline);
                    
                    if (lastNewline <= -1 || lastSenderIndex <= -1)
                        oldSender = "";
                    else
                        oldSender = currentText.substring(lastNewline + 2, 
                                lastSenderIndex);
                    
                    printMessage("Last Sender Name Start Index: " 
                            + lastNewline + 2, DATA);
                    printMessage("Last Sender Name End Index: " 
                            + currentText.indexOf(": ", lastNewline), DATA);
                    printMessage("Current Sender Name: " + senderName, DATA);
                    printMessage("Last Sender Name: " + oldSender, DATA);
                    printMessage("Message: " + message + "\n", DATA);
                    
                    if (senderName.equals(oldSender))
                        chatText.setText(currentText + message + "\n");
                    else
                        chatText.setText(currentText + "\n" + senderName + ": "
                                + message + "\n");

                    if (!message.equals("") && "/".equals(message.substring(0, 1)))
                        checkCommands(message);
                    
                } catch (IOException ex) {
                    printMessage("Server ended connection. Exiting chat...", 
                            INFO);
                    System.exit(0);
                }
            }
        } else
            printMessage("line 226: acceptStringTransfer is set to false: "
                    + "Permission was not given to transfer strings.", ERROR);
    }
    
    private void checkCommands(String message) {
        if (message.toLowerCase().contains("")) {
            
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
    
    public static void setChatText(Text chatText) {
        MessageReceiver.chatText = chatText;
    }
    
    public static void setConnectionInfo(Text connectionInfo) {
        MessageReceiver.connectionInfo = connectionInfo;
    }
    
    public static void setChatTextField(TextField chatTextField) {
        MessageReceiver.chatTextField = chatTextField;
    }
    
    public static void test() {
        chatText.setText("test");
        connectionInfo.setText("testing");
        chatTextField.setPromptText("test");
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
