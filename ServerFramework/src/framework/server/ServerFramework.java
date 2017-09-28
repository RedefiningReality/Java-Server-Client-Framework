/*
 * Copyright (c) 2014, John Ford. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of John Ford or the names of any
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package framework.server;

import static framework.logging.MessageLevels.HIGHEST_PRIORITY;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import framework.logging.Logger;
import framework.properties.PropertiesFile;
import framework.server.thread.ServerThread;

/**
 *
 * @author User
 */
public class ServerFramework {
    private ServerSocket serverSocket;
    private Socket socket;
    
    private int port;
    private int socketTimeout;
    
    private boolean propertiesFile;
    
    private final Logger logger;
    
    public ServerFramework() throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	propertiesFile = true;
    	initializePropertiesFile();
    	initializeProperties();
    }
    
    public ServerFramework(boolean createPropertiesFile) throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	this.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public ServerFramework(String propFileName) throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	propertiesFile = true;
	    initializePropertiesFile(propFileName);
	    initializeProperties();
    }
    
    public ServerFramework(String propFileName, String propFileLocation) throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName, propFileLocation);
    	initializeProperties();
    }
    
    public ServerFramework(boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	propertiesFile = true;
    	initializePropertiesFile();
    	initializeProperties();
    }
    
    public ServerFramework(boolean createPropertiesFile, boolean verbose, int messageLevel) 
    		throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	this.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public ServerFramework(String propFileName, boolean verbose, int messageLevel) 
    		throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName);
    	initializeProperties();
    }
    
    public ServerFramework(String propFileName, String propFileLocation, boolean verbose,
    		int messageLevel) throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName, propFileLocation);
    	initializeProperties();
    }
    
    public ServerFramework(int port, int socketTimeout) throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	propertiesFile = true;
    	initializePropertiesFile();
    	initializeProperties();
    }
    
    public ServerFramework(int port, int socketTimeout, boolean createPropertiesFile) 
    		throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	this.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public ServerFramework(String propFileName, int port, int socketTimeout) 
    		throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	this.propertiesFile = true;
    	if(propertiesFile) {
	    	initializePropertiesFile(propFileName);
	    	initializeProperties();
    	}
    }
    
    public ServerFramework(String propFileName, String propFileLocation, int port,
    		int socketTimeout) throws IOException {
    	logger = new Logger("ServerFramework");
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName, propFileLocation);
    	initializeProperties();
    }
    
    public ServerFramework(int port, int socketTimeout, boolean verbose, 
    		int messageLevel) throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	propertiesFile = true;
    	initializePropertiesFile();
    	initializeProperties();
    }
    
    public ServerFramework(int port, int socketTimeout, boolean createPropertiesFile,
    		boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	this.port = port;
    	
    	this.propertiesFile = createPropertiesFile;
    	if(propertiesFile) {
	    	initializePropertiesFile();
	    	initializeProperties();
    	}
    }
    
    public ServerFramework(String propFileName, int port, int socketTimeout, 
    		boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName);
    	initializeProperties();
    }
    
    public ServerFramework(String propFileName, String propFileLocation, int port,
    		int socketTimeout, boolean verbose, int messageLevel) throws IOException {
    	logger = new Logger("ServerFramework", verbose, messageLevel);
    	
    	this.port = port;
    	this.socketTimeout = socketTimeout;
    	
    	propertiesFile = true;
    	initializePropertiesFile(propFileName, propFileLocation);
    	initializeProperties();
    }
    
    public void connect() throws IOException {
        logger.printMessage("Listening for client on port " + 
                serverSocket.getLocalPort() + "...", HIGHEST_PRIORITY);
        socket = serverSocket.accept();
        
        logger.printMessage("Connected to client '" + socket.getRemoteSocketAddress() 
                + "'.", HIGHEST_PRIORITY);
        new ServerThread(socket, logger.getVerbose(), logger.getMessageLevel()).start();
    }
    
    public void close() throws IOException {
        logger.printMessage("Closing socket '" + socket.getRemoteSocketAddress() 
                + "'...", HIGHEST_PRIORITY);
        
        socket.close();
        logger.printMessage("Communication successful. Disconnected from all clients.", 
                HIGHEST_PRIORITY);
    }
    
    public void start() throws IOException {
    	if(port < 1024)
    		port = 12345;
    	
    	logger.printMessage("Creating server on port '" + port + "'...", HIGHEST_PRIORITY);
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(socketTimeout);
        logger.printMessage("Created server '" + serverSocket.getLocalSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
    }
    
    private void initializePropertiesFile() throws IOException {
    	if(!PropertiesFile.isInitialized())
    		PropertiesFile.initialize("config.properties", logger.getVerbose(), 
    				logger.getMessageLevel());
    }
    
    private void initializePropertiesFile(String propFileName) throws IOException {
    	if(!PropertiesFile.isInitialized())
	        PropertiesFile.initialize(propFileName, logger.getVerbose(), 
	        		logger.getMessageLevel());
    }
    
    private void initializePropertiesFile(String propFileName, String propFileLocation) 
    		throws IOException {
    	if(!PropertiesFile.isInitialized())
	        PropertiesFile.initialize(propFileName, propFileLocation, logger.getVerbose(), 
	                logger.getMessageLevel());
    }
    
    private void initializeProperties() throws IOException {
    	PropertiesFile.addProperty("port", String.valueOf(port));
    	PropertiesFile.addProperty("socketTimeout", String.valueOf(socketTimeout));
    }
    
    public void setPort(int port) throws IOException {
    	this.port = port;
    	
    	if(propertiesFile)
    		PropertiesFile.setProperty("port", String.valueOf(port));
    }
    
    public int getPort() {
    	return port;
    }
    
    public void setSocketTimeout(int socketTimeout) throws IOException {
    	this.socketTimeout = socketTimeout;
    	
    	serverSocket.setSoTimeout(socketTimeout);
    	
    	if(propertiesFile)
    		PropertiesFile.setProperty("socketTimeout", String.valueOf(socketTimeout));
    }
    
    public int getSocketTimeout() {
    	return socketTimeout;
    }
    
    public void createPropertiesFile() throws IOException {
    	propertiesFile = true;
    	
		initializePropertiesFile();
		initializeProperties();
    }
    
    public void removePropertiesFile() throws IOException {
    	propertiesFile = false;
    	
		PropertiesFile.removeFile();
    }
    
    public boolean usingPropertiesFile() {
    	return propertiesFile;
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
