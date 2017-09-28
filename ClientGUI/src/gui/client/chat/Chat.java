package gui.client.chat;

import application.client.Client;
import application.client.transfer.MessageReceiver;
import application.client.transfer.MessageSender;
import application.client.values.Values;
import static application.messaging.Messages.DATA;
import static application.messaging.Messages.ERROR;
import static application.messaging.Messages.HIGHEST_PRIORITY;
import static application.messaging.Messages.INFO;
import static application.messaging.Messages.WARNING;
import static application.protocol.DataProtocol.CHAT;
import gui.client.chat.messages.ChatAlertMessages;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author John Ford
 */
public class Chat {
    
    private Client client;
    
    private final String hostName;
    private final int PORT;
    
    private final String senderName;
    
    private static Text chatText;
    private static Text connectionInfo;
    private static TextField chatTextField;
    
    private boolean verbose;
    private int messageLevel;
    
    public Chat(String hostName, int PORT) throws IOException {
        this.verbose = Values.getVerbose();
        this.messageLevel = Values.getMessageLevel();
        
        this.hostName = hostName;
        this.PORT = PORT;
        
        printMessage("Retrieving sender name...", INFO);
        this.senderName = ChatAlertMessages.getSenderName();
        printMessage("Sender name retrieved successfully.", INFO);
        printMessage("Sender Name: " + senderName, DATA);
        
        Values.setSenderName(senderName);
    }
    
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setOnCloseRequest(event -> {
            printMessage("Recieved end connection request: Exiting chat...", 
                    INFO);
            
            try {
                close();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        stage.setScene(scene);
        stage.setTitle("Chat Client");
        stage.show();
    }
    
    public void connect() throws IOException, InterruptedException {
        this.client = new Client(hostName, PORT, CHAT, verbose, messageLevel);
        
        Chat.connectionInfo.setText(client.getConnectionInfo());
        Chat.chatText.setText("\n\n");
        client.connect();
    }
    
    public void close() throws IOException, InterruptedException {
        this.client.close();
    }
    
    public static void setChatText(Text chatText) {
        Chat.chatText = chatText;
    }
    
    public static void setConnectionInfo(Text connectionInfo) {
        Chat.connectionInfo = connectionInfo;
    }
    
    public static void setChatTextField(TextField chatTextField) {
        Chat.chatTextField = chatTextField;
    }
    
    public static void test() {
        chatText.setText("testing");
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
            System.out.println("Chat: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("Chat: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}
