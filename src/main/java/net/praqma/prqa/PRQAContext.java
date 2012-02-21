/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

/**
 *
 * @author jes
 */
public class PRQAContext {

    public ProductInterface context;

    /**
     * 
     * TODO: For review where should we put these enum constants used as dropdown constants?
     * @author man
     */
    public enum ComparisonSettings {
        None, Threshold, Improvement  
    }
    
    public enum AnalyseTypes {
        QAC, QACpp, Java
    }

    public enum ReportTypes {
        QAV, QAR
    }

    public enum QARReportType {
        Compliance, Quality, CodeReview, Supression
    }

    public PRQAContext(ProductInterface product) {
        Cmd.run("");
        this.context = product;
    }
}
