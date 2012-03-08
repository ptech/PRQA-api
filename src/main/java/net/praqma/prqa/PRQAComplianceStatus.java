package net.praqma.prqa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a compliance status readout. 3 values, file compliance, project compliance and number of messages
 * @author jes, man
 */
public class PRQAComplianceStatus implements Serializable, Comparable<PRQAComplianceStatus> {

    private int messages;
    private Double fileCompliance;
    private Double projectCompliance;
    private List<String> notifications;
    private List<PRQAComplianceStatusCollection.ComplianceCategory> disabledCategories = new ArrayList<PRQAComplianceStatusCollection.ComplianceCategory>(); 

    public PRQAComplianceStatus() {
       notifications = new ArrayList<String>();
    }
    
    public PRQAComplianceStatus(int messages, Double fileCompliance, Double projectCompliance) {
        this.messages = messages;
        this.fileCompliance = fileCompliance;
        this.projectCompliance = projectCompliance;
        notifications = new ArrayList<String>();
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }
    
    public Double getFileCompliance() {
        return this.fileCompliance;
    }
    
    public void setFileCompliance(Double fileCompliance) {
        this.fileCompliance = fileCompliance;
    }
    
    public Double getProjectCompliance() {
        return this.projectCompliance;
    }
    
    public void setProjectCompliance(Double projCompliance) {
        this.projectCompliance = projCompliance;
    }
    
    public Number getComplianceReadout(PRQAComplianceStatusCollection.ComplianceCategory cat) {
        switch(cat) {
            case ProjectCompliance:
                return this.getProjectCompliance();
            case Messages:
                return this.getMessages();
            case FileCompliance:
                return this.getFileCompliance();
            default:
                throw new IllegalArgumentException("Invalid complianace category");
        }
    }
    
    /***
     * Implemented to provide a good reading 
     * @return 
     */
    @Override
    public String toString() {
        String out = "";       
        out += "Project Compliance Index : "+projectCompliance + "%\n";
        out += "File Compliance Index : "+fileCompliance + "%\n";
        out += "Messages : "+messages+"\n";
        if(notifications != null) {
            for(String note : notifications) {
                out += "Notify: "+note+"\n";
            }
        }
        return out;       
    }
    
    
    /***
     * Implemented this to decide which one is 'better than last'.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(PRQAComplianceStatus o) {
        if(o == null)
            return 1;
        if(this.projectCompliance < o.getProjectCompliance() || this.fileCompliance < o.getProjectCompliance() || this.messages > o.getMessages()) {
           return -1; 
        } else if (this.projectCompliance > o.getProjectCompliance() && this.fileCompliance > o.getFileCompliance() && this.messages < o.getMessages()) {
            return 1;
        } else {
            return 0;
        }       
    }
    
    /**
     * TODO: Where is the best place to show build messages?
     * @param message 
     */
    public void addNotication(String message) {
        notifications.add(message);
    }
    
    /**
     * Temporary solution to disable showing of specific properties.
     * @param category
     * @return 
     */
    public boolean isDisabled(PRQAComplianceStatusCollection.ComplianceCategory category) {
        return disabledCategories.contains(category);
    }
    
    public void disable(PRQAComplianceStatusCollection.ComplianceCategory category) {
        disabledCategories.add(category);
    }
    
    /**
     * Tests whether the result contains valid values. in some cases we could end up in situation where an "empty" result would be created.
     * @return 
     */
    public boolean isValid() {
        return this.fileCompliance != null && this.projectCompliance != null;
    }
}
