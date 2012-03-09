package net.praqma.jenkins.plugin.prqa;

import java.io.IOException;
import net.praqma.prqa.PRQA;

/**
 *
 * @author jes
 */
public class PrqaException extends Exception {

    private Exception rootCause;
    
    public PrqaException(String string) {
        super(string);
    }
    
    public PrqaException(Throwable cause) {
        super(cause);
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
}