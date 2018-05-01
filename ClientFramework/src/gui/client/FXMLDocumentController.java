package gui.client;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.ERROR;
import static framework.logging.MessageLevels.INFO;
import static framework.logging.MessageLevels.WARNING;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;

import framework.client.ClientFramework;
import framework.logging.Logger;
import framework.properties.PropertiesFile;
import framework.transfer.DataTransfers;
import gui.dialog.AlertDialog;
import gui.dialog.basic.ErrorDialog;
import gui.dialog.basic.WarningDialog;
import gui.dialog.basic.input.NumericInputDialog;
import gui.dialog.basic.input.TextInputDialog;
import gui.dialog.userinput.PropertiesDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FXMLDocumentController implements Initializable {

	private ClientFramework client;

	private String hostName;
	private int PORT;

	private boolean propertiesFile;

	@FXML
	private VBox transfersVBox;

	private Stack<String> transfers;
	@FXML
	private Text selectedTransfers;

	private Logger logger;

	private void addTransferToList(String protocol) {
		Button button = new Button(protocol);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addTransferToSelected(protocol);
			}
		});

		transfersVBox.getChildren().add(button);
	}

	private void addTransfersToList() {
		for (String protocol : DataTransfers.getProtocols())
			addTransferToList(protocol);
	}

	private void addTransferToSelected(String protocol) {
		transfers.push(protocol);

		if (selectedTransfers.getText().equals("None"))
			selectedTransfers.setText(protocol);
		else
			selectedTransfers.setText(selectedTransfers.getText() + "\n" + protocol);
	}

	@FXML
	private void removeTransferFromSelected() {
		if (!transfers.isEmpty())
			transfers.pop();

		String text;

		int index = selectedTransfers.getText().lastIndexOf('\n');
		if (index < 0)
			text = "None";
		else
			text = selectedTransfers.getText().substring(0, index);

		selectedTransfers.setText(text);
	}

	@FXML
	private void retrieveHostName() throws IOException {
		logger.printMessage("Retrieving host name...", INFO);

		AlertDialog temp;

		TextInputDialog textInput = new TextInputDialog();
		textInput.setTitle("Host Protocol");
		textInput.setContentText("Please enter the server's host name (IPv4 or IPv6 Address): ");

		if (propertiesFile) {
			textInput.setPromptText(PropertiesFile.getProperty("hostName"));
			textInput.setDefaultValue(PropertiesFile.getProperty("hostName"));
			temp = new PropertiesDialog(textInput, "hostName", ClientGUI.getVerbose(), ClientGUI.getMessageLevel());
		} else {
			textInput.setPromptText("example: " + hostName);
			textInput.setDefaultValue(hostName);
			temp = textInput;
		}

		hostName = temp.start();

		if (hostName == null) {
			logger.printMessage("User requested cancellation when entering host protocol.", INFO);
			logger.printMessage("Terminating client application...", INFO);
			System.exit(0);
		}

		logger.printMessage("Host protocol retrieved successfully.", INFO);
		logger.printMessage("Host Protocol: " + hostName, DATA);
	}

	@FXML
	private void retrievePortNumber() throws IOException {
		logger.printMessage("Retrieving port number...", INFO);

		AlertDialog temp;

		NumericInputDialog<Integer> intInput = new NumericInputDialog<Integer>(ClientGUI.getVerbose(),
				ClientGUI.getMessageLevel());
		intInput.setTitle("Port Number");
		intInput.setContentText("Please enter the desired port number: ");

		if (propertiesFile) {
			intInput.setPromptText("example: " + PropertiesFile.getProperty("PORT"));
			intInput.setDefaultValue(PropertiesFile.getProperty("PORT"));
			temp = new PropertiesDialog(intInput, "PORT", ClientGUI.getVerbose(), ClientGUI.getMessageLevel());
		} else {
			intInput.setPromptText("example: " + PORT);
			intInput.setDefaultValue(String.valueOf(PORT));
			temp = intInput;
		}

		String portString = temp.start();

		if (portString == null) {
			logger.printMessage("User requested cancellation when entering port number.", INFO);
			logger.printMessage("Terminating client application...", INFO);
			System.exit(0);
		}

		PORT = Integer.parseInt(portString);
		logger.printMessage("Port number retrieved successfully.", INFO);
		logger.printMessage("Port Number: " + PORT, DATA);
	}

	@FXML
	private void connect() throws FileNotFoundException, IOException, InterruptedException {
		logger.printMessage("Attempting to start client-server connection...", INFO);

		if (transfers.size() > 0) {
			try {
				client = new ClientFramework(hostName, PORT, ClientGUI.getVerbose(), ClientGUI.getMessageLevel());
			} catch (IOException ex) {
				logger.printMessage("line 122: IOException: Unable to connect to server: " + ex.getMessage(), WARNING);

				logger.printMessage("Displaying aforementioned warning...", INFO);
				new ErrorDialog("Connection Failure", "Unable to Connect to Server.",
						"Please ensure that " + "you have entered a valid Internet Protocol address and port number.")
								.start();
				logger.printMessage("Warning displayed successfully.", INFO);

				return;
			}

			logger.printMessage("Setting transfers to desired transfers...", INFO);
			logger.printMessage("Transfers: " + transfers.toString(), DATA);
			client.setTransfers(new LinkedList<String>(transfers));
			client.executeTransfers();
			client.disconnect();
		} else {
			logger.printMessage("User error. No transfers specified by user.", WARNING);

			logger.printMessage("Displaying aforementioned warning...", INFO);
			new WarningDialog("Transfer Selection", "No Transfers Selected",
					"Please ensure that you have "
							+ "selected the various transfers you would like to perform by clicking on the "
							+ "corresponding buttons.").start();
			logger.printMessage("Warning displayed successfully.", INFO);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		logger = new Logger("gui.client.FXMLDocumentController", ClientGUI.getVerbose(), ClientGUI.getMessageLevel());

		propertiesFile = ClientGUI.usingPropertiesFile();

		hostName = ClientGUI.getDefaultHostName();
		PORT = ClientGUI.getDefaultPort();

		try {
			retrieveHostName();
			retrievePortNumber();
		} catch (IOException ex) {
			logger.printMessage("line 186: IOException: Unable to retrieve host name and port "
					+ "number from properties file: " + ex.getMessage() + ": Terminating...", ERROR);
		}

		transfers = new Stack<String>();
		addTransfersToList();
	}

	public String getHostName() {
		return hostName;
	}

	public int getPort() {
		return PORT;
	}

	public void setVerbose(boolean verbose) {
		logger.setVerbose(verbose);
	}

	public boolean getVerbose() {
		return logger.getVerbose();
	}

	public void setMessageLevel(int messageLevel) {
		logger.setMessageLevel(messageLevel);
	}

	public int getMessageLevel() {
		return logger.getMessageLevel();
	}

}
