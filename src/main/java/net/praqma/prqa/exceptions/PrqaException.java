package net.praqma.prqa.exceptions;

import java.util.HashMap;

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
    
    private int returnCode = 99;

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

    @Override
    public String toString() {
        return String.format(PrqaException.generalReturnMessages.containsKey(returnCode) ? "" : PrqaException.generalReturnMessages.get(returnCode) + "\nCaught exception with message:\n\t%s\nCaused by:\n\t%s", getMessage(),getCause());
    }
}
