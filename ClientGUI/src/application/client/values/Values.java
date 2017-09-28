package application.client.values;

/**
 *
 * @author John Ford
 */
public class Values {

    private static boolean verbose;
    private static int messageLevel;
    
    private static String hostName;
    private static int PORT;
    
    private static String senderName;
    private static String transferFilePath;
    
    public static void setHostName(String hostName) {
        Values.hostName = hostName;
    }
    
    public static String getHostName() {
        return hostName;
    }
    
    public static void setPort(int PORT) {
        Values.PORT = PORT;
    }
    
    public static int getPort() {
        return PORT;
    }
    
    public static void setSenderName(String senderName) {
        Values.senderName = senderName;
    }
    
    public static String getSenderName() {
        return senderName;
    }
    
    public static void setTransferFilePath(String transferFilePath) {
        Values.transferFilePath = transferFilePath;
    }
    
    public static String getTransferFilePath() {
        return transferFilePath;
    }
    
    public static void setVerbose(boolean verbose) {
        Values.verbose = verbose;
    }
    
    public static boolean getVerbose() {
        return verbose;
    }
    
    public static void setMessageLevel(int messageLevel) {
        Values.messageLevel = messageLevel;
    }
    
    public static int getMessageLevel() {
        return messageLevel;
    }
}
