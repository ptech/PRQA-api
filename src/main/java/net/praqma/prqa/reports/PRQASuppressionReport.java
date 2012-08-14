/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.logging.Level;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.parsers.SuppressionReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQASuppressionStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CommandLineException;

/**
 *
 * @author Praqma
 */
public class PRQASuppressionReport extends PRQAReport<PRQASuppressionStatus> {
    
    public PRQASuppressionReport(QAR qar) throws PrqaException {
    	super(qar);
        logger.log(Level.FINEST, "Constructor and super constructor called for class PRQASuppressionReport");
        this.parser = new SuppressionReportParser();
    }

    @Override
    public PRQASuppressionStatus completeTask() throws PrqaException {
    	logger.log(Level.FINEST, "Starting execution of method - completeTask");
    	
        executeQAR();
        
        PRQASuppressionStatus status = new PRQASuppressionStatus();
        status.setLinesOfCode(Integer.parseInt(parser.getResult(SuppressionReportParser.linesOfCodePattern)));
        status.setNumberOfFiles(Integer.parseInt(parser.getResult(SuppressionReportParser.numberOfFilesPattern)));
        status.setMsgsSuppressed(Integer.parseInt(parser.getResult(SuppressionReportParser.numberOfMessagesSuppressedPattern)));
        status.setPctMsgsSuppressed(Double.parseDouble(parser.getResult(SuppressionReportParser.percentageOfMsgSuppressedPattern)));
        status.setUniqueMsgsSuppressed(Integer.parseInt(parser.getResult(SuppressionReportParser.uniqueMessagesSuppressedPattern)));
        
        logger.log(Level.FINEST, "Returning status {0}", status);
        
        return status;
    }
}
