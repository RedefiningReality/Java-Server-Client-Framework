package gui.dialog.chat;

import application.client.transfer.MessageReceiver;
import application.client.transfer.MessageSender;
import application.client.values.Values;
import static application.messaging.Messages.DATA;
import static application.messaging.Messages.ERROR;
import static application.messaging.Messages.HIGHEST_PRIORITY;
import static application.messaging.Messages.INFO;
import static application.messaging.Messages.WARNING;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author John Ford
 */
public class FXMLDocumentController implements Initializable {
    
    private static boolean verbose;
    private static int messageLevel;
    
    @FXML
    private Text chatText;
    
    @FXML
    private Text connectionInfo;
    
    @FXML
    private TextField chatTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLDocumentController.verbose = Values.getVerbose();
        FXMLDocumentController.messageLevel = Values.getMessageLevel();
        
        ChatDialog.setChatText(chatText);
        MessageSender.setChatText(chatText);
        MessageReceiver.setChatText(chatText);
        
        ChatDialog.setConnectionInfo(connectionInfo);
        MessageSender.setConnectionInfo(connectionInfo);
        MessageReceiver.setConnectionInfo(connectionInfo);
        
        ChatDialog.setChatTextField(chatTextField);
        MessageSender.setChatTextField(chatTextField);
        MessageReceiver.setChatTextField(chatTextField);
    }
    
    public static void setVerbose(boolean verbose) {
        FXMLDocumentController.verbose = verbose;
    }
    
    public static boolean getVerbose() {
        return verbose;
    }
    
    public static void setMessageLevel(int messageLevel) {
        FXMLDocumentController.messageLevel = messageLevel;
    }
    
    public static int getMessageLevel() {
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
            System.out.println("gui.client.chat.FXMLDocumentController: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("gui.client.chat.FXMLDocumentController: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
    
}
