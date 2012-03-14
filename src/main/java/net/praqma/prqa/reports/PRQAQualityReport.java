/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.PRQAStatus;

/**
 *
 * @author Praqma
 */
public class PRQAQualityReport extends PRQAReport {
    public static String getNamingTemplate() {
        return PRQAContext.QARReportType.Quality.toString() + " "+ PRQAReport.XHTML_REPORT_EXTENSION;
    }

    @Override
    public String getFullReportPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PRQAStatus completeTask(Object parameter) throws PrqaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PRQAStatus completeTask() throws PrqaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
