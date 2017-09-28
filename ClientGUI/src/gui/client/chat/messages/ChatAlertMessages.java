package gui.client.chat.messages;

import application.client.properties.ClientProperties;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author John Ford
 */
public class ChatAlertMessages {
    public static String getSenderName() throws IOException {
        String propSenderName = ClientProperties.getProperty("senderName");
        String stringResult;
        
        Optional<String> result;
        
        TextInputDialog dialog = new TextInputDialog(propSenderName);

        dialog.setTitle("Sender Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your name: ");
        dialog.getEditor().setPromptText("example: John");

        result = dialog.showAndWait();
        stringResult = result.get();
        
        if (result.isPresent() && !stringResult.equals(propSenderName))
            ClientProperties.setProperty("senderName", stringResult);
        
        return result.isPresent() ? stringResult : propSenderName;
    }
}
