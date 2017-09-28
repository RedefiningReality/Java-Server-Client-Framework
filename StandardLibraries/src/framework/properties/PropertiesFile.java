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
package framework.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import framework.logging.Logger;

/**
 *
 * @author John Ford
 */
public class PropertiesFile {
	
	private static boolean isInitialized = false;
	
	private static String propFileName;
    private static String propFileLocation;
    
    private static Properties properties;
    private static FileInputStream inputStream;
    private static FileOutputStream outputStream;
    
    private static Logger logger;
    
    public static void initialize() throws FileNotFoundException, IOException {
        propFileName = "config.properties";
        propFileLocation = "";
        
        properties = new Properties();
        
        if (!new File(propFileLocation + propFileName).exists())
            outputStream = new FileOutputStream(propFileLocation + propFileName);
        else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
        
        logger = new Logger("PropertiesFile");
        
        isInitialized = true;
    }
    
    public static void initialize(String propFileName) throws IOException {
        PropertiesFile.propFileName = propFileName;
        propFileLocation = "";
        
        properties = new Properties();
        
        if (!new File(propFileLocation + propFileName).exists())
            outputStream = new FileOutputStream(propFileLocation + propFileName);
        else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
        
        logger = new Logger("PropertiesFile");
        
        isInitialized = true;
    }
    
    public static void initialize(String propFileName, String propFileLocation) 
            throws IOException {
        PropertiesFile.propFileName = propFileName;
        PropertiesFile.propFileLocation = propFileLocation;
        
        properties = new Properties();
        
        if (!new File(propFileLocation + propFileName).exists())
            outputStream = new FileOutputStream(propFileLocation + propFileName);
        else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
        
        logger = new Logger("PropertiesFile");
        
        isInitialized = true;
    }
    
    public static void initialize(String propFileName, boolean verbose, 
    		int messageLevel) throws IOException {
        PropertiesFile.propFileName = propFileName;
        propFileLocation = "";
        
        properties = new Properties();
        
        if (!new File(propFileLocation + propFileName).exists())
            outputStream = new FileOutputStream(propFileLocation + propFileName);
        else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
        
        logger = new Logger("PropertiesFile", verbose, messageLevel);
        
        isInitialized = true;
    }
    
    public static void initialize(String propFileName, String propFileLocation, 
            boolean verbose, int messageLevel) throws IOException {
        PropertiesFile.propFileName = propFileName;
        PropertiesFile.propFileLocation = propFileLocation;
        
        properties = new Properties();
        
        if (!new File(propFileLocation + propFileName).exists())
            outputStream = new FileOutputStream(propFileLocation + propFileName);
        else
            outputStream = new FileOutputStream(propFileLocation + propFileName,
                    true);
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
        
        logger = new Logger("PropertiesFile", verbose, messageLevel);
        
        isInitialized = true;
    }
    
    public static boolean isInitialized() {
    	return isInitialized;
    }
    
    public static boolean addProperty(String property, String value) throws IOException {
    	if(isInitialized)
    		if(!properties.containsKey(property)) {
    			properties.setProperty(property, value);
    			return true;
    		}
    	
    	return false;
    }
    
    public static String setProperty(String property, String value) throws IOException {
    	if(isInitialized)
    		return (String)properties.setProperty(property, value);
    	
    	return null;
    }
    
    public static String getProperty(String property) throws IOException {
    	if(isInitialized)
    		return properties.getProperty(property);
    	
    	return null;
    }
    
    public static void saveToFile() throws IOException {
    	if(isInitialized) {
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
    }
    
    public static void removeFile() throws IOException {
    	if(isInitialized) {
	    	inputStream.close();
	    	outputStream.close();
	    	
	    	Files.deleteIfExists(Paths.get(propFileLocation + propFileName));
	    	
	    	isInitialized = false;
    	}
    }
    
    public static void setPropertyFileName(String propFileName) throws IOException {
        PropertiesFile.propFileName = propFileName;
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
    }
    
    public static String getPropertyFileName() {
        return propFileName;
    }
    
    public static void setPropertyFileLocation(String propFileLocation) 
            throws IOException {
        PropertiesFile.propFileLocation = propFileLocation;
        
        inputStream = new FileInputStream(propFileLocation + propFileName);
        properties.load(inputStream);
    }
    
    public static String getPropertyFileLocation() {
        return propFileLocation;
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