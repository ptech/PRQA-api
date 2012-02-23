package net.praqma.prqa;

import java.util.ArrayList;

/**
 *
 * @author Praqma
 */
public class PRQAComplianceStatusCollection extends ArrayList<PRQAComplianceStatus> {
    
    public enum ComplianceCategory {
        Messages,
        ProjectCompliance,
        FileCompliance;
        

        @Override
        public String toString() {
            switch(this) {
                case ProjectCompliance:
                    return "ProjectCompliance";
                case Messages:
                    return "Messages";
                case FileCompliance:
                    return "FileCompliance";
                default:
                    throw new IllegalArgumentException("Invalid complianace category");
            }
        }
    }
    
    public PRQAComplianceStatusCollection() {
        this(new ArrayList<PRQAComplianceStatus>());
    }
    
    public PRQAComplianceStatusCollection(ArrayList<PRQAComplianceStatus> collection) {
        this.addAll(collection);
    }

    /***
     * Implemented a collection method to gather extremities from a given set of collected compliance statistics.
     * @param category
     * @return 
     */
    public int getMax(ComplianceCategory category) {
        int max = Integer.MIN_VALUE;
        int tmp = 0;
        switch(category) {
            case ProjectCompliance:
                for(PRQAComplianceStatus s : this) {
                    
                    tmp = s.getProjectCompliance() == null ? 0 : s.getProjectCompliance().intValue();
                    if(tmp >= max)
                        max = tmp;
                }
                break;
            case Messages:
                for(PRQAComplianceStatus s : this) {
                    
                    tmp = s.getMessages();
                    if(tmp >= max)
                        max = tmp;
                }
                break;
            case FileCompliance:
                for(PRQAComplianceStatus s : this) {
                    
                    tmp = s.getFileCompliance() == null ? 0 : s.getFileCompliance().intValue();
                    if(tmp >= max)
                        max = tmp;
                }
        }
        return max;
    }
     /***
     * Implemented a collection method to gather extremities from a given set of collected compliance statistics.
     * @param category
     * @return a number indicating the smallest given observation for the specified category.
     */
    public int getMin(ComplianceCategory category) {
        int min = Integer.MAX_VALUE;
        int tmp = 0;
        switch(category) {
            case ProjectCompliance:
                for(PRQAComplianceStatus s : this) {
                    
                    tmp = s.getProjectCompliance() == null ? 0 : s.getProjectCompliance().intValue();
                    if(tmp <= min)
                        min = tmp;
                }
                break;
            case Messages:
                for(PRQAComplianceStatus s : this) {                
                    tmp = s.getMessages();
                    if(tmp <= min)
                        min = tmp;
                }
                break;
            case FileCompliance:
                for(PRQAComplianceStatus s : this) {                    
                    tmp = s.getFileCompliance() == null ? 0 : s.getFileCompliance().intValue();
                    if(tmp <= min)
                        min = tmp;
                }
        }
        return min;
    }
}
