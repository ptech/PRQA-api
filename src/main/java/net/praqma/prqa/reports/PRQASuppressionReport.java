/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.parsers.SuppressionReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQASuppressionStatus;

/**
 *
 * @author Praqma
 */
public class PRQASuppressionReport extends PRQAReport<PRQASuppressionStatus> {
    
    public PRQASuppressionReport() {
        this.parser = new SuppressionReportParser();
    }
    
    public PRQASuppressionReport(QAR qar) throws PrqaException {
    	super(qar);
    	
        logger.finest(String.format("Constructor and super constructor called for class PRQASuppressionReport"));
        
        this.parser = new SuppressionReportParser();
        
        logger.finest(String.format("Ending execution of constructor - PRQASuppressionReport"));
    }

    @Override
    public PRQASuppressionStatus generateReport() throws PrqaException {
    	logger.finest(String.format("Starting execution of method - completeTask"));
    	
        executeQAR();
        
        PRQASuppressionStatus status = new PRQASuppressionStatus();
        status.setLinesOfCode(Integer.parseInt(parser.getResult(SuppressionReportParser.linesOfCodePattern)));
        status.setNumberOfFiles(Integer.parseInt(parser.getResult(SuppressionReportParser.numberOfFilesPattern)));
        status.setMsgsSuppressed(Integer.parseInt(parser.getResult(SuppressionReportParser.numberOfMessagesSuppressedPattern)));
        status.setPctMsgsSuppressed(Double.parseDouble(parser.getResult(SuppressionReportParser.percentageOfMsgSuppressedPattern)));
        status.setUniqueMsgsSuppressed(Integer.parseInt(parser.getResult(SuppressionReportParser.uniqueMessagesSuppressedPattern)));
        
        logger.finest(String.format("Returning value: %s", status));
        
        return status;
    }
    
    @Override
    public String getDisplayName() {
        return "Suppression";
    }
}
