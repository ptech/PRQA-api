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
    protected static transient Logger logger;
    
    public PRQAComplianceStatus() {}
    
    public PRQAComplianceStatus(int messages, Double fileCompliance, Double projectCompliance) {
    	logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    	logger.log(Level.FINEST, "Constructor called for class PRQAComplianceStatus");
    	logger.log(Level.FINEST, "Input parameter messages type: {0}; value: {1}", new Object[]{"int", messages});
    	logger.log(Level.FINEST, "Input parameter fileCompliance type: {0}; value: {1}", new Object[]{fileCompliance.getClass(), fileCompliance});
    	logger.log(Level.FINEST, "Input parameter projectCompliance type: {0}; value: {1}", new Object[]{projectCompliance.getClass(), projectCompliance});
    	
        this.messages = messages;
        this.fileCompliance = fileCompliance;
        this.projectCompliance = projectCompliance;
        
        logger.log(Level.FINEST, "Ending execution of constructor - PRQAComplianceStatus");
    }

    public int getMessages() {
    	logger.log(Level.FINEST, "Starting execution of method - getMessages");
    	logger.log(Level.FINEST, "Returning value: {0}", messages);
    	
        return messages;
    }

    public void setMessages(int messages) {
    	logger.log(Level.FINEST, "Starting execution of method - setMessages");
		logger.log(Level.FINEST, "Input parameter messages type: {0}; value: {1}", new Object[]{"int", messages});
		
        this.messages = messages;
        
        logger.log(Level.FINEST, "Ending execution of method - setMessages");
    }
    
    public Double getFileCompliance() {
    	logger.log(Level.FINEST, "Starting execution of method - getFileCompliance");
    	logger.log(Level.FINEST, "Returning value: {0}", this.fileCompliance);
    	
        return this.fileCompliance;
    }
    
    public void setFileCompliance(Double fileCompliance) {
    	logger.log(Level.FINEST, "Starting execution of method - setFileCompliance");
		logger.log(Level.FINEST, "Input parameter fileCompliance type: {0}; value: {1}", new Object[]{fileCompliance.getClass(), fileCompliance});
		
        this.fileCompliance = fileCompliance;
        
        logger.log(Level.FINEST, "Ending execution of method - setFileCompliance");
    }
    
    public Double getProjectCompliance() {
    	logger.log(Level.FINEST, "Starting execution of method - getProjectCompliance");
    	logger.log(Level.FINEST, "Returning value: {0}", this.projectCompliance);
    	
        return this.projectCompliance;
    }
    
    public void setProjectCompliance(Double projCompliance) {
    	logger.log(Level.FINEST, "Starting execution of method - setProjectCompliance");
		logger.log(Level.FINEST, "Input parameter projCompliance type: {0}; value: {1}", new Object[]{projCompliance.getClass(), projCompliance});
		
        this.projectCompliance = projCompliance;
        
        logger.log(Level.FINEST, "Ending execution of method - setProjectCompliance");
    }
    
    @Override
    public Number getReadout(StatusCategory cat) throws PrqaException.PrqaReadingException {
    	logger.log(Level.FINEST, "Starting execution of method - getReadout");
    	logger.log(Level.FINEST, "Input parameter cat type: {0}; value: {1}", new Object[]{cat.getClass(), cat});
    	
    	Number output;
        switch(cat) {
            case ProjectCompliance:
                output = this.getProjectCompliance();
                
                logger.log(Level.FINEST, "Returning value: {0}", output);

                return output;
            case Messages:
                output = this.getMessages();
                
                logger.log(Level.FINEST, "Returning value: {0}", output);
                
                return output;
            case FileCompliance:
                output = this.getFileCompliance();
                
                logger.log(Level.FINEST, "Returning this.getFileCompliance(): {0}", this.getFileCompliance());
                
                return output;
            default:
            	PrqaException.PrqaReadingException exception = new PrqaException.PrqaReadingException(String.format("Didn't find category %s for class %s", cat, this.getClass()));
    			
    			logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});
    			
    			throw exception;
        }
    }   
    
    @Override
    public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
    	logger.log(Level.FINEST, "Starting execution of method - setReadout");
    	logger.log(Level.FINEST, "Input parameter category type: {0}; value: {1}", new Object[]{category.getClass(), category});
    	logger.log(Level.FINEST, "Input parameter value type: {0}; value: {1}", new Object[]{value.getClass(), value});
    	
        switch(category) {
            case ProjectCompliance:
            	double prjCompliance = value.doubleValue();
            	
            	logger.log(Level.FINEST, "Setting projectCompliance to: {0}.", prjCompliance);
            	
                setProjectCompliance(prjCompliance);
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            case Messages:
            	int msgs = value.intValue();
            	
            	logger.log(Level.FINEST, "Setting messages to: {0}.", msgs);
            	
                setMessages(msgs);
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            case FileCompliance:
            	double fileCompl = value.doubleValue();
            	
            	logger.log(Level.FINEST, "Setting fileCompliance to: {0}.", fileCompl);
            	
                setFileCompliance(fileCompl);
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            default:
            	PrqaException.PrqaReadingException exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s",value,category,this.getClass()));
    			
    			logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});
    			
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
    	logger.log(Level.FINEST, "Starting execution of method - isValid");
    	
    	boolean result = this.fileCompliance != null && this.projectCompliance != null;
    	
    	logger.log(Level.FINEST, "Returning value: {0}", result);
    	
        return result;
    }
    
    public static PRQAComplianceStatus createEmptyResult() {
    	logger.log(Level.FINEST, "Starting execution of method - createEmptyResult");
    	
    	PRQAComplianceStatus output = new PRQAComplianceStatus(0, new Double(0), new Double(0));
    	
    	logger.log(Level.FINEST, "Returning value: {0}", output);
    	
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
