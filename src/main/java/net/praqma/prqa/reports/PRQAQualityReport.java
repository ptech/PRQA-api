/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.List;
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
public class PRQAQualityReport extends PRQAReport<PRQAQualityStatus,String> {
    
    public PRQAQualityReport(QAR qar) {
        this.qar = qar;
        this.parser = new QualityReportParser();
    }

    @Override
    public PRQAQualityStatus completeTask(String parameter) throws PrqaException {
        cmdResult = null;
        try {
            cmdResult = qar.execute();
        } catch (AbnormalProcessTerminationException ex) {
            throw new PrqaException.PrqaCommandLineException(qar,ex);            
        } catch (CommandLineException cle) {      
            throw new PrqaException.PrqaCommandLineException(qar,cle);            
        }
        
        //Fill in the results:
        PRQAQualityStatus status = new PRQAQualityStatus();
        
        List<String> lines = parser.parse(parameter, QualityReportParser.linesOfCodePattern);
        List<String> fmetrics = parser.parse(parameter, QualityReportParser.numberOfFileMetricsPattern);
        List<String> funcmetrics = parser.parse(parameter, QualityReportParser.numberOfFunctionsMetricPattern);
        List<String> nfunctions = parser.parse(parameter, QualityReportParser.numberOfFunctionsPattern);
        List<String> nsfiles = parser.parse(parameter, QualityReportParser.numberOfSourceFilesPattern);
        List<String> numfiles = parser.parse(parameter, QualityReportParser.totalNumberOfFilesPattern);
        
        status.setLinesOfCode(Integer.parseInt(parser.getFirstResult(lines)));
        status.setNumberOfFileMetrics(Integer.parseInt(parser.getFirstResult(fmetrics)));
        status.setNumberOfFunctionMetrics(Integer.parseInt(parser.getFirstResult(funcmetrics)));
        status.setNumberOfFunctions(Integer.parseInt(parser.getFirstResult(nfunctions)));
        status.setNumberOfSourceFiles(Integer.parseInt(parser.getFirstResult(nsfiles)));
        status.setTotalNumberOfFiles(Integer.parseInt(parser.getFirstResult(numfiles)));
        
        return status;        
    }

    @Override
    public PRQAQualityStatus completeTask() throws PrqaException {
        return completeTask(getFullReportPath());
    }
}
