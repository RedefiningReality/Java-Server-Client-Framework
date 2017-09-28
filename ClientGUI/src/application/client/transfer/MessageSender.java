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
import application.client.values.Values;
import static application.messaging.Messages.DATA;
import static application.messaging.Messages.ERROR;
import static application.messaging.Messages.HIGHEST_PRIORITY;
import static application.messaging.Messages.INFO;
import static application.messaging.Messages.WARNING;
import static application.protocol.DataProtocol.ABORT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author User
 */
public class MessageSender extends Thread {
    private boolean acceptStringTransfer;
    private String senderName;
    
    private InputStream systemInputStream;
    private InputStreamReader systemInputStreamReader;
    private BufferedReader in;
    
    private OutputStream socketOutputStream;
    private PrintWriter out;
    
    private static Text chatText;
    private static Text connectionInfo;
    private static TextField chatTextField;
    
    private boolean verbose;
    private int messageLevel;
    
    public MessageSender(OutputStream socketOutputStream) throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = ClientProperties.getProperty("senderName");
        
        this.systemInputStream = System.in;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageSender(OutputStream socketOutputStream, boolean verbose, 
            int messageLevel) throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = Values.getSenderName();
        
        this.systemInputStream = System.in;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageSender(InputStream systemInputStream, 
            OutputStream socketOutputStream) throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = ClientProperties.getProperty("senderName");
        
        this.systemInputStream = systemInputStream;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageSender(InputStream systemInputStream, 
            OutputStream socketOutputStream, boolean verbose, int messageLevel) 
            throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = ClientProperties.getProperty("senderName");
        
        this.systemInputStream = systemInputStream;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageSender(String senderName, OutputStream socketOutputStream) 
            throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = senderName;
        
        this.systemInputStream = System.in;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageSender(String senderName, OutputStream socketOutputStream, 
            boolean verbose, int messageLevel) throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = senderName;
        
        this.systemInputStream = System.in;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageSender(String senderName, InputStream systemInputStream, 
            OutputStream socketOutputStream) throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = senderName;
        
        this.systemInputStream = systemInputStream;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MessageSender(String senderName, InputStream systemInputStream, 
            OutputStream socketOutputStream, boolean verbose, int messageLevel) 
            throws IOException {
        String acceptStringTransferString = ClientProperties
                .getProperty("acceptStringTransfer");
        this.acceptStringTransfer = Boolean.valueOf(acceptStringTransferString);
        this.senderName = senderName;
        
        this.systemInputStream = systemInputStream;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MessageSender(String senderName, InputStream systemInputStream, 
            OutputStream socketOutputStream, boolean acceptStringTransfer, 
            boolean verbose, int messageLevel) throws IOException {
        this.acceptStringTransfer = acceptStringTransfer;
        this.senderName = senderName;
        
        this.systemInputStream = systemInputStream;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
        
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    @Override
    public void run() {
        if (acceptStringTransfer) {
            chatTextField.setOnKeyPressed((KeyEvent keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String message = chatTextField.getText();
                    
                    out.println(senderName + ": " + message);
                    
                    String currentText = chatText.getText();
                    
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
                    
                    chatTextField.setText("");
                }
            });
        } else
            printMessage("line 226: acceptStringTransfer is set to false: "
                    + "Permission was not given to transfer strings.", ERROR);
    }
    
    private void checkCommands(String message) {
        if ("".equals(message.toLowerCase())) {
            
        }
    }
    
    public void setAcceptStringTransfer(boolean acceptStringTransfer) {
        this.acceptStringTransfer = acceptStringTransfer;
    }
    
    public boolean getAcceptStringTransfer() {
        return acceptStringTransfer;
    }
    
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    public String getSenderName() {
        return senderName;
    }
    
    public void setSystemInputStream(InputStream systemInputStream) {
        this.systemInputStream = systemInputStream;
        this.systemInputStreamReader = new InputStreamReader(systemInputStream);
        this.in = new BufferedReader(systemInputStreamReader);
    }
    
    public InputStream getSystemInputStream() {
        return systemInputStream;
    }
    
    public void setSocketOutputStream(OutputStream socketOutputStream) {
        this.socketOutputStream = socketOutputStream;
        this.out = new PrintWriter(socketOutputStream);
    }
    
    public OutputStream getSocketOutputStream() {
        return socketOutputStream;
    }
    
    public static void setChatText(Text chatText) {
        MessageSender.chatText = chatText;
    }
    
    public static void setConnectionInfo(Text connectionInfo) {
        MessageSender.connectionInfo = connectionInfo;
    }
    
    public static void setChatTextField(TextField chatTextField) {
        MessageSender.chatTextField = chatTextField;
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
            System.out.println("MessageSender: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("MessageSender: " + message);
        }
        if (level == ERROR) {
            out.println(ABORT);
            System.exit(ERROR);
        }
    }
}
