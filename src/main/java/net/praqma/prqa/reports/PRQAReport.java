/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.prqa.PRQAApplicationSettings;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.PRQAReportSettings;
import net.praqma.prqa.PRQAUploadSettings;
import net.praqma.prqa.QAVerifyServerSettings;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.exceptions.PrqaSetupException;
import net.praqma.prqa.exceptions.PrqaUploadException;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QAV;
import net.praqma.prqa.status.PRQAComplianceStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Praqma
 */
public class PRQAReport implements Serializable {
   
    public static String XHTML = "xhtml";
    public static String XML = "xml";
    public static String HTML = "html";
    
    public static String XHTML_REPORT_EXTENSION = "Report."+XHTML;
    public static String XML_REPORT_EXTENSION = "Report."+XML;
    public static String HTML_REPORT_EXTENSION = "Report."+HTML;
    
    
    private static final Logger log = Logger.getLogger(PRQAReport.class.getName());
    private PRQAReportSettings settings;
    private QAVerifyServerSettings qavSettings;
    private PRQAUploadSettings upSettings;
    private File workspace;
    private HashMap<String,String> environment;
    private PRQAApplicationSettings appSettings;
    
    public PRQAReport(PRQAReportSettings settings, QAVerifyServerSettings qavSettings, PRQAUploadSettings upSettings, PRQAApplicationSettings appSettings) {
        this.settings = settings;
        this.qavSettings = qavSettings;
        this.upSettings = upSettings;
        this.appSettings = appSettings;
               
    }
    
    public PRQAReport(PRQAReportSettings settings, QAVerifyServerSettings qavSettings, PRQAUploadSettings upSettings, PRQAApplicationSettings appSettings, HashMap<String,String> environment) {
        this.settings = settings;
        this.environment = environment;
        this.qavSettings = qavSettings;
        this.upSettings = upSettings;
        this.appSettings = appSettings;
    }
    
    public static String getNamingTemplate(PRQAContext.QARReportType type, String extension) {
        return type.toString()+ " "+extension;
    }
    
    public String createAnalysisCommand(boolean isUnix) {
        String finalCommand = "";
        
        PRQACommandBuilder builder = new PRQACommandBuilder(appSettings != null ? appSettings.resolveQawExe(isUnix) : "qaw");        
        builder.prependArgument(settings.product);
        
        //TODO: Either project or file list
        builder.appendArgument(PRQACommandBuilder.getProjectFile(settings.projectFile));
        
        if(settings.enableDependencyMode) {
            builder.appendArgument("-mode depend");
        }
        builder.appendArgument(PRQACommandBuilder.getDataFlowAnanlysisParameter(settings.enableDataFlowAnalysis));
        
        String pal = (settings.performCrossModuleAnalysis ? "pal %Q %P+ %L+" : "");
        
        if(!StringUtils.isEmpty(pal)) {
            builder.appendArgument(PRQACommandBuilder.getMaseq(pal));
        }
        
        //TODO: Analysis command done...ANALYZE
        
        finalCommand = builder.getCommand();
        return finalCommand;
    }
    
    public CmdResult analyze(boolean isUnix) {
        String finalCommand = createAnalysisCommand(isUnix);
 
        CmdResult res = null;
        if(getEnvironment() == null) {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, false);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
        }
        return res;
    }
    
    public String createReportCommand(boolean isUnix) {
        PRQACommandBuilder builder = new PRQACommandBuilder(appSettings != null ? appSettings.resolveQawExe(isUnix) : "qaw");        
        builder.prependArgument(settings.product);
        builder.appendArgument(PRQACommandBuilder.getProjectFile(settings.projectFile));
        if(settings.enableDependencyMode) {
            builder.appendArgument("-mode depend");
        }
        builder.appendArgument(PRQACommandBuilder.getDataFlowAnanlysisParameter(settings.enableDataFlowAnalysis));
        builder.appendArgument(PRQACommandBuilder.getSfbaOption(true));
        
        String reports = "";
        String qar = appSettings != null ? PRQAApplicationSettings.resolveQarExe(isUnix) : "qar"; 
        for (PRQAContext.QARReportType type : settings.chosenReportTypes) {
            reports += qar +" %Q %P+ %L+ " + PRQACommandBuilder.getReportTypeParameter(type.toString(), true)+ " ";
            reports += PRQACommandBuilder.getViewingProgram("noviewer")+ " ";
            reports += PRQACommandBuilder.getReportFormatParameter(PRQAReport.XHTML, false)+ " ";
            reports += PRQACommandBuilder.getProjectName()+ " ";
            reports += PRQACommandBuilder.getOutputPathParameter(workspace.getPath(), true);
            reports += "#";
        }
        //Remove trailing #
        reports = reports.substring(0, reports.length()-1);
        String qarEmbedded = (settings.performCrossModuleAnalysis ? "pal %Q %P+ %L+#" : "")+reports;
        builder.appendArgument(PRQACommandBuilder.getMaseq(qarEmbedded));
        return builder.getCommand();
    }
    
    //TODO: Delete this method before release!
    public List<String> printEnvironmentAsFromPJUTils() {
        CmdResult res = null;
        String finalCommand = "set";
        if(getEnvironment() == null) {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, false);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
        }
        
        return res.stdoutList;
    }
    
    public CmdResult report(boolean isUnix) {      
        String finalCommand = createReportCommand(isUnix);

        CmdResult res = null;
        if(getEnvironment() == null) {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, false);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
        }
        return res;
    }
    
    public String createUploadCommand() {
        if(settings.publishToQAV) {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            String importCommand = PRQACommandBuilder.escapeWhitespace("qaimport");
            
            importCommand += " " +  "%Q %P+ %L+ "+PRQACommandBuilder.getNumberOfThreads(availableProcessors)+" "+PRQACommandBuilder.getSop(StringUtils.isBlank(upSettings.sourceOrigin) ? workspace.getPath() : upSettings.sourceOrigin) + " ";
            importCommand += PRQACommandBuilder.getPrqaVcs(upSettings.codeUploadSetting, upSettings.vcsConfigXml)+" ";
            importCommand += PRQACommandBuilder.getQavOutPathParameter(workspace.getPath())+" ";
            importCommand += PRQACommandBuilder.getImportLogFilePathParameter(workspace.getPath()+QAV.QAV_IMPORT_LOG)+" ";
            importCommand += PRQACommandBuilder.getCodeAll(upSettings.codeUploadSetting);

            //Step2: The upload part
            String uploadPart ="#upload %P+ " + "-prqavcs "+PRQACommandBuilder.wrapInEscapedQuotationMarks(upSettings.vcsConfigXml);
            uploadPart +=" "+PRQACommandBuilder.getHost(qavSettings.host);
            uploadPart +=" "+PRQACommandBuilder.getPort(qavSettings.port);    
            uploadPart +=" "+PRQACommandBuilder.getUser(qavSettings.user);       
            uploadPart +=" "+PRQACommandBuilder.getPassword(qavSettings.password); 
            uploadPart +=" "+PRQACommandBuilder.getProjectDatabase(upSettings.qaVerifyProjectName);
            uploadPart +=" "+PRQACommandBuilder.getProd(upSettings.singleSnapshotMode);     
            uploadPart +=" "+PRQACommandBuilder.getLogFilePathParameter(workspace.getPath()+QAV.QAV_UPLOAD_LOG);
            uploadPart +=" "+PRQACommandBuilder.wrapInEscapedQuotationMarks(workspace.getPath());

            //Step3: Finalize
            //TODO hardcoding to QAC to begin with
            String mainCommand = "qaw" + " " + settings.product +  " "+PRQACommandBuilder.wrapInQuotationMarks(settings.projectFile);
            mainCommand += " "+ PRQACommandBuilder.getSfbaOption(true)+" ";
            mainCommand += PRQACommandBuilder.getDependencyModeParameter(true) + " ";

            String finalCommand = mainCommand + PRQACommandBuilder.getMaseq(PRQACommandBuilder.getCrossModuleAnalysisParameter(settings.performCrossModuleAnalysis)+importCommand+uploadPart);
            return finalCommand;
        }
        return null;
    }
    
    public CmdResult upload() throws PrqaUploadException {
        CmdResult res = null;
        String finalCommand = createUploadCommand();
        if(finalCommand == null) {
            return null;
        }
        
        try {
            if(getEnvironment() == null) {
                res = CommandLine.getInstance().run(finalCommand, workspace, true, false);
            } else {            
                res = CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
            }
        } catch (AbnormalProcessTerminationException abnex) {
            log.logp(Level.SEVERE, this.getClass().getName(), "upload()", "Logged error with upload", abnex);
            throw new PrqaUploadException(String.format("Upload failed with message:\n%s", abnex.getMessage()), abnex);
        }
        
        return res;
    }
    
    public PRQAComplianceStatus getComplianceStatus() throws PrqaException {                        
        //TODO (Cross-platform file path)
        ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser(getWorkspace().getPath()+ System.getProperty("file.separator") + "Compliance Report.xhtml");
        PRQAComplianceStatus status = new PRQAComplianceStatus();
        Double fileCompliance = Double.parseDouble(parser.getResult(ComplianceReportHtmlParser.fileCompliancePattern));
        Double projectCompliance =  Double.parseDouble(parser.getResult(ComplianceReportHtmlParser.projectCompliancePattern));
        int messages = Integer.parseInt(parser.getResult(ComplianceReportHtmlParser.totalMessagesPattern));                
        
        for(int i=0; i<10; i++) {
            try {
                int result = Integer.parseInt(parser.getResult(ComplianceReportHtmlParser.levelNMessages[i]));
                status.getMessagesByLevel().put(i, result);
            } catch (NumberFormatException nfe) {
                status.getMessagesByLevel().put(i, 0);
            }
        }
        
        status.setFileCompliance(fileCompliance);
        status.setProjectCompliance(projectCompliance);
        status.setMessages(messages);
               
        return status;
    }           

    /**
     * @return the workspace
     */
    public File getWorkspace() {
        return workspace;
    }

    /**
     * @param workspace the workspace to set
     */
    public void setWorkspace(File workspace) {
        this.workspace = workspace;
    }

    /**
     * @return the settings
     */
    public PRQAReportSettings getSettings() {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void setSettings(PRQAReportSettings settings) {
        this.settings = settings;
    }

    /**
     * @return the environment
     */
    public HashMap<String,String> getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(HashMap<String,String> environment) {
        this.environment = environment;
    }

    /**
     * @return the appSettings
     */
    public PRQAApplicationSettings getAppSettings() {
        return appSettings;
    }

    /**
     * @param appSettings the appSettings to set
     */
    public void setAppSettings(PRQAApplicationSettings appSettings) {
        this.appSettings = appSettings;
    }

}

