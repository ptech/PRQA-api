/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.praqma.prqa.PRQAApplicationSettings;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.PRQAReportSettings;
import net.praqma.prqa.PRQAToolUploadSettings;
import net.praqma.prqa.QAVerifyServerSettings;
import net.praqma.prqa.exceptions.PrqaException;
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
 * @author Praqma
 */
public class PRQAReport implements Serializable {

    public static final String XHTML = "xhtml";
    public static final String XML = "xml";
    public static final String HTML = "html";

    public static String XHTML_REPORT_EXTENSION = "Report." + PRQAReport.XHTML;
    public static String XML_REPORT_EXTENSION = "Report." + PRQAReport.XML;
    public static String HTML_REPORT_EXTENSION = "Report." + PRQAReport.HTML;

    private static final Logger log = Logger.getLogger(PRQAReport.class.getName());
    private PRQAReportSettings settings;
    private Collection<QAVerifyServerSettings> qavSettings;
    private PRQAToolUploadSettings upSettings;
    private File workspace;
    private Map<String, String> environment;
    private PRQAApplicationSettings appSettings;

    public PRQAReport(PRQAReportSettings settings, Collection<QAVerifyServerSettings> qavSettings, PRQAToolUploadSettings upSettings, PRQAApplicationSettings appSettings,
                      HashMap<String, String> environment) {
        this.settings = settings;
        this.environment = environment;
        this.qavSettings = qavSettings;
        this.upSettings = upSettings;
        this.appSettings = appSettings;
    }

    public static String getReportName(PRQAContext.QARReportType type) {
        return type == PRQAContext.QARReportType.CodeReview ? "Code Review" : type.toString();
    }

    public static String getNamingTemplate(PRQAContext.QARReportType type, String extension) {
        return getReportName(type) + " " + extension;
    }

    private static String wrapFile(File workspaceRoot, String filePath) throws PrqaException {
        return PRQACommandBuilder.wrapFile(workspaceRoot, filePath);
    }

    public PRQACommandBuilder createCommandBuilder(boolean isUnix) throws PrqaException {
        PRQACommandBuilder builder = new PRQACommandBuilder(appSettings != null ? PRQAApplicationSettings.resolveQawExe(isUnix) : "qaw");
        builder.appendArgument(settings.product);
        if (StringUtils.isNotBlank(settings.projectFile)) {
            builder.appendArgument(wrapFile(workspace, settings.projectFile));
        } else if (StringUtils.isNotBlank(settings.fileList)) {
            builder.appendArgument("-list " + wrapFile(workspace, settings.fileList));
            if (StringUtils.isNotBlank(settings.settingsFile)) {
                builder.appendArgument("-via " + wrapFile(workspace, settings.settingsFile));
            }
        } else {
            throw new PrqaException("Report source not configured (Project File/File List)");
        }

        if (settings.enableDependencyMode) {
            builder.appendArgument("-mode depend");
        }
        builder.appendArgument(PRQACommandBuilder.getDataFlowAnanlysisParameter(settings.enableDataFlowAnalysis));
        return builder;
    }

    public String createAnalysisCommand(boolean isUnix) throws PrqaException {
        PRQACommandBuilder builder = createCommandBuilder(isUnix);
        String pal = settings.performCrossModuleAnalysis ? "pal %Q %P+ %L+" : "";
        if (!StringUtils.isEmpty(pal)) {
            builder.appendArgument(PRQACommandBuilder.getMaseq(pal));
        }
        return builder.getCommand();
    }

    public CmdResult analyze(boolean isUnix) throws PrqaException {
        String finalCommand = createAnalysisCommand(isUnix);
        Map<String, String> systemVars = new HashMap<String, String>();
        systemVars.putAll(System.getenv());
        CmdResult res = null;
        try {
            if (getEnvironment() == null) {
                PRQAReport._logEnv("Current analysis execution environment", systemVars);
                res = CommandLine.getInstance().run(finalCommand, workspace, true, false);
            } else {
                systemVars.putAll(getEnvironment());
                PRQAReport._logEnv("Current modified analysis execution environment", systemVars);
                res = CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
            }
        } catch (AbnormalProcessTerminationException abnex) {
            throw new PrqaException(String.format("Failed to analyze, message was:\n %s", abnex.getMessage()), abnex);
        }
        return res;
    }

    public String createReportCommand(boolean isUnix) throws PrqaException {
        PRQACommandBuilder builder = createCommandBuilder(isUnix);
        builder.appendArgument(PRQACommandBuilder.getSfbaOption(true));

        String reports = "";
        String qar = appSettings != null ? PRQAApplicationSettings.resolveQarExe(isUnix) : "qar";
        for (PRQAContext.QARReportType type : settings.chosenReportTypes) {
            reports += qar + " %Q %P+ %L+ " + PRQACommandBuilder.getReportTypeParameter(getReportName(type), true) + " ";
            reports += PRQACommandBuilder.getViewingProgram("none", false) + " ";
            reports += PRQACommandBuilder.getReportFormatParameter(PRQAReport.XHTML, false) + " ";
            reports += PRQACommandBuilder.getProjectName() + " ";
            reports += PRQACommandBuilder.getOutputPathParameter(workspace.getPath(), true);
            reports += "#";
        }
        // Remove trailing #
        reports = reports.substring(0, reports.length() - 1);
        String qarEmbedded = (settings.performCrossModuleAnalysis ? "pal %Q %P+ %L+#" : "") + reports;
        builder.appendArgument(PRQACommandBuilder.getMaseq(qarEmbedded));
        return builder.getCommand();
    }

    public static void _logEnv(String location, Map<String, String> env) {
        log.fine(String.format("%s", location));
        log.fine("==========================================");
        if (env != null) {
            for (String key : env.keySet()) {
                log.fine(String.format("%s=%s", key, env.get(key)));
            }
        }
        log.fine("==========================================");
    }

    public CmdResult report(boolean isUnix) throws PrqaException {
        Map<String, String> systemVars = new HashMap<String, String>();
        systemVars.putAll(System.getenv());

        String finalCommand = createReportCommand(isUnix);
        try {
            if (getEnvironment() == null) {
                PRQAReport._logEnv("Current report generation execution environment", System.getenv());
                return CommandLine.getInstance().run(finalCommand, workspace, true, false);
            } else {
                systemVars.putAll(getEnvironment());
                PRQAReport._logEnv("Current modified report generation execution environment", systemVars);
                return CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
            }
        } catch (AbnormalProcessTerminationException abnex) {
            log.severe(String.format("Failed to execute report generation command: %s%n%s", finalCommand, abnex.getMessage()));
            log.logp(Level.SEVERE, this.getClass().getName(), "report()", "Failed to execute report generation command", abnex);
            throw new PrqaException(String.format("Failed to execute report generation command: %s%n%s", finalCommand, abnex.getMessage()), abnex);
        }
    }

    public Collection<String> createUploadCommand() throws PrqaException {
        Collection<String> commands = new ArrayList<>();

        if (!settings.publishToQAV || qavSettings == null || qavSettings.isEmpty()) {
            return commands;
        }
        String uploadBinary = "upload";
        try {
            run("publish -host 127.0.0.1 -port -1");
        }
        catch (AbnormalProcessTerminationException e)
        {
            if (e.getExitValue() == 10)
            {
                uploadBinary = "publish";
            }
        }
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        String importCommand = "qaimport %Q %P+ %L+ " + PRQACommandBuilder.getNumberOfThreads(availableProcessors) + " "
                + PRQACommandBuilder.getSop(StringUtils.isBlank(upSettings.sourceOrigin) ? workspace.getPath() : upSettings.sourceOrigin) + " "
                + PRQACommandBuilder.getPrqaVcs(upSettings.codeUploadSetting, upSettings.vcsConfigXml) + " "
                + PRQACommandBuilder.getQavOutPathParameter(workspace.getPath()) + " "
                + PRQACommandBuilder.getImportLogFilePathParameter(workspace.getPath() + QAV.QAV_IMPORT_LOG) + " "
                + PRQACommandBuilder.getCodeAll(upSettings.codeUploadSetting);

        // Step2: The upload part
        Collection<String> uploadParts = new ArrayList<>();

        for (QAVerifyServerSettings qavSetts : qavSettings) {
            String uploadPart = "#" + uploadBinary + " %P+ " + "-prqavcs " + PRQACommandBuilder.wrapInEscapedQuotationMarks(upSettings.vcsConfigXml)
                    + " " + PRQACommandBuilder.getHost(qavSetts.host)
                    + " " + PRQACommandBuilder.getPort(qavSetts.port)
                    + " " + PRQACommandBuilder.getUser(qavSetts.user)
                    + " " + PRQACommandBuilder.getPassword(qavSetts.password)
                    + " " + PRQACommandBuilder.getProjectDatabase(upSettings.qaVerifyProjectName)
                    + " " + PRQACommandBuilder.getProd(upSettings.singleSnapshotMode)
                    + " " + PRQACommandBuilder.getLogFilePathParameter(workspace.getPath() + QAV.QAV_UPLOAD_LOG)
                    + " " + PRQACommandBuilder.wrapInEscapedQuotationMarks(workspace.getPath());

            uploadParts.add(uploadPart);
        }

        // Step3: Finalize
        String source = "";
        if (StringUtils.isNotBlank(settings.projectFile)) {
            source = PRQACommandBuilder.wrapFile(workspace, settings.projectFile);
        } else if (StringUtils.isNotBlank(settings.fileList)) {
            source = "-list " + PRQACommandBuilder.wrapFile(workspace, settings.fileList);
            if (StringUtils.isNotBlank(settings.settingsFile)) {
                source += " -via " + PRQACommandBuilder.wrapFile(workspace, settings.settingsFile);
            }
        } else {
            throw new PrqaException("Neither file list nor project file has been set, this should not be happening");
        }

        String mainCommand = "qaw " + settings.product + " " + source
                + " " + PRQACommandBuilder.getSfbaOption(true) + " "
                + PRQACommandBuilder.getDependencyModeParameter(true) + " ";

        for (String uploadPart : uploadParts) {
            String finalCommand = mainCommand
                    + PRQACommandBuilder.getMaseq(PRQACommandBuilder.getCrossModuleAnalysisParameter(settings.performCrossModuleAnalysis)
                    + importCommand
                    + uploadPart);

            commands.add(finalCommand);
        }

        return commands;
    }

    public CmdResult run(String command) {
        return CommandLine.getInstance().run(command, workspace, true, false, getEnvironment());
    }

    public Collection<CmdResult> upload() throws PrqaException {
        Collection<String> finalCommand = createUploadCommand();
        if (finalCommand == null || finalCommand.isEmpty()) {
            return new ArrayList<>();
        }

        Collection<CmdResult> results = new ArrayList<>();

        try {

            for (String s : finalCommand) {
                results.add(run(s));
            }

            return results;
        } catch (AbnormalProcessTerminationException abnex) {
            log.logp(Level.SEVERE, this.getClass().getName(), "upload()", "Logged error with upload", abnex);
            throw new PrqaUploadException(String.format("Upload failed with message: %s", abnex.getMessage()), abnex);
        }
    }

    public PRQAComplianceStatus getComplianceStatus() throws PrqaException {
        ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser(getWorkspace().getPath() + System.getProperty("file.separator")
                + "Compliance Report.xhtml");
        PRQAComplianceStatus status = new PRQAComplianceStatus();
        Double fileCompliance = Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.fileCompliancePattern));
        Double projectCompliance = Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.projectCompliancePattern));
        int messages = Integer.parseInt(parser.getParseFirstResult(ComplianceReportHtmlParser.totalMessagesPattern));

        for (int i = 0; i < 10; i++) {
            try {
                int result = Integer.parseInt(parser.getParseFirstResult(ComplianceReportHtmlParser.levelNMessages[i]));
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

    public File getWorkspace() {
        return workspace;
    }

    public void setWorkspace(File workspace) {
        this.workspace = workspace;
    }

    public PRQAReportSettings getSettings() {
        return settings;
    }

    public void setSettings(PRQAReportSettings settings) {
        this.settings = settings;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
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
