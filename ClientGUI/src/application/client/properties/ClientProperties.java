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
package application.client.properties;

import static application.messaging.Messages.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author User
 */
public class ClientProperties {
    
    private static String propFileName;
    private static String propFileLocation;
    
    private static Properties properties;
    private static FileInputStream inputStream;
    private static FileOutputStream outputStream;
    
    private static List<String[]> propertyValues;
    
    private static boolean verbose;
    private static int messageLevel;
    
    public static void initialize() throws FileNotFoundException, IOException {
        propFileName = "config.properties";
        propFileLocation = "";
        
        properties = new Properties();
        propertyValues = new ArrayList<>();
        propertyValues.add(new String[]{"hostName", "192.168.1.68"});
        propertyValues.add(new String[]{"PORT", "28743"});
        propertyValues.add(new String[]{"acceptFileTransfer", "true"});
        propertyValues.add(new String[]{"filePath", "C:\\"});
        propertyValues.add(new String[]{"transferFilePath", "C:\\"});
        propertyValues.add(new String[]{"numberOfFiles", "1"});
        propertyValues.add(new String[]{"transferFilePath1", "C:\\"});
        propertyValues.add(new String[]{"acceptStringTransfer", "true"});
        propertyValues.add(new String[]{"senderName", "Name"});
        
        if (!new File(propFileLocation + propFileName).exists()) {
            outputStream = new FileOutputStream(propFileLocation + propFileName);
            createPropertyFile();
        } else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        
        properties.load(inputStream);
        
        verbose = true;
        messageLevel = ERROR;
    }
    
    public static void initialize(String propFileName) throws IOException {
        ClientProperties.propFileName = propFileName;
        propFileLocation = "";
        
        properties = new Properties();
        propertyValues = new ArrayList<>();
        propertyValues.add(new String[]{"hostName", "192.168.1.68"});
        propertyValues.add(new String[]{"PORT", "28743"});
        propertyValues.add(new String[]{"acceptFileTransfer", "true"});
        propertyValues.add(new String[]{"filePath", "C:\\"});
        propertyValues.add(new String[]{"transferFilePath", "C:\\"});
        propertyValues.add(new String[]{"numberOfFiles", "1"});
        propertyValues.add(new String[]{"transferFilePath1", "C:\\"});
        propertyValues.add(new String[]{"acceptStringTransfer", "true"});
        propertyValues.add(new String[]{"senderName", "Name"});
        
        if (!new File(propFileLocation + propFileName).exists()) {
            outputStream = new FileOutputStream(propFileLocation + propFileName);
            createPropertyFile();
        } else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        
        properties.load(inputStream);
        
        verbose = true;
        messageLevel = ERROR;
    }
    
    public static void initialize(String propFileName, String propFileLocation) 
            throws IOException {
        ClientProperties.propFileName = propFileName;
        ClientProperties.propFileLocation = propFileLocation;
        
        properties = new Properties();
        propertyValues = new ArrayList<>();
        propertyValues.add(new String[]{"hostName", "192.168.1.68"});
        propertyValues.add(new String[]{"PORT", "28743"});
        propertyValues.add(new String[]{"acceptFileTransfer", "true"});
        propertyValues.add(new String[]{"filePath", "C:\\"});
        propertyValues.add(new String[]{"transferFilePath", "C:\\"});
        propertyValues.add(new String[]{"numberOfFiles", "1"});
        propertyValues.add(new String[]{"transferFilePath1", "C:\\"});
        propertyValues.add(new String[]{"acceptStringTransfer", "true"});
        propertyValues.add(new String[]{"senderName", "Name"});
        
        if (!new File(propFileLocation + propFileName).exists()) {
            outputStream = new FileOutputStream(propFileLocation + propFileName);
            createPropertyFile();
        } else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        
        properties.load(inputStream);
        
        verbose = true;
        messageLevel = ERROR;
    }
    
    public static void initialize(String propFileName, String propFileLocation, 
            boolean verbose, int messageLevel) throws IOException {
        ClientProperties.propFileName = propFileName;
        ClientProperties.propFileLocation = propFileLocation;
        
        properties = new Properties();
        propertyValues = new ArrayList<>();
        propertyValues.add(new String[]{"hostName", "192.168.1.68"});
        propertyValues.add(new String[]{"PORT", "28743"});
        propertyValues.add(new String[]{"acceptFileTransfer", "true"});
        propertyValues.add(new String[]{"filePath", "C:\\"});
        propertyValues.add(new String[]{"transferFilePath", "C:\\"});
        propertyValues.add(new String[]{"numberOfFiles", "1"});
        propertyValues.add(new String[]{"transferFilePath1", "C:\\"});
        propertyValues.add(new String[]{"acceptStringTransfer", "true"});
        propertyValues.add(new String[]{"senderName", "Name"});
        
        if (!new File(propFileLocation + propFileName).exists()) {
            outputStream = new FileOutputStream(propFileLocation + propFileName);
            createPropertyFile();
        } else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        
        properties.load(inputStream);
        
        ClientProperties.verbose = verbose;
        ClientProperties.messageLevel = messageLevel;
    }
    
    public static String getProperty(String property) throws IOException {
        return properties.getProperty(property);
    }
    
    public static void setProperty(String property, String value) throws IOException {
        for (int i = 0; i < propertyValues.size(); i++) {
            if (propertyValues.get(i)[0].equals(property)) {
                propertyValues.set(i, new String[]{property, value});
                properties.setProperty(property, value);
            }
        }
    }
    
    public static void addProperty(String property, String value) throws IOException {
        propertyValues.add(new String[]{property, value});
        properties.setProperty(property, value);
    }
    
    public static final void createPropertyFile() throws IOException {
        for (String[] propertyValue : propertyValues)
            properties.setProperty(propertyValue[0], propertyValue[1]);
        
        properties.store(outputStream, "The values here are edited accordingly "
                + "while running the Graphical User Interface (GUI)\n"
                + "This file has been created to store previous entries and for "
                + "the use of advanced users\n"
                + "DO NOT REMOVE ANY OF THE PROPERTIES DEFINED IN THIS FILE "
                + "UNLESS YOU HAVE KNOWLEDGE OF THE INNER WORKINGS OF THE GUI!\n"
                + "Doing so may cause errors when opening the GUI");
    }
    
    public static void saveToFile() throws IOException {
        inputStream.close();
        outputStream.close();
        
        Files.deleteIfExists(Paths.get(propFileLocation + propFileName));
        
        outputStream = new FileOutputStream(propFileLocation + propFileName);
        
        properties.store(outputStream, "The values here are edited accordingly "
                + "while running the Graphical User Interface (GUI)\n"
                + "This file has been created to store previous entries and for "
                + "the use of advanced users\n"
                + "DO NOT REMOVE ANY OF THE PROPERTIES DEFINED IN THIS FILE "
                + "UNLESS YOU HAVE KNOWLEDGE OF THE INNER WORKINGS OF THE GUI!\n"
                + "Doing so may cause errors when opening the GUI");
    }
    
    public static void setPropertyFileName(String propFileName) throws IOException {
        ClientProperties.propFileName = propFileName;
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
    }
    
    public static String getPropertyFileName() {
        return propFileName;
    }
    
    public static void setPropertyFileLocation(String propFileLocation) 
            throws IOException {
        ClientProperties.propFileLocation = propFileLocation;
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
    }
    
    public static String getPropertyFileLocation() {
        return propFileLocation;
    }
    
    public static void setVerbose(boolean verbose) {
        ClientProperties.verbose = verbose;
    }
    
    public static boolean getVerbose() {
        return verbose;
    }
    
    public static void setMessageLevel(int messageLevel) {
        ClientProperties.messageLevel = messageLevel;
    }
    
    public static int getMessageLevel() {
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
            System.out.println("ServerProperties: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("ServerProperties: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}