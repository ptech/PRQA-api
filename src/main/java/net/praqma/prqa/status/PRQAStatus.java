/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
public abstract class PRQAStatus implements PRQAReading, Serializable {
    
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
		
    public HashMap<StatusCategory, Number> getThresholds() {
    	logger.log(Level.FINEST, "Starting execution of method - getThresholds");
    	logger.log(Level.FINEST, "Returning HashMap<StatusCategory, Number> thresholds:");
    	for (Entry<StatusCategory, Number> entry : thresholds.entrySet()) {
    		logger.log(Level.FINEST, "    StatusCategory: {0}, Number: {1}", new Object[]{entry.getKey(), entry.getValue()});
    	}
    	
        return thresholds;
    }
    
    public void setThresholds(HashMap<StatusCategory,Number> thresholds) {
    	logger.log(Level.FINEST, "Starting execution of method - setThresholds");
    	logger.log(Level.FINEST, "Input parameter thresholds type: {0}, values:", thresholds.getClass());
    	for (Entry<StatusCategory, Number> entry : thresholds.entrySet()) {
    		logger.log(Level.FINEST, "    StatusCategory: {0}, Number: {1}", new Object[]{entry.getKey(), entry.getValue()});
    	}
    	
        this.thresholds = thresholds;
        
        logger.log(Level.FINEST, "Ending execution of method - setThresholds");
    }
    
    /**
     * Gets all associated readouts.
     */
    
    @Override
    public HashMap<StatusCategory,Number> getReadouts(StatusCategory... categories) throws PrqaException.PrqaReadingException {
    	logger.log(Level.FINEST, "Starting execution of method - getReadouts");
    	logger.log(Level.FINEST, "Input parameter categories type: {0}, values:", categories.getClass());
    	
    	for (StatusCategory category : categories) {
        	logger.log(Level.FINEST, "    {0}", category);
    	}
    	
    	logger.log(Level.FINEST, "Attempting to get readouts...");
    	
        HashMap<StatusCategory,Number> map = new HashMap<StatusCategory, Number>();
        for (StatusCategory category : categories) {
            try {
                Number readout = getReadout(category);
                map.put(category, readout);
            } catch (PrqaException.PrqaReadingException ex) {
            	logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{ex.getClass(), ex.getMessage()});
            	
                throw ex;
            }           
        }
        logger.log(Level.FINEST, "Successfully got all readouts!");
        logger.log(Level.FINEST, "Returning HashMap<StatusCategory, Number> map:");
    	for (Entry<StatusCategory, Number> entry : map.entrySet()) {
    		logger.log(Level.FINEST, "    StatusCategory: {0}, Number: {1}", new Object[]{entry.getKey(), entry.getValue()});
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
    	logger.log(Level.FINEST, "Starting execution of method - createComparison(ComparisonSettings setting, StatusCategory cat)");
    	logger.log(Level.FINEST, "Input parameter setting type: {0}; value: {1}", new Object[]{setting.getClass(), setting});
    	logger.log(Level.FINEST, "Input parameter cat type: {0}; value: {1}", new Object[]{cat.getClass(), cat});
    	
    	PRQAComparisonMatrix comparisonMatrix = new PRQAComparisonMatrix(setting, cat);
    	
    	logger.log(Level.FINEST, "Returning comparisonMatrix: {0}", comparisonMatrix);
    	
        return comparisonMatrix;
    }
    
    @Override
    public PRQAComparisonMatrix createComparison(ComparisonSettings setting, StatusCategory cat, PRQAReading lastReading) throws PrqaException.PrqaReadingException {
    	logger.log(Level.FINEST, "Starting execution of method - createComparison(ComparisonSettings setting, StatusCategory cat, PRQAReading lastReading)");
    	logger.log(Level.FINEST, "Input parameter setting type: {0}; value: {1}", new Object[]{setting.getClass(), setting});
    	logger.log(Level.FINEST, "Input parameter cat type: {0}; value: {1}", new Object[]{cat.getClass(), cat});
    	logger.log(Level.FINEST, "Input parameter lastReading type: {0}; value: {1}", new Object[]{lastReading.getClass(), lastReading});
    	
    	PRQAComparisonMatrix comparisonMatrix = new PRQAComparisonMatrix(setting, cat, lastReading);
    	
    	logger.log(Level.FINEST, "Returning comparisonMatrix: {0}", comparisonMatrix);
    	
        return comparisonMatrix;
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
        	logger.log(Level.FINEST, "Constructor called for class PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category)");
        	logger.log(Level.FINEST, "Input parameter setting type: {0}; value: {1}", new Object[]{setting.getClass(), setting});
        	logger.log(Level.FINEST, "Input parameter category type: {0}; value: {1}", new Object[]{category.getClass(), category});
            this.setting = setting;
            this.category = category;
            this.lastReading = null;
            logger.log(Level.FINEST, "Ending execution of constructor - PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category)");
        }
        
        public PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading) throws PrqaException.PrqaReadingException {
        	logger.log(Level.FINEST, "Constructor called for class PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading)");
        	logger.log(Level.FINEST, "Input parameter setting type: {0}; value: {1}", new Object[]{setting.getClass(), setting});
        	logger.log(Level.FINEST, "Input parameter category type: {0}; value: {1}", new Object[]{category.getClass(), category});
        	logger.log(Level.FINEST, "Input parameter lastReading type: {0}; value: {1}", new Object[]{lastReading.getClass(), lastReading});
            this.setting = setting;
            this.category = category;
            this.lastReading = lastReading;
            this.compareValue = lastReading != null ? lastReading.getReadout(category) : null;
            logger.log(Level.FINEST, "Ending execution of constructor - PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading)");
            
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
        	logger.log(Level.FINEST, "Starting execution of method - compareIsEqualOrLower");
            return compare(number, true);
        }
        
        public boolean compareIsEqualOrHigher(Number number) throws PrqaException.PrqaReadingException {
        	logger.log(Level.FINEST, "Starting execution of method - compareIsEqualOrHigher");
        	logger.log(Level.FINEST, "Input parameter number type: {0}; value: {1}", new Object[]{number.getClass(), number});
        	boolean result = compare(number, false);
        	logger.log(Level.FINEST, "Returning result: {0}", result);
            return result;
        }
        
        public Number getCompareValue() {
        	logger.log(Level.FINEST, "Starting execution of method - getCompareValue");
        	logger.log(Level.FINEST, "Returning compareValue: {0}", compareValue);
            return compareValue;
        }
        
        private boolean compare(Number number, boolean less) throws PrqaException.PrqaReadingException {
        	logger.log(Level.FINEST, "Starting execution of method - compare");
        	logger.log(Level.FINEST, "Input parameter number type: {0}; value: {1}", new Object[]{number.getClass(), number});
        	logger.log(Level.FINEST, "Input parameter less type: {0}; value: {1}", new Object[]{"boolean", less});
        	
        	logger.log(Level.FINEST, "Determining value of setting: {0}", setting);
            switch (setting) {
                case Threshold:
                	logger.log(Level.FINEST, "setting is Threshold");
                    this.compareValue = number;
                    if(this.compareValue == null) {
                    	logger.log(Level.FINEST, "compareValue is null, returning true");
                        return true;
                    }
                    if(less && getReadout(category).doubleValue() <= number.doubleValue()) {
                    	logger.log(Level.FINEST, "less is true and category readout less than number, returning true");
                        return true;  
                    } else if(!less && getReadout(category).doubleValue() >= number.doubleValue()) {
                    	logger.log(Level.FINEST, "less is false and category readout greater than number, returning true");
                        return true;
                    } else {
                    	logger.log(Level.FINEST, "less is false and category readout less than number OR less is true and category readout greater than number, returning false");
                        return false;
                    }
                case Improvement:
                	logger.log(Level.FINEST, "setting is Improvement");
                    if(lastReading == null) {
                    	logger.log(Level.FINEST, "lastReading is null, returning true.");	
                        return true;
                    }
                    if(less && getReadout(category).doubleValue() <= lastReading.getReadout(category).doubleValue()) {
                    	logger.log(Level.FINEST, "less is true and category readout less than lastReading readout, returning true");
                        return true;  
                    } else if(!less && getReadout(category).doubleValue() >= lastReading.getReadout(category).doubleValue()) {
                    	logger.log(Level.FINEST, "less is false and category readout greater than lastReading readout, returning true");
                        return true;
                    } else {
                    	logger.log(Level.FINEST, "less is false and category readout less than lastReading readout OR less is true and category readout greater than lastReading readout, returning false");
                        return false;
                    }                    
                case None:
                default:
                	logger.log(Level.FINEST, "setting is None or default, returning true.");
                    return true;
                    
            }
        }
        
    }
}
