/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.Serializable;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.PRQAStatus;
import net.praqma.prqa.parsers.ReportHtmlParser;
import net.praqma.prqa.products.QAR;
import net.praqma.util.execute.CmdResult;

/**
 *Class defining a report. 
 * 
 * @author Praqma
 */
public abstract class PRQAReport<T extends PRQAStatus,K> implements Serializable {
    
    protected ReportHtmlParser parser;
    protected QAR qar;
    //Result
    protected CmdResult cmdResult;
    
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
    
    public QAR getQar() {
        return this.qar;
    }
    
    public void setQar(QAR qar) {
        this.qar = qar;
    }
    
    public CmdResult getCmdResult() {
        return this.cmdResult;
    }
    
    public void setCmdResult(CmdResult res) {
        this.cmdResult = res;
    }
    
    public abstract String getFullReportPath();
    public abstract T completeTask(K parameter) throws PrqaException;
    public abstract <T> T completeTask() throws PrqaException;
}
