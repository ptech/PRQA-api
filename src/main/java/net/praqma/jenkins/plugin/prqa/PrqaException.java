package net.praqma.jenkins.plugin.prqa;

import java.io.IOException;
import java.util.HashMap;
import net.praqma.prqa.PRQA;

/**
 *
 * @author jes
 */
public class PrqaException extends Exception {
    public static HashMap<Integer,String> generalReturnMessages = new HashMap<Integer, String>();
    
    static {
        PrqaException.generalReturnMessages.put(1, "Basic operation warning <NO_FAIL> <DIAGNOSE>");
        PrqaException.generalReturnMessages.put(2, "Information warning <NO_FAIL> <DIAGNOSE>");
        PrqaException.generalReturnMessages.put(10, "Failed: Configuration <FAIL> <NO_DIAGNOSE>");
        PrqaException.generalReturnMessages.put(10, "Failed: Licensing <FAIL> <NO_DIAGNOSE>");
        PrqaException.generalReturnMessages.put(18, "Failed: Tool <FAIL> <NO_DIAGNOSE>");
        PrqaException.generalReturnMessages.put(19, "Failed: System failure <FAIL> <NO_DIAGNOSE>");
    }
    
    private int returnCode;

    public PrqaException(String string) {
        super(string);
    }
    
    public PrqaException(Throwable cause) {
        super(cause);
    }
    
    public PrqaException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public PrqaException(String msg, Throwable cause, int returnCode) {
        super(msg, cause);
    }
    
    public PrqaException(Throwable cause, int returnCode) {
        super(cause);
        this.returnCode = returnCode;
    }

    @Override
    public String toString() {
        return String.format(PrqaException.generalReturnMessages.containsKey(returnCode) ? "" : PrqaException.generalReturnMessages.get(returnCode) + "\nCaught exception with message:\n\t%s\nCaused by:\n\t%s", getMessage(),getCause());
    }
    /**
     * A small static class. Several subtypes of the same exception.
     */
    public static class PrqaCommandLineException extends PrqaException {
        private PRQA command;
        public PrqaCommandLineException(PRQA command, Exception ex) {
            super(ex);
            this.command = command;
        }
        
        public PrqaCommandLineException(PRQA command, Exception ex, int returnCode) {
            super(ex,returnCode);
            this.command = command;
        }
    }
    
    public static class PrqaParserException extends PrqaException {
        public PrqaParserException(String message, IOException originalException) {         
            super(originalException);
        }
    }
    
    public static class PrqaReadingException extends PrqaException {
        public PrqaReadingException(String message) {
            super(message);
        }
        public PrqaReadingException(String message, Exception ex) {
            super(message,ex);
        }
    }
    
    public static class PrqaSetupException extends PrqaException {
        public PrqaSetupException(String message) {
            super(message);
        }
        public PrqaSetupException(String message, Exception ex) {
            super(message,ex);
        }
    }
    
    public static class PrqaUploadException extends PrqaException {
        public PrqaUploadException(String message) {
            super(message);
        }
        
        public PrqaUploadException(String message, Exception ex) {
            super(message, ex);
        }
    }
}
