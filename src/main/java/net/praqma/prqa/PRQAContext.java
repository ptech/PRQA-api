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
