package net.praqma.prqa;

/**
 *
 * @author jes
 */
public class PRQAContext {
    /**
     * TODO: For review where should we put these enum constants used as dropdown constants?
     * @author man
     */
    public enum ComparisonSettings {
        None, Threshold, Improvement;// Greaterorequaltolast, Lessorequaltolast;

        @Override
        public String toString() {
            switch(this) {
//                case Greaterorequaltolast:
//                    return "Greater or equal to last";
//                case Lessorequaltolast:
//                    return "Less or equal to last";
                default:
                    return this.name();
            }
        }
    }
    
    public enum AnalysisTools {
        QAC, QACpp, Java
    }

    public enum ReportingTools {
        QAV, QAR
    }

    public enum QARReportType {
        Compliance, Quality, CodeReview, Suppression;

        @Override
        public String toString() {
            if(this.equals(CodeReview)) {
                return "Code Review";
            } else {
                return this.name();
            }            
        }
    }
}
