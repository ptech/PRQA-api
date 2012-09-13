package net.praqma.prqa;

import java.util.EnumSet;
import net.praqma.prqa.products.QAR;

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
                default:
                    return this.name();
            }
        }
    }
    
    public enum AnalysisTools {
        QAC, QACPP
    }

    public enum ReportingTools {
        QAV, QAR
    }

    public enum QARReportType {
        Compliance, CodeReview, Suppression;

        @Override
        public String toString() {
            if(this.equals(CodeReview)) {
                return "Code Review";
            } else {
                return this.name();
            }            
        }
        
        public static final EnumSet<QARReportType> OPTIONAL_TYPES = EnumSet.of(CodeReview, Suppression);
        
        public static final EnumSet<QARReportType> REQUIRED_TYPES = EnumSet.of(Compliance);
    }
    
    
}
