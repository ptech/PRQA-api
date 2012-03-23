/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.parsers.CodeReviewReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQACodeReviewStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLineException;

/**
 *
 * @author Praqma
 */
public class PRQACodeReviewReport extends PRQAReport<PRQACodeReviewStatus,String> {

    public PRQACodeReviewReport(QAR qar) {
        this.qar = qar;
        this.parser = new CodeReviewReportParser();
    }

    @Override
    public PRQACodeReviewStatus completeTask(String parameter) throws PrqaException {
        cmdResult = null;
        try 
        {
             cmdResult = qar.execute();
        } catch (AbnormalProcessTerminationException ex) {
            throw new PrqaException.PrqaCommandLineException(qar,ex);            
        } catch (CommandLineException cle) {      
            throw new PrqaException.PrqaCommandLineException(qar,cle);            
        }
        PRQACodeReviewStatus status = new PRQACodeReviewStatus();
        return status;
    }

    @Override
    public PRQACodeReviewStatus completeTask() throws PrqaException {
        return completeTask(getFullReportPath());
    }
}
