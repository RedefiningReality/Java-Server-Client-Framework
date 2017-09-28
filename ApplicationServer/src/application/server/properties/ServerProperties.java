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
package application.server.properties;

import static application.messaging.Messages.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author User
 */
public class ServerProperties {
    
    public String propFileName;
    public String propFileLocation;
    
    private boolean verbose;
    private int messageLevel;
    
    public ServerProperties() {
        this.propFileName = "config.properties";
        this.propFileLocation = "";
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public ServerProperties(String propFileName) {
        this.propFileName = propFileName;
        this.propFileLocation = "";
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public ServerProperties(String propFileName, String propFileLocation) {
        this.propFileName = propFileName;
        this.propFileLocation = propFileLocation;
        
        this.verbose = true;
        this.messageLevel = ERROR;
    }
    
    public ServerProperties(String propFileName, String propFileLocation, 
            boolean verbose, int messageLevel) {
        this.propFileName = propFileName;
        this.propFileLocation = propFileLocation;
        
        this.verbose = verbose;
        this.messageLevel = messageLevel;
    }
    
    public String getProperty(String property) throws IOException {
        String value;
        Properties properties;
        properties = new Properties();
        
        FileInputStream inputStream;
        inputStream = new FileInputStream(propFileLocation + propFileName);
        
        properties.load(inputStream);
        
        value = properties.getProperty(property);
        return value;
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
            System.out.println("ServerProperties: [" + stringLevel + "] " + message);
        } else if (level == HIGHEST_PRIORITY) {
            System.out.println("ServerProperties: " + message);
        }
        if (level == ERROR) {
            System.exit(ERROR);
        }
    }
}