/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.util.List;
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
public class PRQASuppressionReport extends PRQAReport<PRQASuppressionStatus,String> {
    
    public PRQASuppressionReport(QAR qar) {
        this.qar = qar;
    }

    @Override
    public PRQASuppressionStatus completeTask(String parameter) throws PrqaException {
        cmdResult = null;
        try {
            cmdResult = qar.execute();
        } catch (AbnormalProcessTerminationException ex) {
            throw new PrqaException.PrqaCommandLineException(qar,ex);            
        } catch (CommandLineException cle) {      
            throw new PrqaException.PrqaCommandLineException(qar,cle);            
        }
        
        PRQASuppressionStatus status = new PRQASuppressionStatus();
        
        List<String> linesOfCode = parser.parse(getFullReportPath(), SuppressionReportParser.linesOfCodePattern);
        List<String> numberOfFiles = parser.parse(getFullReportPath(), SuppressionReportParser.numberOfFilesPattern);
        List<String> suppressedMsgs = parser.parse(getFullReportPath(), SuppressionReportParser.numberOfMessagesSuppressedPattern);
        List<String> pctSuppressedMsgs = parser.parse(getFullReportPath(), SuppressionReportParser.percentageOfMsgSuppressedPattern);
        List<String> uniMsgSuppressed = parser.parse(getFullReportPath(), SuppressionReportParser.uniqueMessagesSuppressedPattern);
        
        status.setLinesOfCode(Integer.parseInt(parser.getFirstResult(linesOfCode)));
        status.setNumberOfFiles(Integer.parseInt(parser.getFirstResult(numberOfFiles)));
        status.setMsgsSuppressed(Integer.parseInt(parser.getFirstResult(suppressedMsgs)));
        status.setPctMsgsSuppressed(Double.parseDouble(parser.getFirstResult(pctSuppressedMsgs)));
        status.setUniqueMsgsSuppressed(Integer.parseInt(parser.getFirstResult(uniMsgSuppressed)));
        
        return status;
    }

    @Override
    public PRQASuppressionStatus completeTask() throws PrqaException {
        return completeTask(getFullReportPath()); 
    }
}
