/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import java.util.logging.Level;

import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 *
 * @author Praqma
 */
public class PRQACodeReviewStatus extends PRQAStatus {

    @Override
    public boolean isValid() {
        logger.log(Level.FINEST, "Starting execution of method - isValid");
        
        UnsupportedOperationException exception;
        exception = new UnsupportedOperationException("Not supported yet.");
        
        logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});

        throw exception;
    }

    @Override
    public Number getReadout(StatusCategory category) throws PrqaException.PrqaReadingException {
    	logger.log(Level.FINEST, "Starting execution of method - getReadout");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{category.getClass(), category});

        switch(category) {
            default:
            	PrqaException.PrqaReadingException exception;
            	exception = new PrqaException.PrqaReadingException("Not supported yet.");
            	
            	logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});
            	
            	throw exception;
        }
    }

    @Override
    public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
    	logger.log(Level.FINEST, "Starting execution of method - setReadout");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{category.getClass(), category});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{value.getClass(), value});

        switch(category) {
            default:
            	PrqaException.PrqaReadingException exception;
            	exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s",value,category,this.getClass()));
            	
            	logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});
            	
            	throw exception;
        }       
    }

    @Override
    public String toString() {
        String res = "";
        res += "Scanned the following CodeReview values" + System.getProperty("line.separator");
        return res;
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
