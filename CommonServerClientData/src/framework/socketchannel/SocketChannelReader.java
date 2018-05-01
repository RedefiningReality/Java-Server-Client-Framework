package framework.socketchannel;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.ERROR;
import static framework.logging.MessageLevels.INFO;
import static framework.logging.MessageLevels.WARNING;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import framework.logging.Logger;

public class SocketChannelReader {

	private final SocketChannel socketChannel;
	private SelectionKey selectionKey;

	private ByteBuffer buffer;

	private boolean logReadInformation;

	private final Logger logger;

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();

		this.logReadInformation = true;

		this.logger = new Logger("SocketChannelReader");
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, int capacity) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();

		this.logReadInformation = true;

		this.logger = new Logger("SocketChannelReader");
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, boolean verbose,
			int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();

		this.logReadInformation = true;

		this.logger = new Logger("SocketChannelReader", verbose, messageLevel);
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, int capacity, boolean verbose,
			int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();

		this.logReadInformation = true;

		this.logger = new Logger("SocketChannelReader", verbose, messageLevel);
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, boolean logReadInformation) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();

		this.logReadInformation = logReadInformation;

		this.logger = new Logger("SocketChannelReader");
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, int capacity,
			boolean logReadInformation) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();

		this.logReadInformation = logReadInformation;

		this.logger = new Logger("SocketChannelReader");
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, boolean logReadInformation,
			boolean verbose, int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();

		this.logReadInformation = logReadInformation;

		this.logger = new Logger("SocketChannelReader", verbose, messageLevel);
	}

	public SocketChannelReader(SocketChannel socketChannel, SelectionKey selectionKey, int capacity,
			boolean logReadInformation, boolean verbose, int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;

		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();

		this.logReadInformation = logReadInformation;

		this.logger = new Logger("SocketChannelReader", verbose, messageLevel);
	}

	public String read() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading string from SocketChannel...", INFO);

		readToBuffer();
		String result = new String(buffer.array()).trim();

		if (logReadInformation) {
			logger.printMessage("String read successfully.", INFO);
			logger.printMessage("String Value: " + result, DATA);
		}

		return result;
	}

	public int readInt() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading integer from SocketChannel...", INFO);

		readToBuffer();
		int result = buffer.getInt();

		if (logReadInformation) {
			logger.printMessage("Integer read successfully.", INFO);
			logger.printMessage("Integer Value: " + result, DATA);
		}

		return result;
	}

	public short readShort() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading short from SocketChannel...", INFO);

		readToBuffer();
		short result = buffer.getShort();

		if (logReadInformation) {
			logger.printMessage("Short read successfully.", INFO);
			logger.printMessage("Short Value: " + result, DATA);
		}

		return result;
	}

	public char readChar() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading character from SocketChannel...", INFO);

		readToBuffer();
		char result = buffer.getChar();

		if (logReadInformation) {
			logger.printMessage("Character read successfully.", INFO);
			logger.printMessage("Character Value: " + result, DATA);
		}

		return result;
	}

	public long readLong() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading long from SocketChannel...", INFO);

		readToBuffer();
		long result = buffer.getLong();

		if (logReadInformation) {
			logger.printMessage("Long read successfully.", INFO);
			logger.printMessage("Long Value: " + result, DATA);
		}

		return result;
	}

	public float readFloat() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading float from SocketChannel...", INFO);

		readToBuffer();
		float result = buffer.getFloat();

		if (logReadInformation) {
			logger.printMessage("Float read successfully.", INFO);
			logger.printMessage("Float Value: " + result, DATA);
		}

		return result;
	}

	public byte[] readBytes() throws IOException {
		if (logReadInformation)
			logger.printMessage("Reading byte array from SocketChannel...", INFO);

		readToBuffer();
		byte[] result = buffer.array();

		if (logReadInformation) {
			logger.printMessage("Byte array read successfully.", INFO);
			logger.printMessage("Byte Array: " + result, DATA);
		}

		return result;
	}

	private void readToBuffer() throws IOException {
		if (!selectionKey.isWritable())
			selectionKey.interestOps(SelectionKey.OP_READ);
		buffer.clear();

		int length = 0;
		try {
			length = socketChannel.read(buffer);
		} catch (IOException ex) {
			logger.printMessage("line 239: Failed to read message from server: IOException: " + ex.getMessage()
					+ ": Terminating...", ERROR);

			selectionKey.cancel();
			socketChannel.close();

			return;
		}

		if (length == -1) {
			logger.printMessage("Nothing was read from the server.", WARNING);
			return;
		}

		buffer.flip();
	}

	public void setBufferCapacity(int capacity) {
		this.buffer = ByteBuffer.allocate(capacity);
	}

	public void resetBufferCapacity() {
		this.buffer = ByteBuffer.allocate(1000);
	}

	public int getBufferCapacity() {
		return this.buffer.capacity();
	}

	public void setSelectionKey(SelectionKey selectionKey) {
		this.selectionKey = selectionKey;
	}

	public SelectionKey getSelectionKey() {
		return selectionKey;
	}

	public void setLogReadInformation(boolean logReadInformation) {
		this.logReadInformation = logReadInformation;
	}

	public boolean getLogReadInformation() {
		return logReadInformation;
	}

}
