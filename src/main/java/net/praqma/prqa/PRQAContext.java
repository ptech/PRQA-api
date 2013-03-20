package net.praqma.prqa;

import java.util.EnumSet;

/**
 *
 * @author jes
 */
public class PRQAContext {

    public enum ComparisonSettings {
        None, Threshold, Improvement;

        @Override
        public String toString() {
            switch(this) {
                default:
                    return this.name();
            }
        }
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

