/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.logging.Level;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.parsers.CodeReviewReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQACodeReviewStatus;

/**
 * 
 * @author Praqma
 */
public class PRQACodeReviewReport extends PRQAReport<PRQACodeReviewStatus> {

    public PRQACodeReviewReport() {
        logger.finest(String.format("Constructor called for class PRQACodeReviewReport()"));
        
        this.parser = new CodeReviewReportParser();
        
        logger.finest(String.format("Ending execution of constructor - PRQACodeReviewReport()"));
    }
    
	public PRQACodeReviewReport(QAR qar) {
		super(qar);
        logger.finest(String.format("Constructor and super constructor called for class PRQACodeReviewReport"));
        
        this.parser = new CodeReviewReportParser();
        
        logger.finest(String.format("Ending execution of constructor - PRQACodeReviewReport"));
    }

	@Override
	public PRQACodeReviewStatus generateReport() throws PrqaException {
		logger.finest(String.format("Starting execution of method - completeTask"));
		
        executeQAR();
		
		PRQACodeReviewStatus status = new PRQACodeReviewStatus();
		
		logger.finest(String.format("Returning value: %s", status));
		
		return status;
	}
    
    @Override
    public String getDisplayName() {
        return "Code Review";
    }
}
