/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.logging.Level;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.parsers.CodeReviewReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQACodeReviewStatus;

/**
 * 
 * @author Praqma
 */
public class PRQACodeReviewReport extends PRQAReport<PRQACodeReviewStatus> {

	public PRQACodeReviewReport(QAR qar) {
		super(qar);
        logger.log(Level.FINEST, "Constructor and super constructor called for class PRQACodeReviewReport");
        
        this.parser = new CodeReviewReportParser();
        
        logger.log(Level.FINEST, "Ending execution of constructor - PRQACodeReviewReport");
    }

	@Override
	public PRQACodeReviewStatus completeTask() throws PrqaException {
		logger.log(Level.FINEST, "Starting execution of method - completeTask");
		
        executeQAR();
		
		PRQACodeReviewStatus status = new PRQACodeReviewStatus();
		
		logger.log(Level.FINEST, "Returning value: {0}", status);
		
		return status;
	}
}
