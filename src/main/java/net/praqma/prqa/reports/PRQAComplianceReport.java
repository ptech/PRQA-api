/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.prqa.PRQAComplianceStatus;
import net.praqma.prqa.PRQATask;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.parsers.ReportHtmlParser;

/**
 *
 * @author Praqma
 */
public class PRQAComplianceReport implements PRQATask<PRQAComplianceStatus, Object> {
   private ReportHtmlParser parser;
   
   public PRQAComplianceReport() {
       this.parser = new ComplianceReportHtmlParser();
   }
   
   public PRQAComplianceReport(ReportHtmlParser parser) {
       this.parser = parser;
   }
    /**
     * @return the parser
     */
    public ReportHtmlParser getParser() {
        return parser;
    }

    /**
     * @param parser the parser to set
     */
    public void setParser(ReportHtmlParser parser) {
        this.parser = parser;
    }

    public PRQAComplianceStatus completeTask(Object parameter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
