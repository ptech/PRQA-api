package net.praqma.prqa.reports;

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

import net.praqma.prqa.PRQAApplicationSettings;
import net.praqma.prqa.PRQAContext;
import net.praqma.prqa.QAVerifyServerSettings;
import net.praqma.prqa.QaFrameworkVersion;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.exceptions.PrqaUploadException;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.parsers.MessageGroup;
import net.praqma.prqa.parsers.QaVerifyConfigurationFileParser;
import net.praqma.prqa.parsers.ResultsDataParser;
import net.praqma.prqa.parsers.Rule;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QACli;
import net.praqma.prqa.status.PRQAComplianceStatus;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;
import net.prqma.prqa.qaframework.QaFrameworkReportSettings;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom2.JDOMException;

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

	public CmdResult analyzeQacli(boolean isUnix, PrintStream out) throws PrqaException {
		String finalCommand = createAnalysisCommandForQacli(isUnix, out);
		out.println("Analysis command:");
		out.println(finalCommand);
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
				res = CommandLine.getInstance().run(finalCommand, workspace, true, false, systemVars);
			}
		} catch (AbnormalProcessTerminationException abnex) {
			throw new PrqaException(String.format("Failed to analyze, message was:\n %s", abnex.getMessage()), abnex);
		}
		return res;
	}

	private String createAnalysisCommandForQacli(boolean isUnix, PrintStream out) throws PrqaException {
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("analyze");
		String analyzeOptions = "-fc";
		if (settings.isQaEnableDependencyMode()) {
			analyzeOptions = analyzeOptions.replace("c", "");
		}
		builder.appendArgument(analyzeOptions);
		builder.appendArgument("-P");
		builder.appendArgument(PRQACommandBuilder.getProjectFile(resolveAbsOrRelativePath(workspace,
				settings.getQaProject(), out)));
		return builder.getCommand();
	}

	public CmdResult cmaAnalysisQacli(boolean isUnix, PrintStream out) throws PrqaException {
		CmdResult res = null;
		if (settings.isQaCrossModuleAnalysis()) {
			String command = createCmaAnalysisCommand(isUnix);
			out.println("Perform Cross-Module analysis command:");
			out.println(command);
			try {
				res = CommandLine.getInstance().run(command, workspace, true, false);
			} catch (AbnormalProcessTerminationException abnex) {
				throw new PrqaException(String.format("Failed to analyze, message was:\n %s", abnex.getMessage()),
						abnex);
			}
		}
		return res;
	}

	private String createCmaAnalysisCommand(boolean isUnix) throws PrqaException {

		if (StringUtils.isBlank(settings.getCmaProjectName())) {
			throw new PrqaException(
					"Perform Cross-Module analysis was selected but no CMA project was provided. The analysis project was aborted.");
		}
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("analyze");
		builder.appendArgument("-C");
		builder.appendArgument(settings.getCmaProjectName());
		return builder.getCommand();
	}

	public CmdResult reportQacli(boolean isUnix, PrintStream out) throws PrqaException {

		String reportCommand = createReportCommandForQacli(isUnix, out);
		Map<String, String> systemVars = new HashMap<String, String>();
		systemVars.putAll(System.getenv());
		systemVars.putAll(getEnvironment());

		out.println("Report command :" + reportCommand);
		try {
			PRQAReport._logEnv("Current report generation execution environment", systemVars);
			return CommandLine.getInstance().run(reportCommand, workspace, true, false, systemVars);
		} catch (AbnormalProcessTerminationException abnex) {
			log.severe(String.format("Failed to execute report generation command: %s%n%s", reportCommand,
					abnex.getMessage()));
			log.logp(Level.SEVERE, this.getClass().getName(), "report()",
					"Failed to execute report generation command", abnex);
			out.println(String.format("Failed to execute report generation command: %s%n%s", reportCommand,
					abnex.getMessage()));
		}
		return new CmdResult();
	}

	private String createReportCommandForQacli(boolean isUnix, PrintStream out) throws PrqaException {
		out.println("Create report command");
		//out.println("settings.getQaProject():" + settings.getQaProject());
		String projectLocation;

		projectLocation = PRQACommandBuilder.getProjectFile(resolveAbsOrRelativePath(workspace,
				settings.getQaProject(), out));
		out.println("PROJECT LOCATION: " + projectLocation);

		removeOldReports(projectLocation.replace(QUOTE, "").trim());
		out.println("After remove old reports");
		return createReportCommand(projectLocation, out);
	}

	private String createReportCommand(String projectLocation, PrintStream out) {
		out.println("Create report command");
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("report -P");
		builder.appendArgument(projectLocation);
		if (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion4()) {
			builder.appendArgument("-l C");
		}
		builder.appendArgument("-t RCR");
		return builder.getCommand();
	}

	private void removeOldReports(String projectLocation) {
		String reportsPath = "/prqa/reports";
		File file = new File(projectLocation + reportsPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				f.delete();
			}
		}
	}

	private String createUploadCommandQacli(PrintStream out) throws PrqaException {
		String projectLocation;
		if (!StringUtils.isBlank(settings.getQaVerifyProjectName())) {
			projectLocation = PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace,
					settings.getQaProject(), out));
		} else {
			throw new PrqaException("Neither filelist or project file has been set, this should not be happening");
		}
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("upload -P");
		builder.appendArgument(projectLocation);
		builder.appendArgument("--qav-upload");
		return builder.getCommand();
	}

	private String createAddUploadConfigurationFilesToProjectCommand(PrintStream out) throws PrqaException {
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("admin --qaf-project-config -P");
		builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace,
				settings.getQaProject(), out)));
		builder.appendArgument("-Q");
		builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace,
				settings.getQaVerifyConfigFile(), out)));
		builder.appendArgument("-V");
		builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace,
				settings.getVcsConfigXml(), out)));
		return builder.getCommand();
	}

	public CmdResult uploadQacli(PrintStream out) throws PrqaUploadException, PrqaException {
		CmdResult res = null;
		if (!settings.isPublishToQAV()) {
			return res;
		}
		String finalCommand = createUploadCommandQacli(out);
		out.println("Upload command: " + finalCommand);
		try {
			if (getEnvironment() == null) {
				res = CommandLine.getInstance().run(finalCommand, workspace, true, false);
			} else {
				res = CommandLine.getInstance().run(finalCommand, workspace, true, false, getEnvironment());
			}
		} catch (AbnormalProcessTerminationException abnex) {
			log.logp(Level.SEVERE, this.getClass().getName(), "upload()", "Logged error with upload", abnex);
			throw new PrqaUploadException(String.format("Upload failed with message:%n%s", abnex.getMessage()), abnex);
		}

		return res;
	}

	public CmdResult addUploadConfigurationFilesToProject(PrintStream out) throws PrqaException, JDOMException,
			IOException {
		CmdResult res = null;
		if (!checkIfCanUpload()) {
			return res;
		}
		setQaVerifyServerSettings();
		String configProjectCommand = createAddUploadConfigurationFilesToProjectCommand(out);
		out.println("Project upload configuration command: " + configProjectCommand);
		try {
			if (getEnvironment() == null) {
				res = CommandLine.getInstance().run(configProjectCommand, workspace, true, false);
			} else {
				res = CommandLine.getInstance().run(configProjectCommand, workspace, true, false, getEnvironment());
			}
		} catch (AbnormalProcessTerminationException abnex) {
			log.logp(Level.SEVERE, this.getClass().getName(), "upload()", "Logged error with upload", abnex);
			throw new PrqaUploadException(String.format("Upload failed with message:%n%s", abnex.getMessage()), abnex);
		}
		return res;
	}

	private boolean checkIfCanUpload() {
		boolean canUploadProject = false;
		if (!settings.isPublishToQAV()) {
			return canUploadProject;
		}
		if (StringUtils.isBlank(settings.getQaVerifyProjectName()) 
		    || StringUtils.isBlank(settings.getQaVerifyConfigFile()) || StringUtils.isBlank(settings.getVcsConfigXml())) {
			return canUploadProject;
		}
		return true;
	}

	private void setQaVerifyServerSettings() throws PrqaException, JDOMException, IOException {
		QaVerifyConfigurationFileParser qaVFileParser = new QaVerifyConfigurationFileParser(
				settings.getQaVerifyConfigFile());
                String projName = settings.getQaVerifyProjectName();
                qaVFileParser.changeQaVerifyConfiFileSettings(qaVerifySettings, projName );
	}

	// __________________________________________________________________

	private String formatQacliPath() {
		String qacliPath;
		if (environment.containsKey(QACli.QAF_BIN_PATH)) {
			qacliPath = QUOTE + environment.get(QACli.QAF_BIN_PATH) + FILE_SEPARATOR + "qacli" + QUOTE;
		} else {
			qacliPath = "qacli";
		}
		return qacliPath;
	}

	public static String getNamingTemplate(PRQAContext.QARReportType type, String extension) {
		return type.toString() + " " + extension;
	}

	/**
	 * Resolves the project file location. This can be either absolute or
	 * relative to the current workspace
	 * 
	 * @param workspaceRoot
	 * @param projectFilePath
	 * @return
	 * @throws PrqaException
	 */
	private String resolveAbsOrRelativePath(File workspaceRoot, String projectFilePath, PrintStream outStream)
			throws PrqaException {
		outStream.println("The selected project is:" + projectFilePath);
		outStream.println("worksapce root" + workspaceRoot);
		File pFile = new File(projectFilePath);
		if (pFile.isAbsolute()) {
			outStream.println("File is absolute");
			if (!pFile.exists()) {
				throw new PrqaException(String.format("The project file %s does not exist.", projectFilePath));
			} else {
				outStream.println("Return is absolute: " + projectFilePath);
				return projectFilePath;
			}
		} else {
			outStream.println("File is relative");
			File relative = new File(workspaceRoot, projectFilePath);
			if (relative.exists()) {
				String path = relative.getPath();
				outStream.println("File is relative with path: " + path);
				return path;
			} else {
				throw new PrqaException(String.format("The project file %s does not exist.", relative.getPath()));
			}
		}
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

	public PRQAComplianceStatus getComplianceStatus(PrintStream out) throws PrqaException, Exception {
		PRQAComplianceStatus status = new PRQAComplianceStatus();
        status.setQaFrameworkVersion(qaFrameworkVersion);
		String projectLocation;
		String report_structure;
		report_structure = new File("prqa/", "/reports").getPath();

		projectLocation = resolveAbsOrRelativePath(workspace, settings.getQaProject(), out);
		File reportFolder = new File(projectLocation, report_structure);
		out.println("Report Folder Path:: " + reportFolder);
		
		File resultsDataFile = new File(projectLocation, getResultsDataFileRelativePath());
		out.println("RESULTS DATA file path: " + resultsDataFile.getPath());

		if (!reportFolder.exists() || !reportFolder.isDirectory() || !resultsDataFile.exists()
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
        boolean PRIOR_QAF104 = (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion4());
        for (int i = 0; i < listOfReports.length; i++){
             if (listOfReports[i].isFile()) {
                 String fileExtension = FilenameUtils.getExtension(listOfReports[i].getPath().toString());
                 if (fileExtension.equals(XHTML) || fileExtension.equals(HTML)){
                    ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser(listOfReports[i].getAbsolutePath());
                    fileCompliance += Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFfileCompliancePattern));
                    projectCompliance += Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFprojectCompliancePattern));
                    messages += Integer.parseInt(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFtotalMessagesPattern));
                 }
            }
        }
		
        
        /*This section is to read result data file and parse the results*/
        ResultsDataParser resultsDataParser = new ResultsDataParser(resultsDataFile.getAbsolutePath());
        resultsDataParser.setQaFrameworkVersion(qaFrameworkVersion);
        List<MessageGroup> messagesGroups = resultsDataParser.parseResultsData();
        sortViolatedRulesByRuleID(messagesGroups);
        status.setMessagesGroups(messagesGroups);
        status.setFileCompliance(fileCompliance);
        status.setProjectCompliance(projectCompliance);
        status.setMessages(messages);

		return status;
	}

	private String getResultsDataFileRelativePath() {
		String relativePath = "/prqa/";
		String resultsDataFileName = "results_data.xml";
		if (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion4()) {
			relativePath += "output/";
		} else {
			relativePath += "reports/";
		}
		return relativePath += resultsDataFileName;

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


	/**
	 * @param workspace
	 *            the workspace to set
	 */
	public void setWorkspace(File workspace) {
		this.workspace = workspace;
	}

	/**
	 * @return the settings
	 */
	public QaFrameworkReportSettings getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(QaFrameworkReportSettings settings) {
		this.settings = settings;
	}

	/**
	 * @return the environment
	 */
	public Map<String, String> getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
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
	 * @return the qaFrameworkVersion
	 */
	public void setQaFrameworkVersion(QaFrameworkVersion qaFrameworkVersion) {
		this.qaFrameworkVersion = qaFrameworkVersion;
	}
}