package gui.client.messages;

import application.client.properties.ClientProperties;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author John Ford
 */
public class AlertMessages {
    
    public static String getHostName() throws IOException {
        String propHostName = ClientProperties.getProperty("hostName");
        String stringResult;
        
        Optional<String> result;
        
        TextInputDialog dialog = new TextInputDialog(propHostName);

        dialog.setTitle("Host Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the server's host name (IPv4 or "
                + "IPv6 Address): ");
        dialog.getEditor().setPromptText("example: 192.168.1.68");

        result = dialog.showAndWait();
        stringResult = result.get();
        
        if (result.isPresent() && !stringResult.equals(propHostName))
            ClientProperties.setProperty("hostName", stringResult);
        
        return result.isPresent() ? stringResult : propHostName;
    }
    
    public static int getPort() throws IOException {
        String propPORT = ClientProperties.getProperty("PORT");
        
        String stringResult;
        int intResult = 0;
        Optional<String> result;
        boolean isNumber = false;
        
        do {
            TextInputDialog dialog = new TextInputDialog(propPORT);
            
            dialog.setTitle("Port Number");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter the port number: ");
            dialog.getEditor().setPromptText("example: 28743");

            result = dialog.showAndWait();
            stringResult = result.get();
            try {
                intResult = Integer.parseInt(stringResult);
                isNumber = true;
            } catch (NumberFormatException ex) {
                if (stringResult.equals("")) {
                    intResult = 0;
                    isNumber = true;
                }
            } catch (NoSuchElementException ex) {
                if (!ex.getMessage().equals("No value present"))
                    throw new NoSuchElementException(ex.getMessage());
                else
                    isNumber = true;
            }
        } while (!isNumber);
        
        if (result.isPresent() && !stringResult.equals(propPORT))
            ClientProperties.setProperty("PORT", stringResult);
        
        return result.isPresent() ? intResult : Integer.valueOf(propPORT);
    }
}
