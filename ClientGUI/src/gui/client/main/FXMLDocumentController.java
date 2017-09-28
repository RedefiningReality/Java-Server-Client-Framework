/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.client.main;

import application.client.values.Values;
import static application.messaging.Messages.DATA;
import static application.messaging.Messages.ERROR;
import static application.messaging.Messages.HIGHEST_PRIORITY;
import static application.messaging.Messages.INFO;
import static application.messaging.Messages.WARNING;
import gui.client.chat.Chat;
import gui.client.filetransfer.clientserver.FileTransferCS;
import gui.client.filetransfer.serverclient.FileTransferSC;
import gui.client.messages.AlertMessages;
import gui.client.multiplefiletransfer.clientserver.MultipleFileTransferCS;
import gui.client.multiplefiletransfer.serverclient.MultipleFileTransferSC;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class FXMLDocumentController implements Initializable {
    
    private String hostName;
    private int PORT;
    
    private boolean verbose;
    private int messageLevel;
    
    @FXML
    private void chat() throws Exception {
        printMessage("Opening chat...", INFO);
        
        Chat chat = new Chat(hostName, PORT);
        Stage stage = new Stage();
        
        chat.start(stage);
        chat.connect();
    }
    
    @FXML
    private void fileTransferCS() throws Exception {
        printMessage("Opening file transfer (client to server)...", INFO);
        
        FileTransferCS ftcs = new FileTransferCS(hostName, PORT);
        Stage stage = new Stage();
        
        ftcs.start(stage);
        ftcs.connect();
    }
    
    @FXML
    private void fileTransferSC() throws Exception {
        printMessage("Opening file transfer (server to client)...", INFO);
        
        FileTransferSC ftsc = new FileTransferSC();
        Stage stage = new Stage();
        
        ftsc.start(stage);
    }
    
    @FXML
    private void multipleFileTransferCS() throws Exception {
        printMessage("Opening multiple file transfer (client to server)...", 
                INFO);
        
        MultipleFileTransferCS mftcs = new MultipleFileTransferCS();
        Stage stage = new Stage();
        
        mftcs.start(stage);
    }
    
    @FXML
    private void multipleFileTransferSC() throws Exception {
        printMessage("Opening multiple file transfer (server to client)...", 
                INFO);
        
        MultipleFileTransferSC mftsc = new MultipleFileTransferSC();
        Stage stage = new Stage();
        
        mftsc.start(stage);
    }
    
    @FXML
    private void retrieveHostName() throws IOException {
        printMessage("Retrieving new host name...", INFO);
        hostName = AlertMessages.getHostName();
        printMessage("Host name retrieved successfully.", INFO);
        printMessage("Host Name: " + hostName, DATA);
    }
    
    @FXML
    private void retrievePortNumber() throws IOException {
        printMessage("Retrieving new port number...", INFO);
        PORT = AlertMessages.getPort();
        printMessage("Port number retrieved successfully.", INFO);
        printMessage("Port Number: " + PORT, DATA);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verbose = Values.getVerbose();
        messageLevel = Values.getMessageLevel();
        
        try {
            printMessage("Retrieving host name...", INFO);
            hostName = AlertMessages.getHostName();
            printMessage("Host name retrieved successfully.", INFO);
            printMessage("Host Name: " + hostName, DATA);
            
            printMessage("Retrieving port number...", INFO);
            PORT = AlertMessages.getPort();
            printMessage("Port number retrieved successfully.", INFO);
            printMessage("Port Number: " + PORT, DATA);
        } catch (IOException ex) {
            printMessage("line 126: IOException: " + ex + ": Terminating...",
                    ERROR);
        }
        
        Values.setHostName(hostName);
        Values.setPort(PORT);
    }
    
    public String getHostName() {
        return hostName;
    }
    
    public int getPort() {
        return PORT;
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
    
    protected final void printMessage(String message, int level) {
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
            System.out.println("gui.client.main.FXMLDocumentController: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("gui.client.main.FXMLDocumentController: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
    
}
