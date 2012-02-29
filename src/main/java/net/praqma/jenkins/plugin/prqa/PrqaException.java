package net.praqma.jenkins.plugin.prqa;

import net.praqma.prqa.PRQA;

/**
 *
 * @author jes
 */
public class PrqaException extends Exception {

    public PrqaException(String string) {
        super(string);
    }
    
    public static class PrqaCommandLineException extends PrqaException {
        public PrqaCommandLineException(PRQA command) {
            super(command.toString());
        }
    }
}
