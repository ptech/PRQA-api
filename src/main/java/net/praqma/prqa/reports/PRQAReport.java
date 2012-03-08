/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import net.praqma.prqa.parsers.ReportHtmlParser;

/**
 *
 * @author Praqma
 */
public abstract class PRQAReport {
    
    protected ReportHtmlParser parser;
    
    public static String XHTML = "xhtml";
    public static String XML = "xml";
    public static String HTML = "html";
    
    public static String XHTML_REPORT_EXTENSION = "Report."+PRQAReport.XHTML;
    public static String XML_REPORT_EXTENSION = "Report."+PRQAReport.XML;
    public static String HTML_REPORT_EXTENSION = "Report."+PRQAReport.HTML;
    
    public void setParser(ReportHtmlParser parser) {
        this.parser = parser;
    }
    
    public ReportHtmlParser getParser() {
        return this.parser;
    }    
}
