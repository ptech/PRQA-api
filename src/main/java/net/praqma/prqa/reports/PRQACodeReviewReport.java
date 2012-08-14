/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.logging.Config;
import net.praqma.prqa.parsers.CodeReviewReportParser;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQACodeReviewStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CommandLineException;

/**
 * 
 * @author Praqma
 */
public class PRQACodeReviewReport extends PRQAReport<PRQACodeReviewStatus> {

	public PRQACodeReviewReport(QAR qar) {
		super(qar);
        logger.log(Level.FINEST, "Constructor and super constructor called for class PRQACodeReviewReport");
        this.parser = new CodeReviewReportParser();
    }

	@Override
	public PRQACodeReviewStatus completeTask() throws PrqaException {
		logger.log(Level.FINEST, "Starting execution of method - completeTask");
		
        executeQAR();
		
		PRQACodeReviewStatus status = new PRQACodeReviewStatus();
		
		logger.log(Level.FINEST, "Returning status {0}", status);
		
		return status;
	}
}
