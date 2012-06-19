/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.jenkins.plugin.prqa.PrqaException;
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
        this.qar = qar;
        this.parser = new SuppressionReportParser();
    }

    @Override
    public PRQASuppressionStatus completeTask() throws PrqaException {
        parser.setFullReportPath(this.getFullReportPath());
        cmdResult = null;
        try {
            cmdResult = qar.execute();
        } catch (AbnormalProcessTerminationException ex) {
            throw new PrqaException.PrqaCommandLineException(qar,ex);            
        } catch (CommandLineException cle) {      
            throw new PrqaException.PrqaCommandLineException(qar,cle);            
        }
        
        
        PRQASuppressionStatus status = new PRQASuppressionStatus();
        status.setLinesOfCode(Integer.parseInt(parser.getResult(SuppressionReportParser.linesOfCodePattern)));
        status.setNumberOfFiles(Integer.parseInt(parser.getResult(SuppressionReportParser.numberOfFilesPattern)));
        status.setMsgsSuppressed(Integer.parseInt(parser.getResult(SuppressionReportParser.numberOfMessagesSuppressedPattern)));
        status.setPctMsgsSuppressed(Double.parseDouble(parser.getResult(SuppressionReportParser.percentageOfMsgSuppressedPattern)));
        status.setUniqueMsgsSuppressed(Integer.parseInt(parser.getResult(SuppressionReportParser.uniqueMessagesSuppressedPattern)));        
        return status;
    }
}
