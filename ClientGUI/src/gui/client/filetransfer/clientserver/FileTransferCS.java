package gui.client.filetransfer.clientserver;

import application.client.Client;
import application.client.values.Values;
import static application.messaging.Messages.DATA;
import static application.messaging.Messages.ERROR;
import static application.messaging.Messages.HIGHEST_PRIORITY;
import static application.messaging.Messages.INFO;
import static application.messaging.Messages.WARNING;
import static application.protocol.DataProtocol.FILE_CS;
import gui.client.chat.Chat;
import gui.client.filetransfer.clientserver.messages.FileTransferCSAlertMessages;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author John Ford
 */
public class FileTransferCS {
    
    private static Client client;
    
    private final String hostName;
    private final int PORT;
    
    private final String transferFilePath;
    
    private static ProgressBar progressBar;
    private static Text connectionInfo;
    private static Button finishButton;
    
    private static boolean verbose;
    private static int messageLevel;
    
    public FileTransferCS(String hostName, int PORT) throws IOException,
            InterruptedException {
        FileTransferCS.verbose = Values.getVerbose();
        FileTransferCS.messageLevel = Values.getMessageLevel();
        
        this.hostName = hostName;
        this.PORT = PORT;
        
        printMessage("Retrieving new transfer file path...", INFO);
        transferFilePath = FileTransferCSAlertMessages.getTransferFilePath();
        printMessage("Transfer file path retrieved successfully.", INFO);
        printMessage("Transfer File Path: " + transferFilePath, DATA);
        
        Values.setTransferFilePath(transferFilePath);
    }
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setOnCloseRequest(event -> {
            close();
        });
        
        stage.setScene(scene);
        stage.setTitle("File Transfer");
        stage.show();
    }
    
    public void connect() throws IOException, InterruptedException {
        client = new Client(hostName, PORT, FILE_CS, verbose, messageLevel);
        
        FileTransferCS.connectionInfo.setText(client.getConnectionInfo());
        client.connect();
    }
    
    public static void close() {
        printMessage("Recieved end connection request: Exiting file transfer...", 
                INFO);

        try {
            client.close();
        } catch (IOException | InterruptedException ex) {
            printMessage("line 88: Exception: " + ex + ": Terminating...", 
                    ERROR);
        }
    }
    
    public static void setProgressBar(ProgressBar progressBar) {
        FileTransferCS.progressBar = progressBar;
    }
    
    public static void setConnectionInfo(Text connectionInfo) {
        FileTransferCS.connectionInfo = connectionInfo;
    }
    
    public static void setFinishButton(Button finishButton) {
        FileTransferCS.finishButton = finishButton;
        
        finishButton.setOnAction(event -> {
            close();
            
            Stage stage = (Stage) finishButton.getScene().getWindow();
            stage.close();
        });
    }
    
    public static void testInfoAndProgressBar() {
        connectionInfo.setText("testing");
        progressBar.setProgress(0.5d);
        finishButton.setText("test");
        finishButton.setDisable(false);
    }
    
    public static void setVerbose(boolean verbose) {
        FileTransferCS.verbose = verbose;
    }
    
    public static boolean getVerbose() {
        return verbose;
    }
    
    public static void setMessageLevel(int messageLevel) {
        FileTransferCS.messageLevel = messageLevel;
    }
    
    public static int getMessageLevel() {
        return messageLevel;
    }
    
    protected final static void printMessage(String message, int level) {
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
            System.out.println("FileTransferCS: [" + stringLevel + "] "
                    + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("FileTransferCS: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}
