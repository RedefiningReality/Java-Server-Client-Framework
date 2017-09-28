/*
 * Copyright (c) 2014-2015, John Ford. All rights reserved.
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
package application.server.thread;

import static application.messaging.Messages.*;
import static application.protocol.DataProtocol.*;
import application.server.properties.ServerProperties;
import application.server.transfer.*;
import java.io.*;
import java.net.Socket;

/**
 * Gets the protocol from the client and sends the data. This class deals with 
 * each client individually after the class MultiServer connects to the client.
 *
 * @author John Ford
 * @version 1.2
 * @see java.lang.Thread
 * @see application.server.MultiServer#connect()
 * @since 1.0
 */
public class MultiServerThread extends Thread {
    
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
    
    private InputStream systemInputStream;
    private OutputStream systemOutputStream;
    
    private String senderName;
    
    private String filePath;
    
    private final ServerProperties serverProperties;
    
    /* Create variables that control message logging */
    
    // Creates a new boolean for controlling the printing of console messages
    private boolean verbose;
    // Creates a new integer for specifying the level of messages printed to the
    // console
    private int messageLevel;
    
    /**
     * Creates a new MultiServerThread for connecting to the client and logging 
     * socket information. This constructor assigns values to the input and 
     * output streams and sets the message level and verbose to default (verbose
     * being true and the message level being ERROR).
     * 
     * @param socket
     * @throws IOException
     * @since 1.0
     */
    public MultiServerThread(Socket socket) throws IOException {
        
        // Calls the parent constructor with the class name as an argument
        super("MultiServerThread");
        
        // Assigns the global socket to the socket parameter
        this.socket = socket;
        
        serverProperties = new ServerProperties();
        
        /* Assign the input and output stream to the socket input and output 
        stream */
        
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
        
        // Assigns the global boolean verbose to default (true)
        this.verbose = true;
        // Assigns the global integer message level to default (ERROR)
        this.messageLevel = ERROR;
        
    }
    
    /**
     * Creates a new MultiServerThread for connecting to the client and logging 
     * socket information. This constructor assigns values to the input and 
     * output streams and sets the message level and verbose to default (verbose
     * being true and the message level being ERROR).
     * 
     * @param socket
     * @param verbose
     * @param messageLevel
     * @throws IOException
     * @since 1.0
     */
    public MultiServerThread(Socket socket, boolean verbose, int messageLevel) 
            throws IOException {
        
        // Calls the parent constructor with the class name as an argument
        super("MultiServerThread");
        
        // Assigns the global socket to the socket parameter
        this.socket = socket;
        
        serverProperties = new ServerProperties();
        
        /* Assign the input and output stream to the socket input and output 
        stream */
        
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
        
        // Assigns the global boolean verbose to the verbose parameter
        this.verbose = verbose;
        // Assigns the global integer message level to the message level 
        // parameter
        this.messageLevel = messageLevel;
        
    }
    
    /**
     * Gets the protocol from the client and sends the data to the client. This 
     * method overrides the run() method in class Thread. It reads the protocol 
     * using a buffered read, transfers the data, and safely disconnects from the client.
     * 
     * @see java.lang.Thread#run()
     * @see application.server.MultiServer#connect()
     * @since 1.0
     */
    @Override
    public void run() {
        
        // Tries to execute the code and logs any I/O Errors
        try {
            
            // Notifies the user that the protocol is being obtained from the 
            // client if log level is greater than or equal to 'INFO'
            printMessage("Obtaining protocol from client...", INFO);
            
            // Reads a line from the socket and stores it in the variable 
            // 'protocol'
            String protocol = in.readLine();
            
            if (protocol.equals(FILE_CS) || protocol.equals(FILES_CS) 
                    || protocol.equals(FILE_SC) || protocol.equals(FILES_SC)) {
                this.filePath = serverProperties.getProperty("filePath");
            }
            
            // Notifies the user that the protocol has been successfully 
            // obtained from the client if log level is greater than or equal to
            // 'INFO'
            printMessage("Protocol successfully obtained.", INFO);
            
            // Returns the value of the protocol if log level is greater than or
            // equal to 'DATA'
            printMessage("The protocol is '" + protocol + "'.", DATA);
            
            
            // Loops forever until the client sends the disconnect message
            while (true) {
                
                // Checks the protocol
                switch (protocol) {
                    
                    // If the protocol is 'FILE' ("FILE_TRANSFER")
                    case FILE_SC:
                        
                        // Notifies the user that the client requested a file
                        printMessage("Client '" + socket.getRemoteSocketAddress() + 
                                "' has requested a file.", HIGHEST_PRIORITY);
                        
                        // Creates a new FileTransferSC in order to transfer the 
                        // file
                        FileTransferSC fileTransfer = 
                                new FileTransferSC(socketOutputStream, verbose, 
                                        messageLevel);
                        // Sends the name of the file to the client
                        fileTransfer.sendFileName();
                        // Sends the file size to the client
                        fileTransfer.sendFileSize();
                        // Converts the file to bytes and sends it to the client
                        fileTransfer.transferFile();
                        
                        // Sends a 'DISCONNECT' message to the client for the 
                        // client to know that the transfer has been completed 
                        // and that the server is ready to disconnect
                        printMessage("Disconnecting from client '" 
                                + socket.getRemoteSocketAddress() + "'...", INFO);
                        sleep(1000);
                        out.println(DISCONNECT);
                        
                        // Breaks out of the switch statement
                        break;
                        
                    case FILE_CS:
                        printMessage("Client '" + socket.getRemoteSocketAddress() + 
                                "' has requested to send a file.", HIGHEST_PRIORITY);
                        
                        FileTransferCS fileTransferCS = new FileTransferCS(socketInputStream, 
                                filePath, verbose, messageLevel);
                        fileTransferCS.retrieveFileName();
                        fileTransferCS.retrieveFileSize();
                        fileTransferCS.retrieveFileData();
                        fileTransferCS.writeToFile();

                        printMessage("Disconnecting from client '" 
                                + socket.getRemoteSocketAddress() + "'...", INFO);
                        sleep(1000);
                        out.println(DISCONNECT);
                        
                        break;
                        
                    // If the protocol is 'FILES' ("MULTIPLE_FILE_TRANSFER")
                    case FILES_SC:
                        
                        // Notifies the user that the client has requested 
                        // multiple files
                        printMessage("Client '" + socket.getRemoteSocketAddress() + 
                                "' has requested multiple files.", HIGHEST_PRIORITY);
                        
                        // Creates a new MultipleFileTransferSC in order to 
                        // transfer the files
                        MultipleFileTransferSC multipleFileTransfer = 
                                new MultipleFileTransferSC(socketOutputStream, 
                                        verbose, messageLevel);
                        // Sends the number of files to the client
                        multipleFileTransfer.sendNumberOfFiles();
                        // Sends the file names to the client
                        multipleFileTransfer.sendFileNames();
                        // Sends the file sizes to the client
                        multipleFileTransfer.sendFileSizes();
                        // Converts the files to bytes and sends them to the 
                        // client
                        multipleFileTransfer.transferFiles();
                        
                        // Sends a 'DISCONNECT' message to the client for the 
                        // client to know that the transfer has been completed 
                        // and that the server is ready to disconnect
                        printMessage("Disconnecting from client '" 
                                + socket.getRemoteSocketAddress() + "'...", INFO);
                        sleep(1000);
                        out.println(DISCONNECT);
                        
                        // Breaks out of the switch statement
                        break;
                        
                    case FILES_CS:
                        printMessage("Client '" + socket.getRemoteSocketAddress() + 
                                "' has requested to send multiple files.", HIGHEST_PRIORITY);
                        MultipleFileTransferCS multipleFileTransferCS = 
                        new MultipleFileTransferCS(socketInputStream, filePath, 
                                verbose, messageLevel);
                        multipleFileTransferCS.retrieveNumberOfFiles();
                        multipleFileTransferCS.retrieveFileNames();
                        multipleFileTransferCS.retrieveFileSizes();
                        multipleFileTransferCS.retrieveFileData();
                        multipleFileTransferCS.writeToFiles();
                        
                        printMessage("Disconnecting from client '" 
                                + socket.getRemoteSocketAddress() + "'...", INFO);
                        sleep(1000);
                        out.println(DISCONNECT);
                        
                        break;
                        
                    // If the protocol is 'CHAT' ("CONTINUOUS_STRING_TRANSFER")
                    case CHAT:
                        
                        // Notifies the user that the client has requested to 
                        // chat
                        printMessage("Client '" + socket.getRemoteSocketAddress() + 
                                "' has requested a message.", HIGHEST_PRIORITY);
                        
                        // Creates a new MessageSender and MessageReciever in 
                        // order to chat with the client
                        MessageSender messageSender;
                        MessageReceiver messageReceiver;
                        
                        if (systemInputStream == null && senderName == null)
                            messageSender = new MessageSender(socketOutputStream, 
                                    verbose, messageLevel);
                        else if (systemInputStream == null && senderName != null)
                            messageSender = new MessageSender(senderName, 
                                    socketOutputStream, verbose, messageLevel);
                        else if (systemInputStream != null && senderName == null)
                            messageSender = new MessageSender(systemInputStream, 
                                    socketOutputStream, verbose, messageLevel);
                        else
                            messageSender = new MessageSender(senderName, systemInputStream, 
                                    socketOutputStream, true, verbose, messageLevel);

                        if (systemOutputStream == null && senderName == null)
                            messageReceiver  = new MessageReceiver(socketInputStream, 
                                    verbose, messageLevel);
                        else if (systemOutputStream == null && senderName != null)
                            messageReceiver = new MessageReceiver(systemOutputStream, 
                                    socketInputStream, verbose, messageLevel);
                        else if (systemOutputStream != null && senderName == null)
                            messageReceiver = new MessageReceiver(senderName, 
                                    socketInputStream, verbose, messageLevel);
                        else
                            messageReceiver = new MessageReceiver(senderName, systemOutputStream, 
                                    socketInputStream, true, verbose, messageLevel);
                        
                        // Starts the chat
                        messageSender.start();
                        messageReceiver.start();
                        
                        synchronized (messageSender) {
                            messageSender.wait();
                        }
                        
                        // Sends a 'DISCONNECT' message to the client for the 
                        // client to know that the transfer has been completed 
                        // and that the server is ready to disconnect
                        printMessage("Disconnecting main thread from client '" 
                                + socket.getRemoteSocketAddress() + "'...", INFO);
                        sleep(1000);
                        out.println(DISCONNECT);
                        
                        // Breaks out of the switch statement
                        break;
                        
                    // If the protocol is 'ABORT' ("ABORT_CONNECTION")
                    case ABORT:
                        
                        printMessage("line: 306: Client has encountered an "
                                + "error: Terminating connection...", ERROR);
                        
                        // Breaks out of the switch statement
                        // This is for visual purposes only, since if the 
                        // program errors correctly, it should automatically 
                        // stop and never reach this point in the code.
                        break;
                    
                    // If the protocol is not recognized
                    default:
                        printMessage("line 317: The value of protocol is '" 
                                + protocol + "': Unrecognized Protocol: "
                                + "Terminating connection...", ERROR);
                        
                        // Breaks out of the switch statement
                        // This is for visual purposes only, since if the 
                        // program errors correctly, it should automatically 
                        // stop and never reach this point in the code.
                        break;
                        
                }
                
                // Notifies the user that the protocol is being obtained from 
                // the client if log level is greater than or equal to 'INFO'
                printMessage("Obtaining protocol from client...", INFO);
            
                // Reads a line from the socket and stores it in the variable 
                // 'protocol'
                protocol = in.readLine();
                
                // Notifies the user that the protocol has been successfully 
                // obtained from the client if log level is greater than or equal to
                // 'INFO'
                printMessage("Protocol successfully obtained.", INFO);
                
                // Returns the value of the protocol if log level is greater 
                // than or equal to 'DATA'
                printMessage("The protocol is '" + protocol + "'.", DATA);
                
                
                // If the protocol is 'DISCONNECT' ("DISCONNECT")
                if (protocol.equals(DISCONNECT)) {
                    
                    // Notifies the user that the server has received the 
                    // DISCONNECT message
                    printMessage("Recieved DISCONNECT message from client. "
                            + "Disconnecting...", HIGHEST_PRIORITY);
                    
                    // Breaks out of the while loop
                    break;
                    
                }
                
                // If the protocol is not 'DISCONNECT ("DISCONNECT"), the server
                // will continue to loop until the protocol is disconnect
            }
            
        // If the code encounters an I/O Errors
        } catch (IOException ex) {
            
            // Notifies the user that the server has encountered an I/O Error, 
            // prints out the exception, and completely terminates the program
            printMessage("line 376: IOException: " + ex + ": Stopping connection"
                    + " with client...", ERROR);
            
        } catch (InterruptedException ex) {
            
            // Notifies the user that the server has encountered an interrupted 
            // Thread.sleep error, prints out the exception, and completely 
            // terminates the program
            printMessage("line 384: InterruptedException: " + ex 
                    + ": Stopping connection with client...", ERROR);
            
        }
        
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setSystemInputStream(InputStream systemInputStream) {
        this.systemInputStream = systemInputStream;
    }
    
    public InputStream getSystemInputStream() {
        return systemInputStream;
    }
    
    public void setSystemOutputStream(OutputStream systemOutputStream) {
        this.systemOutputStream = systemOutputStream;
    }
    
    public OutputStream getSystemOutputStream() {
        return systemOutputStream;
    }
    
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    public String getSenderName() {
        return senderName;
    }
    
    /**
     * Reads a line of text from the socket. A line is considered to be 
     * terminated by any one of a line feed ('\n'), a carriage return ('\r'), or
     * a carriage return followed immediately by a linefeed.
     * 
     * @return A String containing the contents of the line, not including any 
     * line-termination characters, or null if the end of the stream has been 
     * reached
     * @throws IOException - If an I/O error occurs
     * @see java.io.BufferedReader#readLine()
     * @since 1.0
     */
    public String readLine() throws IOException {
        
        // Calls the readLine method on the socket buffered reader and returns 
        // the value
        return in.readLine();
    }
    
    /**
     * Prints a string. If the argument is null then the string "null" is 
     * printed. Otherwise, the string's characters are converted into bytes 
     * according to the platform's default character encoding, and these bytes 
     * are written in exactly the manner of the PrintWriter.write(int) method.
     * 
     * @param string - The String to be printed
     * @see java.io.PrintWriter#print(java.lang.String)
     * @since 1.0
     */
    public void print(String string) {
        
        // Prints out the string to the socket
        out.print(string);
        
    }
    
    /**
     * Prints a String and then terminates the line. This method behaves as 
     * though it invokes PrintWriter.print(String) and then PrintWriter.println().
     * 
     * @param string - The String value to be printed
     * @see java.io.PrintWriter#println(java.lang.String)
     * @since 1.0
     */
    public void println(String string) {
        
        // Prints out the string value and a new line symbol to the socket
        out.println(string);
        
    }
    
    /**
     * Sets the value of verbose. This method is used to set if messages should 
     * be logged to the console or not. This does not apply to <code>
     * HIGHEST_PRIORITY</code> messages.
     * 
     * @param verbose - If messages should be logged
     * @see #verbose
     * @see #printMessage
     * @since 1.0
     */
    public void setVerbose(boolean verbose) {
        
        // Assigns the global boolean verbose to the verbose parameter
        this.verbose = verbose;
        
    }
    
    /**
     * Returns the value of verbose. This method is used to see if messages 
     * should be logged to the console or not. This does not apply to <code>
     * HIGHEST_PRIORITY</code> messages.
     * 
     * @return boolean - The value of verbose
     * @see #verbose
     * @see #printMessage
     * @since 1.0
     */
    public boolean getVerbose() {
        
        // Returns the value of verbose
        return verbose;
        
    }
    
    /**
     * Sets the value of messageLevel. This method is used to set the message
     * level.
     * 0 => HIGHEST_PRIORITY
     * 1 => ERROR
     * 2 => WARNING
     * 3 => INFO
     * 4 => DATA
     * 
     * @param messageLevel - The log level of messages
     * @see #messageLevel
     * @see #printMessage(java.lang.String, int)
     * @since 1.0
     */
    public void setMessageLevel(int messageLevel) {
        
        // Assigns the global integer messageLevel to the messageLevel parameter
        this.messageLevel = messageLevel;
        
    }
    
    /**
     * Gets the value of messageLeve. This method is used to get the message 
     * level.
     * 
     * @return integer - The message level
     * @see #messageLevel
     * @see #printMessage(java.lang.String, int)
     * @since 1.0
     */
    public int getMessageLevel() {
        
        // Returns the value of messageLevel
        return messageLevel;
        
    }
    
    /**
     * Prints messages to the console. This method checks to see if the level of
     * the message is greater than or equal than the desired message level, and 
     * if it is, it logs the message to the console. If the message level is 
     * <code>ERROR</code>, then the system is exited with error code 1 (Error).
     * 
     * @param message - The message to print
     * @param level - The level of the message
     * @see #messageLevel
     * @see #verbose
     * @see application.messaging.Messages
     * @since 1.0
     */
    protected void printMessage(String message, int level) {
        
        // Creates a variable which holds the string value of level
        // For example: 1 = "ERROR"
        String stringLevel;
        
        // If the level is equal to 'ERROR' (1)
        if (level == ERROR) {
            
            // Sets the value of stringLevel to "ERROR"
            stringLevel = "ERROR";
            
        // If the level is equal to 'WARNING' (2)
        } else if (level == WARNING) {
            
            // Sets the value of stringLevel to "WARNING"
            stringLevel = "WARNING";
            
        // If the level is equal to 'INFO' (3)
        } else if (level == INFO) {
            
            // Sets the value of stringLevel to "INFO"
            stringLevel = "INFO";
            
        // If the level is equal to 'DATA' (4)
        } else if (level == DATA) {
            
            // Sets the value of stringLevel to "DATA"
            stringLevel = "DATA";
            
        // Otherwise
        } else {
            
            // Sets the value of stringLevel to "HIGHEST_PRIORITY"
            // This is for visual purposes only, since if the level is 
            // 'HIGHEST_PRIORITY', the program will not print the level to the 
            // console
            stringLevel = "HIGHEST_PRIORITY";
            
        }
        
        // If verbose is equal to true, if the desired message level is greater 
        // or equal to the desired message level, and if the message level is 
        // not HIGHEST_PRIORITY
        if ((verbose && messageLevel >= level) && level != HIGHEST_PRIORITY) {
            
            // Prints out "MultiServerThread:", the string value of the level, 
            // and the message to the console
            System.out.println("MultiServerThread: [" + stringLevel + "] " + message);
            
        // If the message level is HIGHEST_PRIORITY
        } else if (level == HIGHEST_PRIORITY) {
            
            // Prints out "MultiServerThread:" and the message to the console
            System.out.println("MultiServerThread: " + message);
            
        }
        
        // If the level is equal to 'ERROR'
        if (level == ERROR) {
            
            // Sends an ABORT message to the client
            out.println(ABORT);
            
        }
        
    }
    
}