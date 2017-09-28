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
package application.server;

import static application.messaging.Messages.*;
import application.server.properties.ServerProperties;
import application.server.thread.MultiServerThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author User
 */
public class MultiServer {
    private final ServerSocket serverSocket;
    
    private Socket socket;
    
    private final ServerProperties serverProperties;
    
    private boolean verbose;
    private int messageLevel;
    
    public MultiServer() throws IOException {
        this.serverProperties = new ServerProperties();
        String PORT_VALUE = serverProperties.getProperty("PORT");
        int PORT = Integer.parseInt(PORT_VALUE);
        
        String socketTimeoutValue = serverProperties.getProperty("socketTimeout");
        int socketTimeout = Integer.parseInt(socketTimeoutValue);
        
        this.serverSocket = new ServerSocket(PORT);
        this.serverSocket.setSoTimeout(socketTimeout);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MultiServer(boolean verbose, int messageLevel) throws IOException {
        this.serverProperties = new ServerProperties();
        String PORT_VALUE = serverProperties.getProperty("PORT");
        int PORT = Integer.parseInt(PORT_VALUE);
        
        String socketTimeoutValue = serverProperties.getProperty("socketTimeout");
        int socketTimeout = Integer.parseInt(socketTimeoutValue);
        
        this.serverSocket = new ServerSocket(PORT);
        this.serverSocket.setSoTimeout(socketTimeout);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public MultiServer(int PORT, int socketTimeout) throws IOException {
        this.serverProperties = new ServerProperties();
        this.serverSocket = new ServerSocket(PORT);
        this.serverSocket.setSoTimeout(socketTimeout);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public MultiServer(int PORT, int socketTimeout, boolean verbose, 
            int messageLevel) throws IOException {
        this.serverProperties = new ServerProperties();
        this.serverSocket = new ServerSocket(PORT);
        this.serverSocket.setSoTimeout(socketTimeout);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public void connect() throws IOException {
        printMessage("Listening for client on port " + 
                serverSocket.getLocalPort() + "...", HIGHEST_PRIORITY);
        socket = serverSocket.accept();
        printMessage("Connected to client '" + socket.getRemoteSocketAddress() 
                + "'.", HIGHEST_PRIORITY);
        new MultiServerThread(socket, verbose, messageLevel).start();
    }
    
    public void close() throws IOException {
        printMessage("Closing socket '" + socket.getRemoteSocketAddress() 
                + "'...", HIGHEST_PRIORITY);
        socket.close();
        printMessage("Communication successful. Disconnected from all clients.", 
                HIGHEST_PRIORITY);
    }
    
    public int getPort() {
        return serverSocket.getLocalPort();
    }
    
    public void setSocketTimeout(int socketTimeout) throws SocketException {
        serverSocket.setSoTimeout(socketTimeout);
    }
    
    public int getSocketTimeout() throws IOException {
        return serverSocket.getSoTimeout();
    }
    
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public boolean getVerbose() {
        return verbose;
    }
    
    public void setMessageLevel(int messageLevel) {
        this.messageLevel = messageLevel;
    }
    
    public int getMessageLevel() {
        return messageLevel;
    }
    
    protected void printMessage(String message, int level) {
        String stringLevel;
        if (level == ERROR) {
            stringLevel = "ERROR";
        } else if (level == WARNING) {
            stringLevel = "WARNING";
        } else if (level == INFO) {
            stringLevel = "INFO";
        } else if (level == DATA) {
            stringLevel = "DATA";
        } else {
            stringLevel = "HIGHEST_PRIORITY";
        }
        
        if ((verbose && messageLevel >= level) && level != HIGHEST_PRIORITY) {
            System.out.println("MultiServer: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("MultiServer: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}
