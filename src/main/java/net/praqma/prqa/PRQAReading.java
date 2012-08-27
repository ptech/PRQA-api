/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.Serializable;
import java.util.HashMap;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.status.PRQAStatus;
import net.praqma.prqa.status.StatusCategory;

/**
 * Abstracting readings. This means that we can now have a single object for each build.
 * 
 * You can 
 * 
 * @author Praqma
 */
public interface PRQAReading extends Serializable {
    public Number getReadout(StatusCategory category) throws PrqaException;
    public void setReadout(StatusCategory category, Number value) throws PrqaException;
    public void addNotification(String notificaction);
   
    public HashMap<StatusCategory, Number> getThresholds();
    public void setThresholds(HashMap<StatusCategory, Number> thresholds);
    
    public HashMap<StatusCategory, Number> getReadouts(StatusCategory... categories) throws PrqaException;
    
    public PRQAStatus.PRQAComparisonMatrix createComparison(PRQAContext.ComparisonSettings setting, StatusCategory cat, PRQAReading lastReading) throws PrqaException;
    public PRQAStatus.PRQAComparisonMatrix createComparison(PRQAContext.ComparisonSettings setting, StatusCategory cat) throws PrqaException;
}
