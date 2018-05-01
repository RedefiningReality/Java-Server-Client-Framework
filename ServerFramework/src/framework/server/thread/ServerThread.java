package framework.server.thread;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.HIGHEST_PRIORITY;
import static framework.logging.MessageLevels.INFO;
import static framework.logging.MessageLevels.WARNING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import framework.logging.Logger;
import framework.server.ServerFramework;
import framework.transfer.DataTransfers;
import framework.transfer.Transfer;

/**
 * Gets the protocol from the client and sends the data. This class deals with
 * each client individually after the class MultiServer connects to the client.
 *
 * @version 1.2
 * @see java.lang.Thread
 * @see ServerFramework.server.MultiServer#connect()
 * @since 1.0
 */
public class ServerThread extends Thread {

	/* Create the socket for connecting to a client */

	// Creates a new socket for connecting to clients
	private final Socket socket;

	/* Create the buffered reader for reading from the socket */

	// Creates a new input stream for reading binary data from the socket
	private final InputStream socketInputStream;
	// Creates a new input stream reader for converting binary data to a string
	private final InputStreamReader socketInputStreamReader;
	// Creates a new buffered reader for buffering the information from the
	// input stream reader
	private final BufferedReader in;

	/* Create a print writer for writing to the socket */

	// Creates a new output stream for writing binary data to the socket
	private final OutputStream socketOutputStream;
	// Creates a new print writer for converting strings into binary data which
	// can be written to the socket
	private final PrintWriter out;

	/* Create variables that control message logging */

	private Logger logger;

	/**
	 * Creates a new MultiServerThread for connecting to the client and logging
	 * socket information. This constructor assigns values to the input and output
	 * streams and sets the message level and verbose to default (verbose being true
	 * and the message level being ERROR).
	 * 
	 * @param socket
	 * @throws IOException
	 * @since 1.0
	 */
	public ServerThread(Socket socket) throws IOException {

		// Calls the parent constructor with the class name as an argument
		super("ServerThread");

		// Assigns the global socket to the socket parameter
		this.socket = socket;

		/*
		 * Assign the input and output stream to the socket input and output stream
		 */

		// Assigns the global input stream to the socket input stream
		this.socketInputStream = socket.getInputStream();
		// Assigns the global input stream reader to a new input stream reader
		// which reads from the socket input stream
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		// Assigns the global buffered reader to a new buffered reader which
		// buffers the socket input stream reader
		this.in = new BufferedReader(socketInputStreamReader);

		// Assigns the global output stream to the socket output stream
		this.socketOutputStream = socket.getOutputStream();
		// Assigns the global print writer to a new print writer with automatic
		// line flushing which converts strings into binary data and sends it to
		// the socket ouput stream
		this.out = new PrintWriter(socketOutputStream, true);

		/* Assign the default values for message logging */
		this.logger = new Logger("ServerThread");

	}

	/**
	 * Creates a new MultiServerThread for connecting to the client and logging
	 * socket information. This constructor assigns values to the input and output
	 * streams and sets the message level and verbose to default (verbose being true
	 * and the message level being ERROR).
	 * 
	 * @param socket
	 * @param verbose
	 * @param messageLevel
	 * @throws IOException
	 * @since 1.0
	 */
	public ServerThread(Socket socket, boolean verbose, int messageLevel) throws IOException {

		// Calls the parent constructor with the class name as an argument
		super("ServerThread");

		// Assigns the global socket to the socket parameter
		this.socket = socket;

		/*
		 * Assign the input and output stream to the socket input and output stream
		 */

		// Assigns the global input stream to the socket input stream
		this.socketInputStream = socket.getInputStream();
		// Assigns the global input stream reader to a new input stream reader
		// which reads from the socket input stream
		this.socketInputStreamReader = new InputStreamReader(socketInputStream);
		// Assigns the global buffered reader to a new buffered reader which
		// buffers the socket input stream reader
		this.in = new BufferedReader(socketInputStreamReader);

		// Assigns the global output stream to the socket output stream
		this.socketOutputStream = socket.getOutputStream();
		// Assigns the global print writer to a new print writer with automatic
		// line flushing which converts strings into binary data and sends it to
		// the socket ouput stream
		this.out = new PrintWriter(socketOutputStream, true);

		/* Assign the values for message logging */

		logger = new Logger("ServerThread", verbose, messageLevel);

	}

	/**
	 * Gets the protocol from the client and sends the data to the client. This
	 * method overrides the run() method in class Thread. It reads the protocol
	 * using a buffered read, transfers the data, and safely disconnects from the
	 * client.
	 * 
	 * @see java.lang.Thread#run()
	 * @see ServerFramework.server.MultiServer#connect()
	 * @since 1.0
	 */
	@Override
	public void run() {
		String protocol = getProtocol();

		while (!protocol.equals("DISCONNECT")) {

			logger.printMessage("Attempting to execute next transfer...", HIGHEST_PRIORITY);
			Transfer transfer = getTransfer(protocol);

			logger.printMessage("Starting transfer...", INFO);
			try {
				transfer.start(socketInputStream, socketOutputStream);
			} catch (Exception ex) {
				logger.printMessage("line 205: Transfer did not complete successfully: Error message: "
						+ ex.getMessage() + ": Terminating connection with server...", WARNING);
				break;
			}

			logger.printMessage("Transfer completed successfully.", HIGHEST_PRIORITY);

			protocol = getProtocol();
		}

		logger.printMessage("Received \"DISCONNECT\" message from client. Disconnecting...", INFO);
		disconnect();
	}

	private String getProtocol() {
		String protocol = "";

		logger.printMessage("Receiving protocol from client" + socket.getRemoteSocketAddress() + "...", INFO);
		try {
			protocol = in.readLine();
		} catch (IOException e) {
			logger.printMessage("line 198: Failed to receive protocol from client " + socket.getRemoteSocketAddress()
					+ ": Terminating connection...", WARNING);
			disconnect();
		}

		logger.printMessage("Protocol received from client successfully.", INFO);

		return protocol;
	}

	private Transfer getTransfer(String protocol) {
		logger.printMessage("Transfer Protocol: " + protocol, DATA);

		Transfer transfer = DataTransfers.getTransfer(protocol);
		if (transfer == null)
			logger.printMessage("line 198: Unrecognized transfer: The transfer is not a valid type of "
					+ "transfer acceptable by this server: Make sure that the desired transfer was added "
					+ "to the DataTransfers class within the framework.transfer package via the addTransfer() "
					+ "method.", WARNING);

		return transfer;
	}

	public void disconnect() {
		logger.printMessage("Disconnecting from client " + socket.getRemoteSocketAddress() + "...", INFO);
		try {
			close();
		} catch (IOException | InterruptedException ex) {
			logger.printMessage("line 243: Failed to disconnect from client " + socket.getRemoteSocketAddress() + ":"
					+ ex.getMessage() + ": Terminating connection...", WARNING);
			this.interrupt();
		}
	}

	public void close() throws IOException, InterruptedException {
		logger.printMessage("Terminating connection with client '" + socket.getRemoteSocketAddress() + "'...",
				HIGHEST_PRIORITY);

		sleep(1000);
		out.println("DISCONNECT");

		socketInputStream.close();
		in.close();

		socketOutputStream.flush();
		socketOutputStream.close();

		out.flush();
		out.close();

		socket.close();
		logger.printMessage("Communication successful. Disconnected from client.", HIGHEST_PRIORITY);
	}

	/**
	 * Reads a line of text from the socket. A line is considered to be terminated
	 * by any one of a line feed ('\n'), a carriage return ('\r'), or a carriage
	 * return followed immediately by a linefeed.
	 * 
	 * @return A String containing the contents of the line, not including any
	 *         line-termination characters, or null if the end of the stream has
	 *         been reached
	 * @throws IOException
	 *             - If an I/O error occurs
	 * @see java.io.BufferedReader#readLine()
	 * @since 1.0
	 */
	public String readLine() throws IOException {

		// Calls the readLine method on the socket buffered reader and returns
		// the value
		return in.readLine();
	}

	/**
	 * Prints a string. If the argument is null then the string "null" is printed.
	 * Otherwise, the string's characters are converted into bytes according to the
	 * platform's default character encoding, and these bytes are written in exactly
	 * the manner of the PrintWriter.write(int) method.
	 * 
	 * @param string
	 *            - The String to be printed
	 * @see java.io.PrintWriter#print(java.lang.String)
	 * @since 1.0
	 */
	public void print(String string) {

		// Prints out the string to the socket
		out.print(string);

	}

	/**
	 * Prints a String and then terminates the line. This method behaves as though
	 * it invokes PrintWriter.print(String) and then PrintWriter.println().
	 * 
	 * @param string
	 *            - The String value to be printed
	 * @see java.io.PrintWriter#println(java.lang.String)
	 * @since 1.0
	 */
	public void println(String string) {

		// Prints out the string value and a new line symbol to the socket
		out.println(string);

	}

}