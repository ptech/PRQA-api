package net.praqma.prqa;

import net.praqma.prqa.status.PRQAComplianceStatus;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import net.praqma.prqa.PRQAStatus.StatusCategory;

/**
 *
 * @author Praqma
 */
public class PRQAStatusCollection extends ArrayList<PRQAReading>  {
    
    private Map<StatusCategory,Number> overrideMinimum = new EnumMap<StatusCategory, Number>(StatusCategory.class);
    private Map<StatusCategory,Number> overrideMaximum = new EnumMap<StatusCategory, Number>(StatusCategory.class);
    
    public PRQAStatusCollection() { 
        this(new ArrayList<PRQAReading>());
    }
    
    public PRQAStatusCollection(ArrayList<PRQAReading> collection) {
        this.addAll(collection);
    }
    
    public PRQAStatusCollection(PRQAStatusCollection collection) {
        this.addAll(collection);
    }
 
    /***
     * Implemented a collection method to gather extremities from a given set of collected compliance statistics.
     * @param category
     * @return 
     */
    public Number getMax(StatusCategory category) {
        
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
    public Number getMin(StatusCategory category) {
        
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
    public void overrideMin(StatusCategory category, Number value) {
        overrideMinimum.put(category, value);
    }
    
    public void overrideMax(StatusCategory category, Number value) {
        overrideMaximum.put(category, value);
    }
    
    public Number getOverriddenMax(StatusCategory category) {
        if(overrideMaximum.containsKey(category)) {
            return overrideMaximum.get(category);
        }
        return null;
    }
    
    public Number getOverriddenMin(StatusCategory category) {
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
