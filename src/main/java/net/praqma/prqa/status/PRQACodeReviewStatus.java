/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import net.praqma.prga.excetions.PrqaException;
import net.praqma.prga.excetions.PrqaReadingException;

/**
 *
 * @author Praqma
 */
public class PRQACodeReviewStatus extends PRQAStatus {

	@Override
	public boolean isValid() {
		logger.finest(String.format("Starting execution of method - isValid"));

		UnsupportedOperationException exception;
		exception = new UnsupportedOperationException("Not supported yet.");

		logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

		throw exception;
	}

	@Override
	public Number getReadout(StatusCategory category) throws PrqaException {
		logger.finest(String.format("Starting execution of method - getReadout"));
		logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));

		switch(category) {
		default:
			PrqaReadingException exception;
			exception = new PrqaReadingException("Failed reading readout, illegal category");

			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

			throw exception;
		}
	}

	@Override
	public void setReadout(StatusCategory category, Number value) throws PrqaException {
		logger.finest(String.format("Starting execution of method - setReadout"));
		logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));
		logger.finest(String.format("Input parameter value type: %s; value: %s", value.getClass(), value));

		switch(category) {
		default:
			PrqaReadingException exception;
			exception = new PrqaReadingException(String.format("Could not set value of %s for category %s in class %s",value,category,this.getClass()));

			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

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
