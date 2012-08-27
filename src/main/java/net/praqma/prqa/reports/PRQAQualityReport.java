/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAQualityStatus;

/**
 *
 * @author Praqma
 */
public class PRQAQualityReport extends PRQAReport<PRQAQualityStatus> {
    
    public PRQAQualityReport() {
        this.parser = new QualityReportParser();
    }
    
    public PRQAQualityReport(QAR qar) {
        
        super(qar);
        logger.finest(String.format("Constructor and super constructor called for class PRQAQualityReport"));
        
        this.parser = new QualityReportParser();
        
        logger.finest(String.format("Ending execution of constructor - PRQAQualityReport"));
    }

    @Override
    public PRQAQualityStatus generateReport() throws PrqaException {
    	logger.finest(String.format("Starting execution of method - generateReport"));
    	
    	executeQAR();
                
        PRQAQualityStatus status = new PRQAQualityStatus();
        
        status.setLinesOfCode(Integer.parseInt(parser.getResult(QualityReportParser.linesOfCodePattern)));
        status.setNumberOfFileMetrics(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFileMetricsPattern)));
        status.setNumberOfFunctionMetrics(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFunctionsMetricPattern)));
        status.setNumberOfFunctions(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFunctionsPattern)));
        status.setNumberOfSourceFiles(Integer.parseInt(parser.getResult(QualityReportParser.numberOfSourceFilesPattern)));
        status.setTotalNumberOfFiles(Integer.parseInt(parser.getResult(QualityReportParser.totalNumberOfFilesPattern)));
        
        logger.finest(String.format("Returning value: %s", status));
        
        return status;        
    }
    
    @Override
    public String getDisplayName() {
        return "Quality";
    }
}

