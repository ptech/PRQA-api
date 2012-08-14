/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.List;
import java.util.logging.Level;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAQualityStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CommandLineException;

/**
 *
 * @author Praqma
 */
public class PRQAQualityReport extends PRQAReport<PRQAQualityStatus> {
    
    public PRQAQualityReport(QAR qar) {
        super(qar);
        logger.log(Level.FINEST, "Constructor and super constructor called for class PRQAQualityReport");
        this.parser = new QualityReportParser();
    }

    @Override
    public PRQAQualityStatus completeTask() throws PrqaException {
    	logger.log(Level.FINEST, "Starting execution of method - completeTask");
    	
    	executeQAR();
                
        PRQAQualityStatus status = new PRQAQualityStatus();
        
        status.setLinesOfCode(Integer.parseInt(parser.getResult(QualityReportParser.linesOfCodePattern)));
        status.setNumberOfFileMetrics(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFileMetricsPattern)));
        status.setNumberOfFunctionMetrics(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFunctionsMetricPattern)));
        status.setNumberOfFunctions(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFunctionsPattern)));
        status.setNumberOfSourceFiles(Integer.parseInt(parser.getResult(QualityReportParser.numberOfSourceFilesPattern)));
        status.setTotalNumberOfFiles(Integer.parseInt(parser.getResult(QualityReportParser.totalNumberOfFilesPattern)));
        
        logger.log(Level.FINEST, "Returning status {0}", status);
        
        return status;        
    }
}
