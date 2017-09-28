package gui.client.filetransfer.clientserver;

import application.client.Client;
import application.client.values.Values;
import static application.messaging.Messages.DATA;
import static application.messaging.Messages.ERROR;
import static application.messaging.Messages.HIGHEST_PRIORITY;
import static application.messaging.Messages.INFO;
import static application.messaging.Messages.WARNING;
import static application.protocol.DataProtocol.FILE_CS;
import gui.client.filetransfer.clientserver.messages.FileTransferCSAlertMessages;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author John Ford
 */
public class FXMLDocumentController implements Initializable {
    
    private static boolean verbose;
    private static int messageLevel;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Button finishButton;
    
    @FXML
    private Text connectionInfo;
    
    @FXML
    private void cancel() {
        printMessage("Cancel button clicked: Terminating...", INFO);
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLDocumentController.verbose = Values.getVerbose();
        FXMLDocumentController.messageLevel = Values.getMessageLevel();
        
        gui.client.filetransfer.clientserver.FileTransferCS
                .setConnectionInfo(connectionInfo);
        
        gui.client.filetransfer.clientserver.FileTransferCS
                .setProgressBar(progressBar);
        application.client.transfer.FileTransferCS.setProgressBar(progressBar);
        
        gui.client.filetransfer.clientserver.FileTransferCS
                .setFinishButton(finishButton);
        application.client.transfer.FileTransferCS
                .setFinishButton(finishButton);
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
            System.out.println("gui.client.filetransfer.clientserver."
                    + "FXMLDocumentController: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("gui.client.filetransfer.clientserver."
                    + "FXMLDocumentController: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
    
}
