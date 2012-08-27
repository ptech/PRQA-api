/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prga.excetions;

/**
 *
 * @author Praqma
 */
public class PrqaReportGenerationException extends PrqaException {
    public PrqaReportGenerationException(String message) {
        super(message);
    }
    
    public PrqaReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }   
}
