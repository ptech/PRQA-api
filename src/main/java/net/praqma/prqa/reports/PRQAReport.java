/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.logging.Logger;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.parsers.ReportHtmlParser;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAStatus;
import net.praqma.util.execute.CmdResult;

/**
 *Class defining a report. The report holds a commmand line too. (The QAR object) and also holds a result, 
 * 
 * @author Praqma
 */
public abstract class PRQAReport<T extends PRQAStatus> implements Serializable {
        
	protected static final Logger logger = Logger.getLogger(PRQAReport.class.getName());
    private EnumSet<PRQAContext.QARReportType> chosenReports;
    private HashMap<String, String> environment;
	
    protected ReportHtmlParser parser;
    protected QAR reportTool;
    private boolean useCrossModuleAnalysis;
    
    //RQ-1
    private boolean enableDependencyMode;
    private boolean enableDataFlowAnalysis;
    
    //Store the result of the executed command result.
    protected CmdResult cmdResult;
    
    public static String XHTML = "xhtml";
    public static String XML = "xml";
    public static String HTML = "html";
    
    public static String XHTML_REPORT_EXTENSION = "Report."+PRQAReport.XHTML;
    public static String XML_REPORT_EXTENSION = "Report."+PRQAReport.XML;
    public static String HTML_REPORT_EXTENSION = "Report."+PRQAReport.HTML;
    
    public PRQAReport() {
    }
    
    public static String getNamingTemplate(PRQAContext.QARReportType type, String extension) {
        return type.toString()+ " "+extension;
    }
    
    public PRQAReport(QAR reportTool) {
    	logger.finest(String.format("Constructor called for class PRQAReport(QAR reportTool)"));
    	logger.finest(String.format("Input parameter qar type: %s; value: %s", reportTool.getClass(), reportTool));
    	
        this.reportTool = reportTool;
        
        logger.finest(String.format("Ending execution of constructor - PRQAReport"));
	}
    
    public QAR getReportTool() {
    	logger.finest(String.format("Starting execution of method - getQar"));
    	logger.finest(String.format("Returning value: %s", this.reportTool));
    	
        return this.reportTool;
    }

    public void setReportTool(QAR reportTool) {
    	logger.finest(String.format("Starting execution of method - setQar"));
		logger.finest(String.format("Input parameter qar type: %s; value: %s", reportTool.getClass(), reportTool));
		
        this.reportTool = reportTool;
        
        logger.finest(String.format("Ending execution of method - setQar"));
    }
    
    public CmdResult getCmdResult() {
    	logger.finest(String.format("Starting execution of method - getCmdResult"));
    	logger.finest(String.format("Returning value: %s", this.cmdResult));
    	
        return this.cmdResult;
    }
    
    public void setCmdResult(CmdResult res) {
    	logger.finest(String.format("Starting execution of method - setCmdResult"));
		logger.finest(String.format("Input parameter res type: %s; value: %s", res.getClass(), res));
		
        this.cmdResult = res;
        
        logger.finest(String.format("Ending execution of method - setCmdResult"));
    }
    
    /**
     * @return the useCrossModuleAnalysis
     */
    public boolean isUseCrossModuleAnalysis() {
        return useCrossModuleAnalysis;
    }

    /**
     * @param useCrossModuleAnalysis the useCrossModuleAnalysis to set
     */
    public void setUseCrossModuleAnalysis(boolean useCrossModuleAnalysis) {
        this.useCrossModuleAnalysis = useCrossModuleAnalysis;
    }
    
    
    /**
     * The task that is to be executed on the master/slave hosting the job. 
     * @param parameter
     * @return
     * @throws PrqaException 
     */
    public abstract <T> T generateReport() throws PrqaException;        
    
    public abstract String getDisplayName();

    /**
     * @return the enableDependencyMode
     */
    public boolean isEnableDependencyMode() {
        return enableDependencyMode;
    }

    /**
     * @param enableDependencyMode the enableDependencyMode to set
     */
    public void setEnableDependencyMode(boolean enableDependencyMode) {
        this.enableDependencyMode = enableDependencyMode;
    }

    /**
     * @return the chosenReports
     */
    public EnumSet<PRQAContext.QARReportType> getChosenReports() {
        return chosenReports;
    }

    /**
     * @param chosenReports the chosenReports to set
     */
    public void setChosenReports(EnumSet<PRQAContext.QARReportType> chosenReports) {
        this.chosenReports = chosenReports;
    }

    /**
     * @return the enableDataFlowAnalysis
     */
    public boolean isEnableDataFlowAnalysis() {
        return enableDataFlowAnalysis;
    }

    /**
     * @param enableDataFlowAnalysis the enableDataFlowAnalysis to set
     */
    public void setEnableDataFlowAnalysis(boolean enableDataFlowAnalysis) {
        this.enableDataFlowAnalysis = enableDataFlowAnalysis;
    }

    /**
     * @return the environment
     */
    public HashMap<String, String> getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(HashMap<String, String> environment) {
        this.environment = environment;
    }
}

