package net.praqma.prqa.reports;

import net.praqma.prqa.PRQAApplicationSettings;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.QAVerifyServerSettings;
import net.praqma.prqa.QaFrameworkVersion;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.exceptions.PrqaUploadException;
import net.praqma.prqa.execute.PrqaCommandLine;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.parsers.MessageGroup;
import net.praqma.prqa.parsers.ResultsDataParser;
import net.praqma.prqa.parsers.Rule;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QACli;
import net.praqma.prqa.status.PRQAComplianceStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.prqma.prqa.qaframework.QaFrameworkReportSettings;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.praqma.prqa.reports.ReportType.CRR;
import static net.praqma.prqa.reports.ReportType.MDR;
import static net.praqma.prqa.reports.ReportType.RCR;
import static net.praqma.prqa.reports.ReportType.SUR;

public class QAFrameworkReport implements Serializable {

    /**
     * @author Alexandru Ion
     * @since 2.0.3
     */
    private static final long serialVersionUID = 1L;
    public static final String XHTML = "xhtml";
    public static final String XML = "xml";
    public static final String HTML = "html";

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String QUOTE = "\"";

    public static String XHTML_REPORT_EXTENSION = "Report." + PRQAReport.XHTML;
    public static String XML_REPORT_EXTENSION = "Report." + PRQAReport.XML;
    public static String HTML_REPORT_EXTENSION = "Report." + PRQAReport.HTML;

    public static String extractReportsPath(final String root, final String qaProject)
            throws
            PrqaException {

        String path = StringUtils.isEmpty(root) ? root : root + File.separator;

        path = PRQACommandBuilder.resolveAbsOrRelativePath(new File(path), qaProject);

        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.parse(new File(path, "prqaproject.xml"));
            final Element element = document.getDocumentElement();
            final XPath xPath = XPathFactory.newInstance().newXPath();
            final XPathExpression compile = xPath.compile("/prqaproject/configurations/default_config/@name");
            final String name = compile.evaluate(element);

            if (StringUtils.isEmpty(name)) {
                throw new PrqaException("Unable to find default config name in project");
            }

            final String fmt = "prqa%1$sconfigs%1$s%2$s%1$sreports";
            return path + File.separator + String.format(fmt, File.separator, name);

        } catch (IOException | SAXException | XPathExpressionException | ParserConfigurationException e) {
            throw new PrqaException("Failed to parse project configuration", e);
        }
    }

    private static final Logger log = Logger.getLogger(QAFrameworkReport.class.getName());
    private QaFrameworkReportSettings settings;
    private QAVerifyServerSettings qaVerifySettings;
    private File workspace;
    private Map<String, String> environment;
    private PRQAApplicationSettings appSettings;
    private QaFrameworkVersion qaFrameworkVersion;

    public QAFrameworkReport(QaFrameworkReportSettings settings, QAVerifyServerSettings qaVerifySettings,
            PRQAApplicationSettings appSettings) {
        this.settings = settings;
        this.appSettings = appSettings;
        this.qaVerifySettings = qaVerifySettings;
    }

    public QAFrameworkReport(QaFrameworkReportSettings settings, QAVerifyServerSettings qaVerifySettings,
            PRQAApplicationSettings appSettings, HashMap<String, String> environment) {
        this.settings = settings;
        this.environment = environment;
        this.appSettings = appSettings;
        this.qaVerifySettings = qaVerifySettings;
    }

    public CmdResult pullUnifyProjectQacli(boolean isUnix, PrintStream out) throws PrqaException {
        if (!settings.isLoginToQAV()) {
            out.println("Configuration Error: Download Unified Project is Selected but QAV Server Connection Configuration is not Selected");
            return null;
        } else {
            String command = createPullUnifyProjectCommand(isUnix, out);
            out.println("Perform DOWNLOAD UNIFIED PROJECT DEFINITION command:");
            out.println(command);
            try {
                return PrqaCommandLine.getInstance().run(command, workspace, true, false, out);
            } catch (AbnormalProcessTerminationException abnex) {
                throw new PrqaException("ERROR: Failed to Download Unified Project, please check the download command message above for more details", abnex);
            }
        }
    }

    private String createPullUnifyProjectCommand(boolean isUnix, PrintStream out) throws PrqaException {

        if (StringUtils.isBlank(settings.getUniProjectName())) {
            throw new PrqaException(
                    "Configuration Error: Download Unified Project Definition was selected but no Unified project was provided. The Download unified project was aborted.");
        } else if (StringUtils.isBlank(qaVerifySettings.host) || StringUtils.isBlank(qaVerifySettings.user)) {
            throw new PrqaException("QAV Server Connection Settings are not selected");
        }
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("admin");
        builder.appendArgument("--pull-unify-project");
        builder.appendArgument("--qaf-project");
        builder.appendArgument(PRQACommandBuilder.wrapFile(workspace, settings.getQaProject()));
        builder.appendArgument("--username");
        builder.appendArgument(qaVerifySettings.user);
        builder.appendArgument("--password");
        builder.appendArgument(qaVerifySettings.password.isEmpty() ? "\"\"" : qaVerifySettings.password);
        builder.appendArgument("--url");
        builder.appendArgument(qaVerifySettings.host + ":" + qaVerifySettings.port);
        builder.appendArgument("--project-name");
        builder.appendArgument(settings.getUniProjectName());
        return builder.getCommand();
    }

    public CmdResult analyzeQacli(boolean isUnix, String Options, PrintStream out) throws PrqaException {
        String finalCommand = createAnalysisCommandForQacli(isUnix, Options, out);
        out.println("Perform ANALYSIS command:");
        out.println(finalCommand);
        HashMap<String, String> systemVars = new HashMap<>();
        systemVars.putAll(System.getenv());
        try {
            if (getEnvironment() == null) {
                PRQAReport._logEnv("Current analysis execution environment", systemVars);
                return PrqaCommandLine.getInstance().run(finalCommand, workspace, true, false, out);
            } else {
                systemVars.putAll(getEnvironment());
                PRQAReport._logEnv("Current modified analysis execution environment", systemVars);
                return PrqaCommandLine.getInstance().run(finalCommand, workspace, true, false, systemVars, out);
            }
        } catch (AbnormalProcessTerminationException abnex) {
            throw new PrqaException("ERROR: Failed to analyze, please check the analysis command message above for more details", abnex);
        }
    }

    private String createAnalysisCommandForQacli(boolean isUnix, String options, PrintStream out) throws PrqaException {
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("analyze");
        String analyzeOptions = options;
        if (settings.isQaEnableDependencyMode() && analyzeOptions.contains("c")) {
            analyzeOptions = analyzeOptions.replace("c", "");
        }
        if (settings.isQaEnableProjectCma()) {
            if (analyzeOptions.contains("f")) {
                analyzeOptions = analyzeOptions.replace("f", "p");
            }
        }

        builder.appendArgument(analyzeOptions);
        if (settings.isStopWhenFail() && settings.isAnalysisSettings()) {
            builder.appendArgument("--stop-on-fail");
        }

        if (settings.isGeneratePreprocess() && settings.isAnalysisSettings()) {
            builder.appendArgument("--generate-preprocessed-source");
        }

        if (settings.isGeneratePreprocess() && settings.isAnalysisSettings() && settings.isAssembleSupportAnalytics()) {
            builder.appendArgument("--assemble-support-analytics");
        } else if (settings.isAnalysisSettings() && settings.isAssembleSupportAnalytics() && (!settings.isGeneratePreprocess())) {
            log.log(Level.WARNING, "Assemble Support Analytics is selected but Generate Preprocessed Source option is not selected");
        }

        builder.appendArgument("-P");
        builder.appendArgument(PRQACommandBuilder.wrapFile(workspace, settings.getQaProject()));
        return builder.getCommand();
    }

    public CmdResult cmaAnalysisQacli(boolean isUnix, PrintStream out) throws PrqaException {
        if (settings.isQaCrossModuleAnalysis()) {
            String command = createCmaAnalysisCommand(isUnix, out);
            if (command != null) {
                out.println("Perform CROSS-MODULE ANALYSIS command:");
                out.println(command);
                try {
                    return PrqaCommandLine.getInstance().run(command, workspace, true, false, out);
                } catch (AbnormalProcessTerminationException abnex) {
                    throw new PrqaException("ERROR: Failed to analyze, please check the Cross-Module-Analysis command message above for more details");
                }
            } else {
                throw new PrqaException("ERROR: Detected PRQA Framework version 2.1.0. CMA analysis cannot be configured with the selected option. It has to be done by adding it to the toolchain of the project.");
            }
        }
        return null;
    }

    private String createCmaAnalysisCommand(boolean isUnix, PrintStream out) throws PrqaException {
        if (!qaFrameworkVersion.isQaFrameworkVersion210()) {
            PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
            builder.appendArgument("analyze");
            builder.appendArgument("-p");
            builder.appendArgument("-P");
            builder.appendArgument(PRQACommandBuilder.wrapFile(workspace, settings.getQaProject()));
            return builder.getCommand();
        }
        return null;
    }

    public CmdResult reportQacli(boolean isUnix, String repType, PrintStream out) throws PrqaException {
        /*MDR Report type isnt supported in 1.0.3, 1.0.2, 1.0.1 and 1.0.0 */
        if (repType.equals("MDR") && (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion104())) {
            out.println("===================================================================================================");
            out.println("Warning: Metrics Data Report isn't supported report type in PRQA-Framework Prior to 1.0.4 version");
            out.println("===================================================================================================");
            log.severe(String.format("Warning: Metrics Data Report isn't supported report type in PRQA-Framework Prior to 1.0.4 version"));
            return null;
        }
        String reportCommand = createReportCommandForQacli(isUnix, repType, out);
        Map<String, String> systemVars = new HashMap<String, String>();
        systemVars.putAll(System.getenv());
        systemVars.putAll(getEnvironment());
        out.println(reportCommand);
        try {
            PRQAReport._logEnv("Current report generation execution environment", systemVars);
            return PrqaCommandLine.getInstance().run(reportCommand, workspace, true, false, systemVars, out);
        } catch (AbnormalProcessTerminationException abnex) {
            log.severe(String.format("Failed to execute report generation command: %s%n%s", reportCommand,
                    abnex.getMessage()));
            log.logp(Level.SEVERE, this.getClass().getName(), "report()",
                    "Failed to execute report generation command", abnex);
            out.println("Failed to execute report generation command, please check the report command message above for more details");
        }
        return new CmdResult();
    }

    private String createReportCommandForQacli(boolean isUnix, String reportType, PrintStream out) throws PrqaException {
        out.println("Perform CREATE " + reportType + " REPORT command");
        String projectLocation = PRQACommandBuilder.resolveAbsOrRelativePath(workspace, settings.getQaProject());
        removeOldReports(projectLocation, reportType);
        return createReportCommand(projectLocation, reportType, out);
    }

    private String createReportCommand(String projectLocation, String reportType, PrintStream out) {
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("report -P");
        builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(projectLocation));
        if (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion104()) {
            builder.appendArgument("-l C");
        }
        builder.appendArgument("-t");
        builder.appendArgument(reportType);

        return builder.getCommand();
    }

    private void removeOldReports(String projectLocation, String reportType) {
        String reportsPath = "/prqa/reports";
        File file = new File(projectLocation + reportsPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if ((f.getName().contains(RCR.name()) && reportType.equals(RCR.name()))
                        || (f.getName().contains(CRR.name()) && reportType.equals(CRR.name()))
                        || (f.getName().contains(MDR.name()) && reportType.equals(MDR.name()))
                        || (f.getName().contains(SUR.name()) && reportType.equals(SUR.name()))
                        || f.getName().contains("results_data")) {
                    f.delete();
                }

            }
        }
    }

    private String createUploadCommandQacli(PrintStream out) throws PrqaException {
        String projectLocation;
        if (!StringUtils.isBlank(settings.getQaVerifyProjectName())) {
            projectLocation = PRQACommandBuilder.wrapFile(workspace, settings.getQaProject());
        } else {
            throw new PrqaException("Neither file list nor project file has been set, this should not be happening");
        }
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("upload -P");
        builder.appendArgument(projectLocation);
        builder.appendArgument("--qav-upload");
        builder.appendArgument("--username");
        builder.appendArgument(qaVerifySettings.user);
        builder.appendArgument("--password");
        builder.appendArgument(qaVerifySettings.password.isEmpty() ? "\"\"" : qaVerifySettings.password);
        builder.appendArgument("--url");
        builder.appendArgument(qaVerifySettings.host + ":" + qaVerifySettings.port);
        builder.appendArgument("--upload-project");
        builder.appendArgument(settings.getQaVerifyProjectName());
        builder.appendArgument("--snapshot-name");
        builder.appendArgument(settings.getUploadSnapshotName() + '_' + settings.getbuildNumber());
        builder.appendArgument("--upload-source");
        builder.appendArgument(settings.getUploadSourceCode());
        return builder.getCommand();
    }

    public CmdResult uploadQacli(PrintStream out) throws PrqaUploadException, PrqaException {
        if (!settings.isLoginToQAV()) {
            out.println("Configuration Error: Upload Results to QAV is Selected but QAV Server Connection Configuration is not Selected");
            return null;
        }
        String finalCommand = createUploadCommandQacli(out);
        out.println("Perform UPLOAD command: " + finalCommand);
        try {
            Map<String, String> getEnv = getEnvironment();
            if (getEnv == null) {
                return PrqaCommandLine.getInstance().run(finalCommand, workspace, true, false, out);
            } else {
                return PrqaCommandLine.getInstance().run(finalCommand, workspace, true, false, getEnv, out);
            }
        } catch (AbnormalProcessTerminationException abnex) {
            log.logp(Level.SEVERE, this.getClass().getName(), "upload()", "Logged error with upload", abnex);
            throw new PrqaUploadException("ERROR: Failed to upload, please check the upload command message above for more details", abnex);
        }
    }

    // __________________________________________________________________
    private String formatQacliPath() {
        if (environment.containsKey(QACli.QAF_BIN_PATH)) {
            return (QUOTE + environment.get(QACli.QAF_BIN_PATH) + FILE_SEPARATOR + QACli.QACLI + QUOTE);
        } else {
            return QACli.QACLI;
        }
    }

    public static String getNamingTemplate(PRQAContext.QARReportType type, String extension) {
        return type.toString() + " " + extension;
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

    public PRQAComplianceStatus getComplianceStatus(PrintStream out) throws PrqaException {
        PRQAComplianceStatus status = new PRQAComplianceStatus();
        status.setQaFrameworkVersion(qaFrameworkVersion);
        String report_structure;
        report_structure = new File(extractReportsPath(workspace.getAbsolutePath(), settings.getQaProject())).getPath();
        File reportFolder = new File(report_structure);
        out.println("Report Folder Path: " + reportFolder);

        File resultsDataFile = new File(reportFolder + File.separator + "results_data.xml");
        out.println("Results Data File Path: " + resultsDataFile.getPath());

        if (!reportFolder.exists()
                || !reportFolder.isDirectory()
                || !resultsDataFile.exists()
                || !resultsDataFile.isFile()) {
            return status;
        }

        File[] listOfReports = reportFolder.listFiles();
        if (listOfReports.length < 1) {
            return status;
        }

        Double fileCompliance = 0.0;
        Double projectCompliance = 0.0;
        int messages = 0;
        for (File reportFile : listOfReports) {
            if (reportFile.getName().contains(RCR.name())) {
                ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser(reportFile.getAbsolutePath());
                fileCompliance += Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFfileCompliancePattern));
                projectCompliance += Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFprojectCompliancePattern));
                messages += Integer.parseInt(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFtotalMessagesPattern));
            }
        }

        /*This section is to read result data file and parse the results*/
        ResultsDataParser resultsDataParser = new ResultsDataParser(resultsDataFile.getAbsolutePath());
        resultsDataParser.setQaFrameworkVersion(qaFrameworkVersion);
        List<MessageGroup> messagesGroups;
        try {
            messagesGroups = resultsDataParser.parseResultsData();
        } catch (Exception e) {
            throw new PrqaException(e);
        }
        sortViolatedRulesByRuleID(messagesGroups);
        status.setMessagesGroups(messagesGroups);
        status.setFileCompliance(fileCompliance);
        status.setProjectCompliance(projectCompliance);
        status.setMessages(messages);

        return status;
    }

    private void sortViolatedRulesByRuleID(List<MessageGroup> messagesGroups) {
        for (MessageGroup messageGroup : messagesGroups) {
            Collections.sort(messageGroup.getViolatedRules(), new Comparator<Rule>() {
                @Override
                public int compare(Rule o1, Rule o2) {
                    return o1.getRuleID().toString().compareTo(o2.getRuleID().toString());
                }
            });
        }
    }

    public void setWorkspace(File workspace) {
        this.workspace = workspace;
    }

    public QaFrameworkReportSettings getSettings() {
        return settings;
    }

    public void setSettings(QaFrameworkReportSettings settings) {
        this.settings = settings;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public PRQAApplicationSettings getAppSettings() {
        return appSettings;
    }

    public void setQaFrameworkVersion(QaFrameworkVersion qaFrameworkVersion) {
        this.qaFrameworkVersion = qaFrameworkVersion;
    }

    public boolean applyCustomLicenseServer(PrintStream out) throws PrqaException {

        if (!settings.isUseCustomLicenseServer()) {
            return false;
        }

        if (isCustomServerAlreadySet(out)) {
            return false;
        }

        String setLicenseServerCmd = createSetLicenseServersCmd();
        out.println("Perform SET CUSTOM LICENSE SERVER command:");
        out.println(setLicenseServerCmd);

        try {
            if (getEnvironment() == null) {
                PrqaCommandLine.getInstance().run(setLicenseServerCmd, workspace, true, false, out);
            } else {
                HashMap<String, String> systemVars = new HashMap<>();
                systemVars.putAll(System.getenv());
                systemVars.putAll(getEnvironment());
                PrqaCommandLine.getInstance().run(setLicenseServerCmd, workspace, true, false, systemVars, out);
            }
        } catch (AbnormalProcessTerminationException abnex) {
            throw new PrqaException(String.format("ERROR: Failed to set license server, message is... \n %s ", abnex.getMessage()), abnex);
        }

        return true;
    }

    public void unsetCustomLicenseServer(boolean wasApplied, PrintStream out) throws PrqaException {
        if (!wasApplied || !settings.isUseCustomLicenseServer()) {
            return;
        }

        String setLicenseServerCmd = createRemoveLicenseServersCmd();
        out.println("Perform REMOVE CUSTOM LICENSE SERVER command:");
        out.println(setLicenseServerCmd);

        try {
            if (getEnvironment() == null) {
                PrqaCommandLine.getInstance().run(setLicenseServerCmd, workspace, true, false, out);
            } else {
                HashMap<String, String> systemVars = new HashMap<>();
                systemVars.putAll(System.getenv());
                systemVars.putAll(getEnvironment());
                PrqaCommandLine.getInstance().run(setLicenseServerCmd, workspace, true, false, systemVars, out);
            }
        } catch (AbnormalProcessTerminationException abnex) {
            throw new PrqaException(String.format("ERROR: Failed to remove license , message is... \n %s ", abnex.getMessage()), abnex);
        }
    }

    private boolean isCustomServerAlreadySet(PrintStream out) throws PrqaException {

        String listLicenseServersCmd = createListLicenseServersCmd();
        out.println("Perform LIST LICENSE SERVERS command:");
        out.println(listLicenseServersCmd);

        CmdResult res;
        try {
            if (getEnvironment() == null) {
                res = PrqaCommandLine.getInstance().run(listLicenseServersCmd, workspace, false, true, out);
            } else {
                HashMap<String, String> systemVars = new HashMap<>();
                systemVars.putAll(System.getenv());
                systemVars.putAll(getEnvironment());
                res = PrqaCommandLine.getInstance().run(listLicenseServersCmd, workspace, false, true, systemVars, out);
            }
        } catch (AbnormalProcessTerminationException abnex) {
            throw new PrqaException(String.format("ERROR: Failed to list current servers, message is... \n %s ", abnex.getMessage()), abnex);
        }

        boolean contains = StringUtils.contains(res.stdoutBuffer.toString(), settings.getCustomLicenseServerAddress());
        if (contains) {
            out.println("Custom license server already set");
        } else {
            out.println("Custom license server not set");
        }
        return contains;
    }


    private String createListLicenseServersCmd() {
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("admin");
        builder.appendArgument("--list-license-servers");
        return builder.getCommand();
    }

    private String createSetLicenseServersCmd() {
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("admin");
        builder.appendArgument("--set-license-server");
        builder.appendArgument(settings.getCustomLicenseServerAddress());
        return builder.getCommand();
    }

    private String createRemoveLicenseServersCmd() {
        PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
        builder.appendArgument("admin");
        builder.appendArgument("--remove-license-server");
        builder.appendArgument(settings.getCustomLicenseServerAddress());
        return builder.getCommand();
    }
}
