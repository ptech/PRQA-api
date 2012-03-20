/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.PRQAStatus;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAQualityStatus;

/**
 *
 * @author Praqma
 */
public class PRQAQualityReport extends PRQAReport<PRQAQualityStatus,String> {
    
    public PRQAQualityReport(QAR qar) {
        this.qar = qar;
        this.parser = new QualityReportParser();
    }
    
    public static String getNamingTemplate() {
        return PRQAContext.QARReportType.Quality.toString() + " "+ PRQAReport.XHTML_REPORT_EXTENSION;
    }
    
    @Override
    public String getFullReportPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PRQAQualityStatus completeTask(String parameter) throws PrqaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PRQAQualityStatus completeTask() throws PrqaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
