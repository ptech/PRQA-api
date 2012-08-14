/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAQualityStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CommandLineException;

/**
 * @author Praqma
 */
public class PRQAQualityReport extends PRQAReport<PRQAQualityStatus> {
    
    public PRQAQualityReport(QAR qar) {
        this.reportTool = qar;
        this.parser = new QualityReportParser();
    }
        
    public PRQAQualityReport() {
        this.parser = new QualityReportParser();
    }

    @Override
    public PRQAQualityStatus generateReport() throws PrqaException {
        parser.setFullReportPath(this.getFullReportPath());
        cmdResult = null;
        try {
            cmdResult = reportTool.generateReportFiles();
        } catch (AbnormalProcessTerminationException ex) {
            throw new PrqaException.PrqaCommandLineException(reportTool,ex);            
        } catch (CommandLineException cle) {      
            throw new PrqaException.PrqaCommandLineException(reportTool,cle);            
        }
                
        PRQAQualityStatus status = new PRQAQualityStatus();
        
        status.setLinesOfCode(Integer.parseInt(parser.getResult(QualityReportParser.linesOfCodePattern)));
        status.setNumberOfFileMetrics(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFileMetricsPattern)));
        status.setNumberOfFunctionMetrics(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFunctionsMetricPattern)));
        status.setNumberOfFunctions(Integer.parseInt(parser.getResult(QualityReportParser.numberOfFunctionsPattern)));
        status.setNumberOfSourceFiles(Integer.parseInt(parser.getResult(QualityReportParser.numberOfSourceFilesPattern)));
        status.setTotalNumberOfFiles(Integer.parseInt(parser.getResult(QualityReportParser.totalNumberOfFilesPattern)));
        
        
        
        return status;        
    }

    @Override
    public String getDisplayName() {
        return "Quality";
    }
}
