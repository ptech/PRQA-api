package net.praqma.prqa.status;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.logging.Config;

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
 
    	logger.finest(String.format("Constructor called for class PRQAComplianceStatus"));
    	logger.finest(String.format("Input parameter messages type: %s; value: %s", "int", messages));
    	logger.finest(String.format("Input parameter fileCompliance type: %s; value: %s", fileCompliance.getClass(), fileCompliance));
    	logger.finest(String.format("Input parameter projectCompliance type: %s; value: %s", projectCompliance.getClass(), projectCompliance));
    	
        this.messages = messages;
        this.fileCompliance = fileCompliance;
        this.projectCompliance = projectCompliance;
        
        logger.finest(String.format("Ending execution of constructor - PRQAComplianceStatus"));
    }

    public int getMessages() {
    	logger.finest(String.format("Starting execution of method - getMessages"));
    	logger.finest(String.format("Returning value: %s", messages));
    	
        return messages;
    }

    public void setMessages(int messages) {
    	logger.finest(String.format("Starting execution of method - setMessages"));
		logger.finest(String.format("Input parameter messages type: %s; value: %s", "int", messages));
		
        this.messages = messages;
        
        logger.finest(String.format("Ending execution of method - setMessages"));
    }
    
    public Double getFileCompliance() {
    	logger.finest(String.format("Starting execution of method - getFileCompliance"));
    	logger.finest(String.format("Returning value: %s", this.fileCompliance));
    	
        return this.fileCompliance;
    }
    
    public void setFileCompliance(Double fileCompliance) {
    	logger.finest(String.format("Starting execution of method - setFileCompliance"));
		logger.finest(String.format("Input parameter fileCompliance type: %s; value: %s", fileCompliance.getClass(), fileCompliance));
		
        this.fileCompliance = fileCompliance;
        
        logger.finest(String.format("Ending execution of method - setFileCompliance"));
    }
    
    public Double getProjectCompliance() {
    	logger.finest(String.format("Starting execution of method - getProjectCompliance"));
    	logger.finest(String.format("Returning value: %s", this.projectCompliance));
    	
        return this.projectCompliance;
    }
    
    public void setProjectCompliance(Double projCompliance) {
    	logger.finest(String.format("Starting execution of method - setProjectCompliance"));
		logger.finest(String.format("Input parameter projCompliance type: %s; value: %s", projCompliance.getClass(), projCompliance));
		
        this.projectCompliance = projCompliance;
        
        logger.finest(String.format("Ending execution of method - setProjectCompliance"));
    }
    
    @Override
    public Number getReadout(StatusCategory cat) throws PrqaException.PrqaReadingException {
    	logger.finest(String.format("Starting execution of method - getReadout"));
    	logger.finest(String.format("Input parameter cat type: %s; value: %s", cat.getClass(), cat));
    	
    	Number output;
        switch(cat) {
            case ProjectCompliance:
                output = this.getProjectCompliance();
                
                logger.finest(String.format("Returning value: %s", output));

                return output;
            case Messages:
                output = this.getMessages();
                
                logger.finest(String.format("Returning value: %s", output));
                
                return output;
            case FileCompliance:
                output = this.getFileCompliance();
                
                logger.finest(String.format("Returning this.getFileCompliance(): %s", this.getFileCompliance()));
                
                return output;
            default:
            	PrqaException.PrqaReadingException exception = new PrqaException.PrqaReadingException(String.format("Didn't find category %s for class %s", cat, this.getClass()));
    			
    			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));
    			
    			throw exception;
        }
    }   
    
    @Override
    public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
    	logger.finest(String.format("Starting execution of method - setReadout"));
    	logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));
    	logger.finest(String.format("Input parameter value type: %s; value: %s", value.getClass(), value));
    	
        switch(category) {
            case ProjectCompliance:
            	double prjCompliance = value.doubleValue();
            	
            	logger.finest(String.format("Setting projectCompliance to: %s.", prjCompliance));
            	
                setProjectCompliance(prjCompliance);
                
                logger.finest(String.format("Ending execution of method - setReadout"));
                
                break;
            case Messages:
            	int msgs = value.intValue();
            	
            	logger.finest(String.format("Setting messages to: %s.", msgs));
            	
                setMessages(msgs);
                
                logger.finest(String.format("Ending execution of method - setReadout"));
                
                break;
            case FileCompliance:
            	double fileCompl = value.doubleValue();
            	
            	logger.finest(String.format("Setting fileCompliance to: %s.", fileCompl));
            	
                setFileCompliance(fileCompl);
                
                logger.finest(String.format("Ending execution of method - setReadout"));
                
                break;
            default:
            	PrqaException.PrqaReadingException exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s",value,category,this.getClass()));
    			
    			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));
    			
    			throw exception;
        }
    }
    
    /***
     * Implemented to provide a good reading 
     * @return 
     */
    @Override
    public String toString() {
        String out = "";       
        out += "Project Compliance Index : " + projectCompliance + "%" + System.getProperty("line.separator");
        out += "File Compliance Index : " + fileCompliance + "%" + System.getProperty("line.separator");
        out += "Messages : " + messages + System.getProperty("line.separator") ;
        
        if(notifications != null) {
            for(String note : notifications) {
                out += "Notify: " + note + System.getProperty("line.separator");
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
    	logger.finest(String.format("Starting execution of method - isValid"));
    	
    	boolean result = this.fileCompliance != null && this.projectCompliance != null;
    	
    	logger.finest(String.format("Returning value: %s", result));
    	
        return result;
    }
    
    public static PRQAComplianceStatus createEmptyResult() {
    	logger.finest(String.format("Starting execution of method - createEmptyResult"));
    	
    	PRQAComplianceStatus output = new PRQAComplianceStatus(0, new Double(0), new Double(0));
    	
    	logger.finest(String.format("Returning value: %s", output));
    	
        return output;
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
        sb.append("<td>").append(getMessages()).append("</td>");
        sb.append("<td>").append(getProjectCompliance()).append("%</td>");
        sb.append("<td>").append(getFileCompliance()).append("%</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");               
        return sb.toString();
    }
}
