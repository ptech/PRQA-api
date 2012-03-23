/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.praqma.prqa.PRQAReading;

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
    protected List<StatusCategory> disabledCategories = new ArrayList<StatusCategory>(); 
    
    public enum StatusCategory {
        Messages,
        ProjectCompliance,
        FileCompliance,
        TotalNumberOfFiles,
        LinesOfCode,
        NumberOfSourceFiles,
        NumberOfFileMetrics,
        NumberOfFunctions,
        NumberOfFunctionMetrics;
    }
    
        /**
     * Temporary solution to disable showing of specific properties.
     * @param category
     * @return 
     */
    public boolean isDisabled(StatusCategory category) {
        return disabledCategories.contains(category);
    }
    
    @Override
    public void disable(StatusCategory category) {
        disabledCategories.add(category);
    }
    
    public List<StatusCategory> getDisabledCategories() {
        return disabledCategories;
    }
    
     /**
     * 
     * @param message 
     */
    
    @Override
    public void addNotification(String message) {
        notifications.add(message);
    }
       
    /**
     * Tests whether the result contains valid values. in some cases we could end up in situation where an "empty" result would be created.
     * @return 
     */
    public abstract boolean isValid();
}
