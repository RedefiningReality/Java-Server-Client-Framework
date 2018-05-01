package gui.dialog.chat;

import java.io.IOException;

import com.sun.security.ntlm.Client;

import framework.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author John Ford
 */
public class ChatDialog {

	private static EventHandler<WindowEvent> onCloseRequest;

	private static String title;
	private static String connectionInfo;

	private static Text chatText;
	private static Text connectionInfoText;
	private static TextField chatTextField;

	private Logger logger;

	public ChatDialog() throws IOException {
		this.logger = new Logger("ChatDialog");
	}

	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

		Scene scene = new Scene(root);

		if (onCloseRequest != null)
			stage.setOnCloseRequest(onCloseRequest);

		stage.setScene(scene);
		stage.setTitle("Chat Client");

		if (connectionInfo != null)
			ChatDialog.connectionInfoText.setText(connectionInfo);
		ChatDialog.chatText.setText("\n\n");

		stage.show();
	}

	public void connect() throws IOException, InterruptedException {
		this.client = new Client(hostName, PORT, CHAT, verbose, messageLevel);

		ChatDialog.connectionInfo.setText(client.getConnectionInfo());
		ChatDialog.chatText.setText("\n\n");
		client.connect();
	}

	public void close() throws IOException, InterruptedException {
		this.client.close();
	}

	public static void setChatText(Text chatText) {
		ChatDialog.chatText = chatText;
	}

	public static void setConnectionInfo(Text connectionInfo) {
		ChatDialog.connectionInfo = connectionInfo;
	}

	public static void setChatTextField(TextField chatTextField) {
		ChatDialog.chatTextField = chatTextField;
	}

}
