/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.exceptions;

import net.praqma.prqa.PRQA;

/**
 *
 * @author Praqma
 */
public class PrqaCommandLineException extends PrqaException {
    private PRQA command;
    private int returnCode;
    
    public PrqaCommandLineException(String message, Exception ex, PRQA command) {
        super(message, ex);
        this.command = command;
    }

    public PrqaCommandLineException(String message, PRQA command, Exception ex, int returnCode) {
        super(message,ex);
        this.returnCode = returnCode;
        this.command = command;
    }

    /**
     * @return the command
     */
    public PRQA getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(PRQA command) {
        this.command = command;
    }

    /**
     * @return the returnCode
     */
    public int getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
