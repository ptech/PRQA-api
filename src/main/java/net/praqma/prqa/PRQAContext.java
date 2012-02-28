package net.praqma.prqa;

/**
 *
 * @author jes
 */
public class PRQAContext {
    /**
     * 
     * TODO: For review where should we put these enum constants used as dropdown constants?
     * @author man
     */
    public enum ComparisonSettings {
        None, Threshold, Improvement  
    }
    
    public enum AnalysisTools {
        QAC, QACpp, Java
    }

    public enum ReportingTools {
        QAV, QAR
    }

    public enum QARReportType {
        Compliance, Quality, CodeReview, Supression
    }
}
