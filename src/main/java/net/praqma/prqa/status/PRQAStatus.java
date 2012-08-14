/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.PRQAContext.ComparisonSettings;
import net.praqma.prqa.PRQAReading;
import net.praqma.prqa.logging.Config;

/**
 * Base class for all status objects.
 * 
 * Each object holds a list of notifactions associated with that status.
 * 
 * And a list of disbaled categories...should be removed in final iteration.
 * 
 * @author Praqma
 */
public abstract class PRQAStatus implements PRQAReading,Serializable {
    
    protected List<String> notifications = new ArrayList<String>();
    protected HashMap<StatusCategory,Number> thresholds;
    protected transient final Logger logger;
    
    public PRQAStatus() {
    	logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }
    
     /**
     * 
     * @param message 
     */
    
    @Override
    public void addNotification(String message) {
    	logger.log(Level.FINEST, "Starting execution of method - addNotification");
    	logger.log(Level.FINEST, "Input parameter message type: {0}; value: {1}", new Object[]{message.getClass(), message});
        
    	notifications.add(message);
        
        logger.log(Level.FINEST, "Ending execution of method - setFullReportPath");
    }
		
    public HashMap<StatusCategory,Number> getThresholds() {
        return thresholds;
    }
    
    public void setThresholds(HashMap<StatusCategory,Number> thresholds) {
        this.thresholds = thresholds;
    }
    
    /**
     * Gets all associated readouts.
     */
    
    @Override
    public HashMap<StatusCategory,Number> getReadouts(StatusCategory... categories) throws PrqaException.PrqaReadingException {
        HashMap<StatusCategory,Number> map = new HashMap<StatusCategory, Number>();
        for (StatusCategory category : categories) {
            try {
                Number readout = getReadout(category);
                map.put(category, getReadout(category));
            } catch (PrqaException.PrqaReadingException ex) {
                throw ex;
            }           
        }    
        return map;
    }
    
     
    /**
     * Tests whether the result contains valid values. in some cases we could end up in situation where an "empty" result would be created.
     * @return 
     */
    public abstract boolean isValid();
    
    /**
     * Returns a table representation of the the toString method.
     * @return 
     */
    public abstract String toHtml();
    
    //EXPERIMENTS BELOW
    @Override
    public PRQAComparisonMatrix createComparison(ComparisonSettings setting, StatusCategory cat) {
        return new PRQAComparisonMatrix(setting, cat);
    }
    
    @Override
    public PRQAComparisonMatrix createComparison(ComparisonSettings setting, StatusCategory cat, PRQAReading lastReading) throws PrqaException.PrqaReadingException {
        return new PRQAComparisonMatrix(setting, cat, lastReading);
    }
    
    /**
     * Small helper class for creating a comparison. Given a mode of operation (None, Threshold, Improvement) compare a given set of readings. 
     * 
     * 
     */
    public class PRQAComparisonMatrix {
        private transient ComparisonSettings setting;
        private transient StatusCategory category;
        private transient Number compareValue;
        private transient PRQAReading lastReading;
       
        
        public PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category) {
            this.setting = setting;
            this.category = category;
            this.lastReading = null;
        }
        
        public PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading) throws PrqaException.PrqaReadingException {
            this.setting = setting;
            this.category = category;
            this.lastReading = lastReading;
            this.compareValue = lastReading != null ? lastReading.getReadout(category) : null;
        }
        
        
        /**
         * Small piece of logic. Given a setting for comparison, find out if the readings are better or worse than the user expected. 
         * 
         * The comparison always have a value for 
         * @param number
         * @param less
         * @return
         * @throws net.praqma.jenkins.plugin.prqa.PrqaException.PrqaReadingException 
         */
        public boolean compareIsEqualOrLower(Number number) throws PrqaException.PrqaReadingException {
            return compare(number, true);
        }
        
        public boolean compareIsEqualOrHigher(Number number) throws PrqaException.PrqaReadingException {
            return compare(number, false);
        }
        
        public Number getCompareValue() {
            return compareValue;
        }
        
        private boolean compare(Number number, boolean less) throws PrqaException.PrqaReadingException {
            switch (setting) {
                case Threshold:
                    this.compareValue = number;
                    if(this.compareValue == null)
                        return true;                  
                    if(less && getReadout(category).doubleValue() <= number.doubleValue()) {
                        return true;  
                    } else if(!less && getReadout(category).doubleValue() >= number.doubleValue()) {
                        return true;
                    } else {
                        return false;
                    }
                case Improvement:
                    if(lastReading == null) {
                        return true;
                    }
                    if(less && getReadout(category).doubleValue() <= lastReading.getReadout(category).doubleValue()) {
                        return true;  
                    } else if(!less && getReadout(category).doubleValue() >= lastReading.getReadout(category).doubleValue()) {
                        return true;
                    } else {
                        return false;
                    }                    
                case None:
                default:
                    return true;
                    
            }
        }
        
    }
}
