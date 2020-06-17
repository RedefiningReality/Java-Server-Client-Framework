package framework.client;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.ERROR;
import static framework.logging.MessageLevels.HIGHEST_PRIORITY;
import static framework.logging.MessageLevels.INFO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import framework.logging.Logger;
import framework.properties.PropertiesFile;
import framework.transfer.DataTransfers;
import framework.transfer.Transfer;
import gui.dialog.userinput.UserInputDialog;

public class ClientFramework {
	private final InetAddress inetAddress;
	private final Socket socket;

	private final InputStream socketInputStream;
	private final InputStreamReader socketInputStreamReader;
	private final BufferedReader in;

	private final OutputStream socketOutputStream;
	private final PrintWriter out;

	private Queue<String> transfers;

	private final Logger logger;

	public ClientFramework() throws IOException {
		logger = new Logger("ClientFramework");

		String hostName = PropertiesFile.getProperty("hostName");

		String PORT_VALUE = PropertiesFile.getProperty("PORT");
		int PORT = Integer.parseInt(PORT_VALUE);

		this.transfers = new LinkedList<String>();

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(boolean verbose, int messageLevel) throws IOException {
		logger = new Logger("ClientFramework", verbose, messageLevel);

		String hostName = PropertiesFile.getProperty("hostName");

		String PORT_VALUE = PropertiesFile.getProperty("PORT");
		int PORT = Integer.parseInt(PORT_VALUE);

		this.transfers = new LinkedList<String>();

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(String hostName, int PORT) throws IOException {
		logger = new Logger("ClientFramework");

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.transfers = new LinkedList<String>();

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(String hostName, int PORT, boolean verbose, int messageLevel) throws IOException {
		logger = new Logger("ClientFramework", verbose, messageLevel);
		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.transfers = new LinkedList<String>();

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(Queue<String> transfers) throws IOException {
		logger = new Logger("ClientFramework");

		String hostName = PropertiesFile.getProperty("hostName");

		String PORT_VALUE = PropertiesFile.getProperty("PORT");
		int PORT = Integer.parseInt(PORT_VALUE);

		this.transfers = transfers;

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(Queue<String> transfers, boolean verbose, int messageLevel) throws IOException {
		logger = new Logger("ClientFramework", verbose, messageLevel);

		String hostName = PropertiesFile.getProperty("hostName");

		String PORT_VALUE = PropertiesFile.getProperty("PORT");
		int PORT = Integer.parseInt(PORT_VALUE);

		this.transfers = transfers;

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(String hostName, int PORT, Queue<String> transfers) throws IOException {
		logger = new Logger("ClientFramework");

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.transfers = transfers;

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public ClientFramework(String hostName, int PORT, Queue<String> transfers, boolean verbose, int messageLevel)
			throws IOException {
		logger = new Logger("ClientFramework", verbose, messageLevel);

		logger.printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
		this.inetAddress = InetAddress.getByName(hostName);
		this.socket = new Socket(inetAddress, PORT);
		logger.printMessage("Connected to server '" + socket.getRemoteSocketAddress() + "'.", HIGHEST_PRIORITY);

		this.transfers = transfers;

		this.socketInputStream = socket.getInputStream();
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		this.in = new BufferedReader(socketInputStreamReader);

		this.socketOutputStream = socket.getOutputStream();
		this.out = new PrintWriter(socketOutputStream, true);
	}

	public void printConnectionInfo() throws IOException {
		System.out.println(getConnectionInfo());
	}

	public String getConnectionInfo() throws IOException {
		return "====================== SERVER CONNECTION INFO ======================\n" + "Host Name: "
				+ inetAddress.getHostName() + "\n" + "Host Address: " + inetAddress.getHostAddress() + "\n"
				+ "Canonical Host Name: " + inetAddress.getCanonicalHostName() + "\n" + "IP Address: "
				+ Arrays.toString(inetAddress.getAddress()) + "\n" + "Is Wildcard Address: "
				+ inetAddress.isAnyLocalAddress() + "\n" + "Is Link-Local Unicast Address: "
				+ inetAddress.isLinkLocalAddress() + "\n" + "Is Loopback Address: " + inetAddress.isLoopbackAddress()
				+ "\n" + "Is Multicast Address of Global Scope: " + inetAddress.isMCGlobal() + "\n"
				+ "Is Multicast Address of Link-Local Scope: " + inetAddress.isMCLinkLocal() + "\n"
				+ "Is Multicast Address of Node-Local Scope: " + inetAddress.isMCNodeLocal() + "\n"
				+ "Is Multicast Address of Organization-Local Scope: " + inetAddress.isMCOrgLocal() + "\n"
				+ "Is Multicast Address of Site-Local Scope: " + inetAddress.isMCSiteLocal() + "\n"
				+ "Is Multicast Address: " + inetAddress.isMulticastAddress() + "\n" + "Is Site-Local Address: "
				+ inetAddress.isSiteLocalAddress() + "\n" + "Is Reachable in 1 Second: " + inetAddress.isReachable(1000)
				+ "\n" + "Is Reachable in 2 Seconds: " + inetAddress.isReachable(2000) + "\n"
				+ "Is Reachable in 4 Seconds: " + inetAddress.isReachable(4000) + "\n" + "Is Reachable in 10 Seconds: "
				+ inetAddress.isReachable(10000) + "\n"
				+ "====================================================================";
	}

	public void executeTransfers() throws FileNotFoundException, IOException, InterruptedException {
		while (!transfers.isEmpty()) {
			logger.printMessage("Attempting to execute next transfer...", HIGHEST_PRIORITY);

			Transfer transfer = getTransfer(transfers.peek());
			setDialogValues(transfer.getDialogs());

			logger.printMessage("Sending protocol to server...", INFO);
			out.println(transfers.remove());
			logger.printMessage("Protocol sent to server successfully.", INFO);

			logger.printMessage("Starting transfer...", INFO);
			try {
				transfer.start(socketInputStream, socketOutputStream);
			} catch (Exception ex) {
				logger.printMessage("line 199: Transfer did not complete successfully: Error message: "
						+ ex.getMessage() + ": Terminating connection with server...", ERROR);
			}

			logger.printMessage("Transfer completed successfully.", HIGHEST_PRIORITY);
		}

		logger.printMessage("All transfers completed successfully.", HIGHEST_PRIORITY);
	}

	private Transfer getTransfer(String protocol) {
		logger.printMessage("Transfer Protocol: " + protocol, DATA);

		Transfer transfer = DataTransfers.getTransfer(protocol);
		if (transfer == null)
			logger.printMessage("line 178: Unrecognized transfer: The transfer is not a valid type of "
					+ "transfer acceptable by this client: Make sure that the desired transfer was added "
					+ "to the DataTransfers class within the framework.transfer package via the addTransfer() "
					+ "method.", ERROR);

		return transfer;
	}

	private void setDialogValues(List<UserInputDialog> dialogs) throws IOException {
		logger.printMessage("Obtaining required info from properties file and user...", INFO);

		for (UserInputDialog dialog : dialogs)
			dialog.start();

		logger.printMessage("Finished obtaining required info from properties file and user.", INFO);
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setTransfers(Queue<String> transfers) throws IOException {
		this.transfers = transfers;
	}

	public void addTransfer(String protocol) {
		this.transfers.add(protocol);
	}

	public Queue<String> getTransfers() {
		return transfers;
	}

	public String removeTransfer() {
		return transfers.remove();
	}

	public String getNextTransfer() {
		return transfers.peek();
	}

	public int getPort() {
		return socket.getLocalPort();
	}

	public void disconnect() {
		out.println("DISCONNECT");
		logger.printMessage("Disconnecting from server main thread...", INFO);

		try {
			close();
		} catch (IOException | InterruptedException ex) {
			logger.printMessage("line 358: Failed to disconnect from server:" + ex.getMessage() + ": Terminating...",
					ERROR);
		}
	}

	public void close() throws IOException, InterruptedException {
		logger.printMessage("Disconnecting from server '" + socket.getRemoteSocketAddress() + "'...", HIGHEST_PRIORITY);

		String protocol = in.readLine();
		logger.printMessage("Protocol: ", DATA);
		if (!protocol.equals("DISCONNECT")) {
			logger.printMessage("line 368: Failed to disconnect from server: "
					+ "Server sent unrecognized protocol: Terminating...", ERROR);
		}

		socketInputStream.close();
		in.close();

		socketOutputStream.flush();
		socketOutputStream.close();

		out.flush();
		out.close();

		socket.close();
		logger.printMessage("Communication successful. Disconnected from server.", HIGHEST_PRIORITY);
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
