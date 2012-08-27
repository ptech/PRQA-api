/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prga.excetions;

/**
 *
 * @author Praqma
 */
public class PrqaSetupException extends PrqaException {
    public PrqaSetupException(String message) {
        super(message);
    }
    public PrqaSetupException(String message, Exception ex) {
        super(message,ex);
    }
}
