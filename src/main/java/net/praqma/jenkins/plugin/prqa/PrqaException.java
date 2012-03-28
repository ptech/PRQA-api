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
        super(msg,cause);
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
    
    
}
