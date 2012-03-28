package net.praqma.prqa.status;

import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 * This class represent a compliance status readout. 3 values, file compliance, project compliance and number of messages
 * @author jes, man
 */
public class PRQAComplianceStatus extends PRQAStatus implements Comparable<PRQAComplianceStatus> {

    private int messages;
    private Double fileCompliance;
    private Double projectCompliance;
    
    public PRQAComplianceStatus() {}
    
    public PRQAComplianceStatus(int messages, Double fileCompliance, Double projectCompliance) {
        this.messages = messages;
        this.fileCompliance = fileCompliance;
        this.projectCompliance = projectCompliance;
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
    
    @Override
    public Number getReadout(StatusCategory cat) throws PrqaException.PrqaReadingException {
        switch(cat) {
            case ProjectCompliance:
                return this.getProjectCompliance();
            case Messages:
                return this.getMessages();
            case FileCompliance:
                return this.getFileCompliance();
            default:
                throw new PrqaException.PrqaReadingException(String.format("Dident find category %s for class %s", cat, this.getClass()));
        }
    }   
    
    @Override
    public void setReadout(StatusCategory category, Number value) {
        switch(category) {
            case ProjectCompliance:
                setProjectCompliance(value.doubleValue());
                break;
            case Messages:
                setMessages(value.intValue());
                break;
            case FileCompliance:
                setFileCompliance(value.doubleValue());
                break;
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
        if(this == o) {
            return 0;
        }
        
        if(o == null) {
            return 1;
        }
        
        if(this.projectCompliance < o.getProjectCompliance() || this.fileCompliance < o.getProjectCompliance() || this.messages > o.getMessages()) {
           return -1; 
        } else if (this.projectCompliance > o.getProjectCompliance() || this.fileCompliance > o.getFileCompliance() || this.messages < o.getMessages()) {
            return 1;
        } else {
            return 0;
        }       
    }
    
    @Override
    public boolean isValid() {
        return this.fileCompliance != null && this.projectCompliance != null;
    }
    
    public static PRQAComplianceStatus createEmptyResult() {
        return new PRQAComplianceStatus(0, new Double(0), new Double(0));
    }

    @Override
    public String toHtml() {        
        StringBuilder sb = new StringBuilder();
        sb.append("<table style=\"border:solid;border-width:1px;padding:15px;margin:10px;\">");
        sb.append("<h2>Compliance Summary</h2>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Max Messages</th>");
        sb.append("<th>Project Compliance</th>");
        sb.append("<th>File Compliance</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>"+getMessages()+"</td>");
        sb.append("<td>"+getProjectCompliance()+"%</td>");
        sb.append("<td>"+getFileCompliance()+"%</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");               
        return sb.toString();
    }
}
