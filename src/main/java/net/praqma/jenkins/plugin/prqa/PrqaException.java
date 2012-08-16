package net.praqma.jenkins.plugin.prqa;

import java.io.IOException;
import net.praqma.prqa.PRQA;

/**
 *
 * @author jes
 */
public class PrqaException extends Exception {

    public PrqaException(String string) {
        super(string);
    }
    
    public PrqaException(Throwable cause) {
        super(cause);
    }
    
    public PrqaException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public String toString() {
        return String.format("Caught exception with message:\n\t%s\nCaused by:\n\t%s", getMessage(),getCause());
    }
    /**
     * A small static class. Several subtypes of the same exception.
     */
    public static class PrqaCommandLineException extends PrqaException {
        public PrqaCommandLineException(PRQA command, Exception ex) {
            super(ex);
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
