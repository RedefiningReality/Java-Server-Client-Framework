package gui.client.filetransfer.clientserver.messages;

import application.client.properties.ClientProperties;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;

/**
 *
 * @author John Ford
 */
public class FileTransferCSAlertMessages {
    public static String getTransferFilePath() throws IOException {
        String propFilePath = ClientProperties.getProperty("transferFilePath");
        String stringResult = "";
        boolean resultIsValid;
        
        File selectedFile;
        
        do {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        
        fileChooser.setInitialDirectory(
                new File(propFilePath.substring(0, propFilePath.lastIndexOf("\\")))
        );
        
        selectedFile = fileChooser.showOpenDialog(null);
        try {
            stringResult = selectedFile.getAbsolutePath();
            resultIsValid = true;
        } catch (NullPointerException ex) {
            resultIsValid = false;
        }
        
        } while (!resultIsValid);
        
        if (!stringResult.equals(propFilePath))
            ClientProperties.setProperty("transferFilePath", stringResult);
        
        return stringResult;
    }
}
