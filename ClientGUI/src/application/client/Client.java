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
package application.client;

import application.client.transfer.*;
import application.client.properties.ClientProperties;
import static application.messaging.Messages.*;
import static application.protocol.DataProtocol.*;
import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class Client {
    private final InetAddress inetAddress;
    private final Socket socket;
    
    private final InputStream socketInputStream;
    private final InputStreamReader socketInputStreamReader;
    private final BufferedReader in;
    
    private final OutputStream socketOutputStream;
    private final PrintWriter out;
    
    private String protocol;
    private String filePath;
    
    private boolean verbose;
    private int messageLevel;
    
    public Client() throws IOException {
        String hostName = ClientProperties.getProperty("hostName");
        
        String PORT_VALUE = ClientProperties.getProperty("PORT");
        int PORT = Integer.parseInt(PORT_VALUE);
        
        this.protocol = ClientProperties.getProperty("protocol");
        if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS) 
                || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
            this.filePath = ClientProperties.getProperty("filePath");
        }
        
        printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
        this.inetAddress = InetAddress.getByName(hostName);
        this.socket = new Socket(inetAddress, PORT);
        printMessage("Connected to server '" + socket.getRemoteSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
        
        this.socketInputStream = socket.getInputStream();
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.socketOutputStream = socket.getOutputStream();
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public Client(boolean verbose, int messageLevel) throws IOException {
        String hostName = ClientProperties.getProperty("hostName");
        
        String PORT_VALUE = ClientProperties.getProperty("PORT");
        int PORT = Integer.parseInt(PORT_VALUE);
        
        this.protocol = ClientProperties.getProperty("protocol");
        if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS) 
                || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
            this.filePath = ClientProperties.getProperty("filePath");
        }
        
        printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
        this.inetAddress = InetAddress.getByName(hostName);
        this.socket = new Socket(inetAddress, PORT);
        printMessage("Connected to server '" + socket.getRemoteSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
        
        this.socketInputStream = socket.getInputStream();
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.socketOutputStream = socket.getOutputStream();
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public Client(String hostName, int PORT, String protocol) 
            throws IOException {
        
        printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
        this.inetAddress = InetAddress.getByName(hostName);
        this.socket = new Socket(inetAddress, PORT);
        printMessage("Connected to server '" + socket.getRemoteSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
        
        this.protocol = protocol;
        if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS) 
                || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
            this.filePath = ClientProperties.getProperty("filePath");
        }
        
        this.socketInputStream = socket.getInputStream();
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.socketOutputStream = socket.getOutputStream();
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public Client(String hostName, int PORT, String protocol, String filePath) 
            throws IOException {
        printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
        this.inetAddress = InetAddress.getByName(hostName);
        this.socket = new Socket(inetAddress, PORT);
        printMessage("Connected to server '" + socket.getRemoteSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
        
        this.protocol = protocol;
        if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS)
                || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
            this.filePath = filePath;
        }
        
        this.socketInputStream = socket.getInputStream();
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.socketOutputStream = socket.getOutputStream();
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public Client(String hostName, int PORT, String protocol, boolean verbose, 
            int messageLevel) throws IOException {
        printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
        this.inetAddress = InetAddress.getByName(hostName);
        this.socket = new Socket(inetAddress, PORT);
        printMessage("Connected to server '" + socket.getRemoteSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
        
        this.protocol = protocol;
        if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS)
                || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
            this.filePath = "";
        }
        
        this.socketInputStream = socket.getInputStream();
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.socketOutputStream = socket.getOutputStream();
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public Client(String hostName, int PORT, String protocol, String filePath, 
            boolean verbose, int messageLevel) throws IOException {
        printMessage("Connecting to server on port '" + PORT + "'...", HIGHEST_PRIORITY);
        this.inetAddress = InetAddress.getByName(hostName);
        this.socket = new Socket(inetAddress, PORT);
        printMessage("Connected to server '" + socket.getRemoteSocketAddress() + 
                "'.", HIGHEST_PRIORITY);
        
        this.protocol = protocol;
        if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS)
                || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
            this.filePath = filePath;
        }
        
        this.socketInputStream = socket.getInputStream();
        this.socketInputStreamReader = new InputStreamReader(socketInputStream);
        this.in = new BufferedReader(socketInputStreamReader);
        
        this.socketOutputStream = socket.getOutputStream();
        this.out = new PrintWriter(socketOutputStream, true);
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public void printConnectionInfo() throws IOException {
        System.out.println();
        System.out.println("====================== SERVER CONNECTION INFO ======================");
        System.out.println("Host Name: " + inetAddress.getHostName());
        System.out.println("Host Address: " + inetAddress.getHostAddress());
        System.out.println("Canonical Host Name: " + inetAddress.getCanonicalHostName());
        System.out.println("IP Address: " + Arrays.toString(inetAddress.getAddress()));
        System.out.println("Is Wildcard Address: " + inetAddress.isAnyLocalAddress());
        System.out.println("Is Link-Local Unicast Address: " + inetAddress.isLinkLocalAddress());
        System.out.println("Is Loopback Address: " + inetAddress.isLoopbackAddress());
        System.out.println("Is Multicast Address of Global Scope: " + inetAddress.isMCGlobal());
        System.out.println("Is Multicast Address of Link-Local Scope: " + inetAddress.isMCLinkLocal());
        System.out.println("Is Multicast Address of Node-Local Scope: " + inetAddress.isMCNodeLocal());
        System.out.println("Is Multicast Address of Organization-Local Scope: " + inetAddress.isMCOrgLocal());
        System.out.println("Is Multicast Address of Site-Local Scope: " + inetAddress.isMCSiteLocal());
        System.out.println("Is Multicast Address: " + inetAddress.isMulticastAddress());
        System.out.println("Is Site-Local Address: " + inetAddress.isSiteLocalAddress());
        System.out.println("Is Reachable in 1 Second: " + inetAddress.isReachable(1000));
        System.out.println("Is Reachable in 2 Seconds: " + inetAddress.isReachable(2000));
        System.out.println("Is Reachable in 4 Seconds: " + inetAddress.isReachable(4000));
        System.out.println("Is Reachable in 10 Seconds: " + inetAddress.isReachable(10000));
        System.out.println("====================================================================");
        System.out.println();
    }
    
    public String getConnectionInfo() throws IOException {
        return "====================== SERVER CONNECTION INFO ======================\n"
            + "Host Name: " + inetAddress.getHostName() + "\n"
            + "Host Address: " + inetAddress.getHostAddress() + "\n"
            + "Canonical Host Name: " + inetAddress.getCanonicalHostName() + "\n"
            + "IP Address: " + Arrays.toString(inetAddress.getAddress()) + "\n"
            + "Is Wildcard Address: " + inetAddress.isAnyLocalAddress() + "\n"
            + "Is Link-Local Unicast Address: " + inetAddress.isLinkLocalAddress() + "\n"
            + "Is Loopback Address: " + inetAddress.isLoopbackAddress() + "\n"
            + "Is Multicast Address of Global Scope: " + inetAddress.isMCGlobal() + "\n"
            + "Is Multicast Address of Link-Local Scope: " + inetAddress.isMCLinkLocal() + "\n"
            + "Is Multicast Address of Node-Local Scope: " + inetAddress.isMCNodeLocal() + "\n"
            + "Is Multicast Address of Organization-Local Scope: " + inetAddress.isMCOrgLocal() + "\n"
            + "Is Multicast Address of Site-Local Scope: " + inetAddress.isMCSiteLocal() + "\n"
            + "Is Multicast Address: " + inetAddress.isMulticastAddress() + "\n"
            + "Is Site-Local Address: " + inetAddress.isSiteLocalAddress() + "\n"
            + "Is Reachable in 1 Second: " + inetAddress.isReachable(1000) + "\n"
            + "Is Reachable in 2 Seconds: " + inetAddress.isReachable(2000) + "\n"
            + "Is Reachable in 4 Seconds: " + inetAddress.isReachable(4000) + "\n"
            + "Is Reachable in 10 Seconds: " + inetAddress.isReachable(10000) + "\n"
            + "====================================================================";
    }
    
    public void connect() throws FileNotFoundException, IOException, InterruptedException {
        out.println(protocol);
        
        switch (protocol) {
            case FILE_SC:
                printMessage("Requesting file from server '" + 
                        socket.getRemoteSocketAddress() + "'...", 
                        HIGHEST_PRIORITY);
                FileTransferSC fileTransferSC = new FileTransferSC(socketInputStream, 
                        filePath, verbose, messageLevel);
                fileTransferSC.retrieveFileName();
                fileTransferSC.retrieveFileSize();
                fileTransferSC.retrieveFileData();
                fileTransferSC.writeToFile();
                
                protocol = in.readLine();
                if (protocol.equals(DISCONNECT)) {
                    printMessage("Disconnecting from server main thread...", INFO);
                    break;
                } else
                    printMessage("Did not disconnect from server successfully.",
                            ERROR);
            case FILE_CS:
                printMessage("Requesting to send a file to server '" + 
                        socket.getRemoteSocketAddress() + "'...", 
                        HIGHEST_PRIORITY);
                
                FileTransferCS fileTransferCS;
                fileTransferCS = 
                        new FileTransferCS(true, socketOutputStream, verbose, 
                                messageLevel);
                
                fileTransferCS.start();
                
                break;
            case FILES_SC:
                printMessage("Requesting multiple files from server '" + 
                        socket.getRemoteSocketAddress() + "'...", 
                        HIGHEST_PRIORITY);
                MultipleFileTransferSC multipleFileTransferSC = 
                        new MultipleFileTransferSC(socketInputStream, filePath, 
                                verbose, messageLevel);
                multipleFileTransferSC.retrieveNumberOfFiles();
                multipleFileTransferSC.retrieveFileNames();
                multipleFileTransferSC.retrieveFileSizes();
                multipleFileTransferSC.retrieveFileData();
                multipleFileTransferSC.writeToFiles();
                
                protocol = in.readLine();
                if (protocol.equals(DISCONNECT)) {
                    printMessage("Disconnecting from server main thread...", INFO);
                    break;
                } else
                    printMessage("Did not disconnect from server successfully.",
                            ERROR);
            case FILES_CS:
                printMessage("Requesting to transfer file to server '" + 
                        socket.getRemoteSocketAddress() + "'...", 
                        HIGHEST_PRIORITY);
                MultipleFileTransferCS multipleFileTransferCS = 
                        new MultipleFileTransferCS(socketOutputStream, 
                                verbose, messageLevel);
                multipleFileTransferCS.sendNumberOfFiles();
                multipleFileTransferCS.sendFileNames();
                multipleFileTransferCS.sendFileSizes();
                multipleFileTransferCS.transferFiles();
                printMessage("Disconnecting from server '" 
                        + socket.getRemoteSocketAddress() + "'...", INFO);
                
                protocol = in.readLine();
                if (protocol.equals(DISCONNECT)) {
                    printMessage("Disconnecting from server main thread...", INFO);
                    break;
                } else
                    printMessage("Did not disconnect from server successfully.",
                            ERROR);
            case CHAT:
                printMessage("Requesting message from server '" + 
                        socket.getRemoteSocketAddress() + "'...", 
                        HIGHEST_PRIORITY);
                
                MessageSender messageSender;
                MessageReceiver messageReceiver;
                
                messageSender = new MessageSender(socketOutputStream, verbose, 
                        messageLevel);
                
                messageReceiver  = new MessageReceiver(socketInputStream, 
                        verbose, messageLevel);
                
                messageSender.start();
                messageReceiver.start();
                
                break;
            default:
                printMessage("line 372: The value of protocol is '" + protocol + 
                        "': Unrecognized Protocol: Terminating...", ERROR);
        }
    }
    
    public InetAddress getInetAddress() {
        return inetAddress;
    }
    
    public void setProtocol(String protocol) throws IOException {
        this.protocol = protocol;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public int getPort() {
        return socket.getLocalPort();
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void close() throws IOException, InterruptedException {
        printMessage("Disconnecting from server '" + 
                socket.getRemoteSocketAddress() + "'...", HIGHEST_PRIORITY);
        sleep(1000);
        out.println(DISCONNECT);
        
        socketInputStream.close();
        in.close();
        
        socketOutputStream.flush();
        socketOutputStream.close();
        
        out.flush();
        out.close();
        printMessage("Communication successful. Disconnected from server.", 
                HIGHEST_PRIORITY);
        
        socket.close();
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
    
    protected final void printMessage(String message, int level) {
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
            System.out.println("Client: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("Client: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}