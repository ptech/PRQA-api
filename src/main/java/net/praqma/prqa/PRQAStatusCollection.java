package net.praqma.prqa;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import net.praqma.prqa.PRQAStatus.ComplianceCategory;

/**
 *
 * @author Praqma
 */
public class PRQAComplianceStatusCollection extends ArrayList<PRQAReading>  {
    
    private Map<ComplianceCategory,Number> overrideMinimum = new EnumMap<ComplianceCategory, Number>(ComplianceCategory.class);
    private Map<ComplianceCategory,Number> overrideMaximum = new EnumMap<ComplianceCategory, Number>(ComplianceCategory.class);
    
    public PRQAComplianceStatusCollection() { 
        this(new ArrayList<PRQAComplianceStatus>());
    }
    
    public PRQAComplianceStatusCollection(ArrayList<PRQAComplianceStatus> collection) {
        this.addAll(collection);
    }
    
    public PRQAComplianceStatusCollection(PRQAComplianceStatusCollection collection) {
        this.addAll(collection);
    }
 
    /***
     * Implemented a collection method to gather extremities from a given set of collected compliance statistics.
     * @param category
     * @return 
     */
    public Number getMax(ComplianceCategory category) {
        
        if(getOverriddenMax(category) != null) {
            return getOverriddenMax(category);
        }
        
        int max = Integer.MIN_VALUE;
        int tmp = 0;
        
        for(PRQAReading s : this) {                    
            tmp = s.getReadout(category) == null ? 0 : s.getReadout(category).intValue();
            if(tmp >= max)
                max = tmp;
        }
        return max;
    }
     /***
     * Implemented a collection method to gather extremities from a given set of collected compliance statistics.
     * @param category
     * @return a number indicating the smallest given observation for the specified category.
     */
    public Number getMin(ComplianceCategory category) {
        
        if(getOverriddenMin(category) != null) {
            return getOverriddenMin(category);
        }
        
        int min = Integer.MAX_VALUE;
        int tmp = 0;
        for(PRQAReading s : this) {
            tmp = s.getReadout(category) == null ? 0 : s.getReadout(category).intValue();
            if(tmp <= min)
                min = tmp;
        }
        return min;
    }
    
    /**
     * Implemented methods to override the min and max values, these are used in the graphing part of the project. You override specific performance metrics.
     * @param category
     * @param value 
     */
    public void overrideMin(ComplianceCategory category, Number value) {
        overrideMinimum.put(category, value);
    }
    
    public void overrideMax(ComplianceCategory category, Number value) {
        overrideMaximum.put(category, value);
    }
    
    public Number getOverriddenMax(ComplianceCategory category) {
        if(overrideMaximum.containsKey(category)) {
            return overrideMaximum.get(category);
        }
        return null;
    }
    
    public Number getOverriddenMin(ComplianceCategory category) {
        if(overrideMinimum.containsKey(category)) {
            return overrideMinimum.get(category);
        }
        return null;
    }
    
    public void clearOverrides() {
        overrideMaximum.clear();
        overrideMinimum.clear();
    }
}
