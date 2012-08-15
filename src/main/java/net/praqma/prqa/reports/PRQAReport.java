/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.logging.Config;
import net.praqma.prqa.parsers.ReportHtmlParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLineException;

/**
 *Class defining a report. The report holds a commmand line too. (The QAR object) and also holds a result, 
 * 
 * @author Praqma
 */
public abstract class PRQAReport<T extends PRQAStatus> implements Serializable {
    
	protected transient Logger logger;
	
    protected ReportHtmlParser parser;
    protected QAR qar;
    
    //Store the result of the executed command result.
    protected CmdResult cmdResult;
    
    public static String XHTML = "xhtml";
    public static String XML = "xml";
    public static String HTML = "html";
    
    public static String XHTML_REPORT_EXTENSION = "Report."+PRQAReport.XHTML;
    public static String XML_REPORT_EXTENSION = "Report."+PRQAReport.XML;
    public static String HTML_REPORT_EXTENSION = "Report."+PRQAReport.HTML;
    
    public PRQAReport(QAR qar) {
    	logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    	logger.log(Level.FINEST, "Constructor called for class PRQAReport");
    	logger.log(Level.FINEST, "Input parameter qar type: {0}; value: {1}", new Object[]{qar.getClass(), qar});
    	
        this.qar = qar;
        
        logger.log(Level.FINEST, "Ending execution of constructor - PRQAReport");
	}

	public void setParser(ReportHtmlParser parser) {
		logger.log(Level.FINEST, "Starting execution of method - setParser");
		logger.log(Level.FINEST, "Input parameter parser type: {0}; value: {1}", new Object[]{parser.getClass(), parser});
		
        this.parser = parser;
        
        logger.log(Level.FINEST, "Ending execution of method - setParser");
    }
    
    public ReportHtmlParser getParser() {
    	logger.log(Level.FINEST, "Starting execution of method - getParser");
    	logger.log(Level.FINEST, "Returning value: {0}", this.parser);
    	
        return this.parser;
    }
    
    public QAR getQar() {
    	logger.log(Level.FINEST, "Starting execution of method - getQar");
    	logger.log(Level.FINEST, "Returning value: {0}", this.qar);
    	
        return this.qar;
    }
    
    public void setQar(QAR qar) {
    	logger.log(Level.FINEST, "Starting execution of method - setQar");
		logger.log(Level.FINEST, "Input parameter qar type: {0}; value: {1}", new Object[]{qar.getClass(), qar});
		
        this.qar = qar;
        
        logger.log(Level.FINEST, "Ending execution of method - setQar");
    }
    
    public CmdResult getCmdResult() {
    	logger.log(Level.FINEST, "Starting execution of method - getCmdResult");
    	logger.log(Level.FINEST, "Returning value: {0}", this.cmdResult);
    	
        return this.cmdResult;
    }
    
    public void setCmdResult(CmdResult res) {
    	logger.log(Level.FINEST, "Starting execution of method - setCmdResult");
		logger.log(Level.FINEST, "Input parameter res type: {0}; value: {1}", new Object[]{res.getClass(), res});
		
        this.cmdResult = res;
        
        logger.log(Level.FINEST, "Ending execution of method - setCmdResult");
    }
    
    /**
     * QAR Reports seem to follow the naming convention of this kind.
     * @return A string representing the actual filename generated by the QAR reporting tool. 
     */
    public String getNamingTemplate() {
    	logger.log(Level.FINEST, "Starting execution of method - getNamingTemplate()");
    	
    	String result = qar.getType() + " " + PRQAReport.XHTML_REPORT_EXTENSION;
    	
    	logger.log(Level.FINEST, "Returning value: {0}", result);
    	
        return result;
    }
    
    /**
     * Provides an alternative extension to the default XHTML extension
     * 
     *Options include:
     * 
     * PRQAReport.XML_REPORT_EXTENSION
     * PRQAReport.HTML_REPORT_EXTENSION
     * PRQAReport.XHTML_REPORT_EXTENSION
     * 
     * @param extension
     * @return 
     */
    public String getNamingTemplate(String extension) {
    	logger.log(Level.FINEST, "Starting execution of method - getNamingTemplate(String extension)");
    	logger.log(Level.FINEST, "Input parameter extension type: {0}; value: {1}", new Object[]{extension.getClass(), extension});
    	
    	String result = qar.getType() + " " + extension;
    	
    	logger.log(Level.FINEST, "Returning value: {0}", result);
    	
        return result;
    }
    
    /**
     * Knowing the naming convention. This will give us a complete path to the report. This should always be the Workspace directory, followed by 
     * the template name for the report.
     * @return A string representing the full path to the generated report.
     */
    public String getFullReportPath() {
    	logger.log(Level.FINEST, "Starting execution of method - getFullReportPath");
    	
    	String result = qar.getReportOutputPath() + File.separator + getNamingTemplate();
    	
    	logger.log(Level.FINEST, "Returning value: {0}", result);
    	
        return result;
    }
    
    public void executeQAR() throws PrqaException {
    	logger.log(Level.FINEST, "Starting execution of method - executeQAR");
		
		String fullReportPath = this.getFullReportPath();
		
		logger.log(Level.FINEST, "Setting full report path to: {0}", fullReportPath);
		
		parser.setFullReportPath(fullReportPath);
		cmdResult = null;
		
		logger.log(Level.FINEST, "Attempting to execute qar...");
		try {
			cmdResult = qar.execute();
		} catch (AbnormalProcessTerminationException ex) {
			PrqaException.PrqaCommandLineException exception = new PrqaException.PrqaCommandLineException(qar, ex);
			
			logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});
			
			throw exception;
		} catch (CommandLineException cle) {
			PrqaException.PrqaCommandLineException exception = new PrqaException.PrqaCommandLineException(qar, cle);
			
			logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});
			
			throw exception;
		}
		logger.log(Level.FINEST, "qar executed successfully!");
		
		logger.log(Level.FINEST, "Ending execution of method - executeQAR");
    }
    
    /**
     * The task that is to be executed on the master/slave hosting the job. 
     * @param parameter
     * @return
     * @throws PrqaException 
     */
    public abstract <T> T completeTask() throws PrqaException;
}
