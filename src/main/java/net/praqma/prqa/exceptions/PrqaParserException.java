/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.exceptions;

/**
 *
 * @author Praqma
 */
public class PrqaParserException extends PrqaException {
    public PrqaParserException(String message, Exception originalException) {         
        super(originalException);
    }
}
