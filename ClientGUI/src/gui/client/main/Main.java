package gui.client.main;

import application.client.properties.ClientProperties;
import application.client.values.Values;
import static application.messaging.Messages.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Main extends Application {
    
    private static boolean verbose;
    private static int messageLevel;
    
    @Override
    public void start(Stage stage) throws Exception {
        initializeLogger(true, DATA);
        initializeClientProperties();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        launch(args);
        ClientProperties.saveToFile();
    }
    
    private static void initializeLogger(boolean verbose, int messageLevel) {
        Main.verbose = verbose;
        Values.setVerbose(verbose);
        Main.messageLevel = messageLevel;
        Values.setMessageLevel(messageLevel);
    }
    
    private static void initializeClientProperties() throws IOException {
        ClientProperties.initialize("config.properties", "", verbose, 
                messageLevel);
    }
    
    private static void initializeClientProperties(String propFileName, 
            String propFileLocation) throws IOException {
        ClientProperties.initialize(propFileName, propFileLocation, verbose, 
                messageLevel);
    }
}
