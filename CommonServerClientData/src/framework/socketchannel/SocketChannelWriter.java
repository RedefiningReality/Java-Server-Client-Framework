package framework.socketchannel;

import static framework.logging.MessageLevels.DATA;
import static framework.logging.MessageLevels.ERROR;
import static framework.logging.MessageLevels.INFO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import framework.logging.Logger;

public class SocketChannelWriter {
	
	private final SocketChannel socketChannel;
	private SelectionKey selectionKey;
	
	private ByteBuffer buffer;
	
	private boolean logWriteInformation;
	
	private final Logger logger;
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();
		
		this.logWriteInformation = true;
		
		this.logger = new Logger("SocketChannelWriter");
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, int capacity) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();
		
		this.logWriteInformation = true;
		
		this.logger = new Logger("SocketChannelWriter");
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, boolean verbose, 
			int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();
		
		this.logWriteInformation = true;
		
		this.logger = new Logger("SocketChannelWriter", verbose, messageLevel);
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, int capacity, 
			boolean verbose, int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();
		
		this.logWriteInformation = true;
		
		this.logger = new Logger("SocketChannelWriter", verbose, messageLevel);
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, boolean logWriteInformation) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();
		
		this.logWriteInformation = logWriteInformation;
		
		this.logger = new Logger("SocketChannelWriter");
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, int capacity, 
			boolean logWriteInformation) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();
		
		this.logWriteInformation = logWriteInformation;
		
		this.logger = new Logger("SocketChannelWriter");
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, boolean logWriteInformation,
			boolean verbose, int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(1000);
		this.buffer.clear();
		
		this.logWriteInformation = logWriteInformation;
		
		this.logger = new Logger("SocketChannelWriter", verbose, messageLevel);
	}
	
	public SocketChannelWriter(SocketChannel socketChannel, SelectionKey selectionKey, int capacity, 
			boolean logWriteInformation, boolean verbose, int messageLevel) {
		this.socketChannel = socketChannel;
		this.selectionKey = selectionKey;
		
		this.buffer = ByteBuffer.allocate(capacity);
		this.buffer.clear();
		
		this.logWriteInformation = logWriteInformation;
		
		this.logger = new Logger("SocketChannelWriter", verbose, messageLevel);
	}
	
	public void write(String value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing string to SocketChannel...", INFO);
			logger.printMessage("String Value: " + value, DATA);
		}
		
		buffer = ByteBuffer.wrap(value.getBytes());
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("String written successfully.", INFO);
	}
	
	public void writeInt(int value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing integer to SocketChannel...", INFO);
			logger.printMessage("Integer Value: " + value, DATA);
		}
		
		buffer.putInt(value);
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("Integer written successfully.", INFO);
	}
	
	public void writeShort(short value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing short to SocketChannel...", INFO);
			logger.printMessage("Short Value: " + value, DATA);
		}
		
		buffer.putShort(value);
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("Short written successfully.", INFO);
	}
	
	public void writeChar(char value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing character to SocketChannel...", INFO);
			logger.printMessage("Character Value: " + value, DATA);
		}

		buffer.putChar(value);
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("Character written successfully.", INFO);
	}
	
	public void writeLong(long value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing long to SocketChannel...", INFO);
			logger.printMessage("Long Value: " + value, DATA);
		}
		
		buffer.putLong(value);
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("Long written successfully.", INFO);
	}
	
	public void writeFloat(float value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing float to SocketChannel...", INFO);
			logger.printMessage("Float Value: " + value, DATA);
		}
		
		buffer.putFloat(value);
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("Float written successfully.", INFO);
	}
	
	public void readBytes(byte[] value) throws IOException {
		if(logWriteInformation) {
			logger.printMessage("Writing byte array to SocketChannel...", INFO);
			logger.printMessage("Byte Array: " + value, DATA);
		}
		
		buffer.put(value);
		writeFromBuffer();
		
		if(logWriteInformation)
			logger.printMessage("Byte array written successfully.", INFO);
	}
	
	private void writeFromBuffer() throws IOException {
		if(!selectionKey.isWritable())
			selectionKey.interestOps(SelectionKey.OP_WRITE);
		
		try {
			socketChannel.write(buffer);
        } catch(IOException ex) {
            logger.printMessage("line 239: Failed to write message to server: IOException: " + ex.getMessage()
            		+ ": Terminating...", ERROR);
            
            selectionKey.cancel();
            socketChannel.close();
            
            return;
        }
        
        buffer.flip();
        buffer.clear();
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
	
	public void setLogWriteInformation(boolean logWriteInformation) {
		this.logWriteInformation = logWriteInformation;
	}
	
	public boolean getLogWriteInformation() {
		return logWriteInformation;
	}

}
