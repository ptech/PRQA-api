/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.PRQAApplicationSettings;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.PRQAReportSettings;
import net.praqma.prqa.PRQAUploadSettings;
import net.praqma.prqa.QAVerifyServerSettings;
import net.praqma.prqa.logging.Config;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAComplianceStatus;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Praqma
 */
public class PRQAReport2 implements Serializable {
   
    
    private static final Logger log = Logger.getLogger(PRQAReport2.class.getName());
    private PRQAReportSettings settings;
    private QAVerifyServerSettings qavSettings;
    private PRQAUploadSettings upSettings;
    private File workspace;
    private HashMap<String,String> environment;
    private PRQAApplicationSettings appSettings;
    
    public PRQAReport2(PRQAReportSettings settings, QAVerifyServerSettings qavSettings, PRQAUploadSettings upSettings, PRQAApplicationSettings appSettings) {
        this.settings = settings;
        this.qavSettings = qavSettings;
        this.upSettings = upSettings;
        this.appSettings = appSettings;
               
    }
    
    public PRQAReport2(PRQAReportSettings settings, QAVerifyServerSettings qavSettings, PRQAUploadSettings upSettings, PRQAApplicationSettings appSettings, HashMap<String,String> environment) {
        this.settings = settings;
        this.environment = environment;
        this.qavSettings = qavSettings;
        this.upSettings = upSettings;
        this.appSettings = appSettings;
    }
    
    public String createAnalysisCommand() {
        String finalCommand = "";
        
        PRQACommandBuilder builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
        //TODO: QAC should not be hardcoded (use settings!)
        builder.prependArgument(settings.product);
        
        //TODO: Either project or file list
        builder.appendArgument(PRQACommandBuilder.getProjectFile(settings.projectFile));
        
        if(settings.enableDataFlowAnalysis) {
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
    
    public CmdResult analyze() {
        String finalCommand = createAnalysisCommand();
 
        CmdResult res = null;
        if(getEnvironment() == null) {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true, getEnvironment());
        }
        return res;
    }
    
    public String createReportCommand() {
        PRQACommandBuilder builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);        
        builder.prependArgument(settings.product);
        builder.appendArgument(PRQACommandBuilder.getProjectFile(settings.projectFile));
        if(settings.enableDependencyMode) {
            builder.appendArgument("-mode depend");
        }
        builder.appendArgument(PRQACommandBuilder.getDataFlowAnanlysisParameter(settings.enableDataFlowAnalysis));
        builder.appendArgument(PRQACommandBuilder.getSfbaOption(true));
        
        String reports = "";
        for (PRQAContext.QARReportType type : settings.chosenReportTypes) {
            reports += appSettings.resolveQarExe(false)+" %Q %P+ %L+ " + PRQACommandBuilder.getReportTypeParameter(type.toString(), true)+ " ";
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
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true, getEnvironment());
        }
        
        return res.stdoutList;
    }
    
    
    public CmdResult report() {      
        String finalCommand = createReportCommand();

        CmdResult res = null;
        if(getEnvironment() == null) {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true, getEnvironment());
        }
        return res;
    }
    
    public String createUploadCommand() {
        if(settings.publishToQAV) {
            int availableProcessors = Runtime.getRuntime().availableProcessors(); 
            String importCommand = " " +  "%Q %P+ %L+ "+PRQACommandBuilder.getNumberOfThreads(availableProcessors)+" "+PRQACommandBuilder.getSop(StringUtils.isBlank(upSettings.sourceOrigin) ? workspace.getPath() : upSettings.sourceOrigin) + " ";
            importCommand += PRQACommandBuilder.getPrqaVcs(upSettings.codeUploadSetting, upSettings.vcsConfigXml)+" ";
            importCommand += PRQACommandBuilder.getQavOutPathParameter(workspace.getPath())+" ";
            importCommand += PRQACommandBuilder.getImportLogFilePathParameter(workspace.getPath()+Config.QAV_IMPORT_LOG)+" ";
            importCommand += PRQACommandBuilder.getCodeAll(upSettings.codeUploadSetting);

            //Step2: The upload part
            String uploadPart ="#upload %P+ " + "-prqavcs "+PRQACommandBuilder.wrapInEscapedQuotationMarks(upSettings.vcsConfigXml);
            uploadPart +=" "+PRQACommandBuilder.getHost(qavSettings.host);
            uploadPart +=" "+PRQACommandBuilder.getPort(qavSettings.port);    
            uploadPart +=" "+PRQACommandBuilder.getUser(qavSettings.user);       
            uploadPart +=" "+PRQACommandBuilder.getPassword(qavSettings.password); 
            uploadPart +=" "+PRQACommandBuilder.getProjectDatabase(upSettings.qaVerifyProjectName);
            uploadPart +=" "+PRQACommandBuilder.getProd(upSettings.singleSnapshotMode);     
            uploadPart +=" "+PRQACommandBuilder.getLogFilePathParameter(workspace.getPath()+Config.QAV_UPLOAD_LOG);
            uploadPart +=" "+PRQACommandBuilder.wrapInEscapedQuotationMarks(workspace.getPath());

            //Step3: Finalize
            //TODO hardcoding to QAC to begin with
            String mainCommand = "qaw " + "qac " +PRQACommandBuilder.wrapInQuotationMarks(settings.projectFile);
            mainCommand += " "+ PRQACommandBuilder.getSfbaOption(true)+" ";
            mainCommand += PRQACommandBuilder.getDependencyModeParameter(true) + " ";

            String finalCommand = mainCommand + PRQACommandBuilder.getMaseq(PRQACommandBuilder.getCrossModuleAnalysisParameter(settings.performCrossModuleAnalysis)+importCommand+uploadPart);
            return finalCommand;
        }
        return null;
    }
    
    public CmdResult upload() {
        String finalCommand = createUploadCommand();
        if(finalCommand == null) {
            return null;
        }
        CmdResult res = null;
        //Step 1: The import part

        log.fine("Executing command:\n"+finalCommand);

        
        if(getEnvironment() == null) {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true);
        } else {
            res = CommandLine.getInstance().run(finalCommand, workspace, true, true, getEnvironment());
        }
        
        return res;
    }
    
    public PRQAComplianceStatus getComplianceStatus() throws PrqaException {                        
        //TODO (Cross-platform file path)
        ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser(getWorkspace().getPath()+"\\Compliance Report.xhtml");
        PRQAComplianceStatus status = new PRQAComplianceStatus();
        Double fileCompliance = Double.parseDouble(parser.getResult(ComplianceReportHtmlParser.fileCompliancePattern));
        Double projectCompliance =  Double.parseDouble(parser.getResult(ComplianceReportHtmlParser.projectCompliancePattern));
        int messages = Integer.parseInt(parser.getResult(ComplianceReportHtmlParser.totalMessagesPattern));
        
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

