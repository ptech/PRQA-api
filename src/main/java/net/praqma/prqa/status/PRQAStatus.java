
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
import java.util.logging.Logger;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prga.excetions.PrqaReadingException;
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
	protected static final Logger logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    
	/**
	 * 
	 * @param message 
	 */

	@Override
	public void addNotification(String message) {
		logger.finest(String.format("Starting execution of method - addNotification"));
		logger.finest(String.format("Input parameter message type: %s; value: %s", message.getClass(), message));

		notifications.add(message);

		logger.finest(String.format("Ending execution of method - addNotification"));
	}

    @Override
	public HashMap<StatusCategory, Number> getThresholds() {
		logger.finest(String.format("Starting execution of method - getThresholds"));
		logger.finest(String.format("Returning HashMap<StatusCategory, Number> thresholds:"));
		for (Entry<StatusCategory, Number> entry : thresholds.entrySet()) {
			logger.finest(String.format("    StatusCategory: %s, Number: %s", entry.getKey(), entry.getValue()));
		}

		return thresholds;
	}

    @Override
	public void setThresholds(HashMap<StatusCategory,Number> thresholds) {
		logger.finest(String.format("Starting execution of method - setThresholds"));
		logger.finest(String.format("Input parameter thresholds type: %s, values:", thresholds.getClass()));
		for (Entry<StatusCategory, Number> entry : thresholds.entrySet()) {
			logger.finest(String.format("    StatusCategory: %s, Number: %s", entry.getKey(), entry.getValue()));
		}

		this.thresholds = thresholds;

		logger.finest(String.format("Ending execution of method - setThresholds"));
	}

	/**
	 * Gets all associated readouts.
	 */

	@Override
	public HashMap<StatusCategory,Number> getReadouts(StatusCategory... categories) throws PrqaException {
		logger.finest(String.format("Starting execution of method - getReadouts"));
		logger.finest(String.format("Input parameter categories type: %s, values:", categories.getClass()));
		for (StatusCategory category : categories) {
			logger.finest(String.format("    %s", category));
		}
		logger.finest(String.format("Attempting to get readouts..."));

		HashMap<StatusCategory,Number> map = new HashMap<StatusCategory, Number>();
		for (StatusCategory category : categories) {
			try {
				Number readout = getReadout(category);
				map.put(category, readout);
			} catch (PrqaReadingException ex) {
				logger.severe(String.format("Exception thrown type: %s; message: %s", ex.getClass(), ex.getMessage()));

				throw ex;
			}           
		}
		logger.finest(String.format("Successfully got all readouts!"));
		logger.finest(String.format("Returning HashMap<StatusCategory, Number> map:"));
		for (Entry<StatusCategory, Number> entry : map.entrySet()) {
			logger.finest(String.format("    StatusCategory: %s, Number: %s", entry.getKey(), entry.getValue()));
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

	@Override
	public PRQAComparisonMatrix createComparison(ComparisonSettings setting, StatusCategory cat) {
		logger.finest(String.format("Starting execution of method - createComparison(ComparisonSettings setting, StatusCategory cat)"));
		logger.finest(String.format("Input parameter setting type: %s; value: %s", setting.getClass(), setting));
		logger.finest(String.format("Input parameter cat type: %s; value: %s", cat.getClass(), cat));

		PRQAComparisonMatrix comparisonMatrix = new PRQAComparisonMatrix(setting, cat);

		logger.finest(String.format("Returning value: %s", comparisonMatrix));

		return comparisonMatrix;
	}
    
	@Override
	public PRQAComparisonMatrix createComparison(ComparisonSettings setting, StatusCategory cat, PRQAReading lastReading) throws PrqaException {
        if(lastReading == null) {
            return createComparison(setting,cat);
        } else {
            logger.finest(String.format("Starting execution of method - createComparison(ComparisonSettings setting, StatusCategory cat, PRQAReading lastReading)"));
            logger.finest(String.format("Input parameter setting type: %s; value: %s", setting.getClass(), setting));
            logger.finest(String.format("Input parameter cat type: %s; value: %s", cat.getClass(), cat));

            logger.finest(String.format("Input parameter lastReading type: %s; value: %s", lastReading.getClass(), lastReading)); 


            PRQAComparisonMatrix comparisonMatrix = new PRQAComparisonMatrix(setting, cat, lastReading);

            logger.finest(String.format("Returning value: %s", comparisonMatrix));

            return comparisonMatrix;
        }
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
			logger.finest(String.format("Constructor called for class PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category)"));
			logger.finest(String.format("Input parameter setting type: %s; value: %s", setting.getClass(), setting));
			logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));

			this.setting = setting;
			this.category = category;
			this.lastReading = null;

			logger.finest(String.format("Ending execution of constructor - PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category)"));
		}

		public PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading) throws PrqaException {
			logger.finest(String.format("Constructor called for class PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading)"));
			logger.finest(String.format("Input parameter setting type: %s; value: %s", setting.getClass(), setting));
			logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));
			logger.finest(String.format("Input parameter lastReading type: %s; value: %s", lastReading.getClass(), lastReading));

			this.setting = setting;
			this.category = category;
			this.lastReading = lastReading;
			this.compareValue = lastReading != null ? lastReading.getReadout(category) : null;

			logger.finest(String.format("Ending execution of constructor - PRQAComparisonMatrix(ComparisonSettings setting, StatusCategory category, PRQAReading lastReading)"));

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
		public boolean compareIsEqualOrLower(Number number) throws PrqaException {
			logger.finest(String.format("Starting execution of method - compareIsEqualOrLower"));
			logger.finest(String.format("Input parameter number type: %s; value: %s", number.getClass(), number));

			boolean result = compare(number, true);

			logger.finest(String.format("Returning value: %s", result));

			return result;
		}

		public boolean compareIsEqualOrHigher(Number number) throws PrqaException {
			logger.finest(String.format("Starting execution of method - compareIsEqualOrHigher"));
			logger.finest(String.format("Input parameter number type: %s; value: %s", number.getClass(), number));

			boolean result = compare(number, false);

			logger.finest(String.format("Returning value: %s", result));

			return result;
		}

		public Number getCompareValue() {
			logger.finest(String.format("Starting execution of method - getCompareValue"));
			logger.finest(String.format("Returning value: %s", compareValue));

			return compareValue;
		}

		private boolean compare(Number number, boolean less) throws PrqaException {
			logger.finest(String.format("Starting execution of method - compare"));
			logger.finest(String.format("Input parameter number type: %s; value: %s", number.getClass(), number));
			logger.finest(String.format("Input parameter less type: %s; value: %s", "boolean", less));

			logger.finest(String.format("Determining value of setting: %s", setting));
			switch (setting) {
                case Threshold:
                    logger.finest(String.format("setting is Threshold"));

                    this.compareValue = number;
                    if(this.compareValue == null) {
                        logger.finest(String.format("compareValue is null, returning true"));

                        return true;
                    }
                    if(less && getReadout(category).doubleValue() <= number.doubleValue()) {
                        logger.finest(String.format("less is true and category readout less than number, returning true"));

                        return true;  
                    } else if(!less && getReadout(category).doubleValue() >= number.doubleValue()) {
                        logger.finest(String.format("less is false and category readout greater than number, returning true"));

                        return true;
                    } else {
                        logger.finest(String.format("less is false and category readout less than number OR less is true and category readout greater than number, returning false"));

                        return false;
                    }
                case Improvement:
                    logger.finest(String.format("setting is Improvement"));
                    if(lastReading == null) {
                        logger.finest(String.format("lastReading is null, returning true."));	

                        return true;
                    }
                    if(less && getReadout(category).doubleValue() <= lastReading.getReadout(category).doubleValue()) {
                        logger.finest(String.format("less is true and category readout less than lastReading readout, returning true"));

                        return true;  
                    } else if(!less && getReadout(category).doubleValue() >= lastReading.getReadout(category).doubleValue()) {
                        logger.finest(String.format("less is false and category readout greater than lastReading readout, returning true"));

                        return true;
                    } else {
                        logger.finest(String.format("less is false and category readout less than lastReading readout OR less is true and category readout greater than lastReading readout, returning false"));

                        return false;
                    }                    
                case None:
                default:
                    logger.finest(String.format("setting is None or default, returning true."));
                    return true;

			}
		}

	}
}
