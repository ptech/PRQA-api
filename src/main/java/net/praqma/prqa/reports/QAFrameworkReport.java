package net.praqma.prqa.reports;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
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

	public QAFrameworkReport(QaFrameworkReportSettings settings, QAVerifyServerSettings qaVerifySettings, PRQAApplicationSettings appSettings) {
		this.settings = settings;
		this.appSettings = appSettings;
		this.qaVerifySettings = qaVerifySettings;
	}

	public QAFrameworkReport(QaFrameworkReportSettings settings, QAVerifyServerSettings qaVerifySettings, PRQAApplicationSettings appSettings,
			HashMap<String, String> environment) {
		this.settings = settings;
		this.environment = environment;
		this.appSettings = appSettings;
		this.qaVerifySettings = qaVerifySettings;
	}

	private String createAnalysisCommandForQacli(boolean isUnix) throws PrqaException {
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("analyze");
		String analyzeOptions = "-fc";
		if (settings.qaEnableDependencyMode) {
			analyzeOptions = analyzeOptions.replace("c", "");
		}
		builder.appendArgument(analyzeOptions);
		builder.appendArgument("-P");
		builder.appendArgument(PRQACommandBuilder.getProjectFile(resolveAbsOrRelativePath(workspace, settings.qaProject)));
		return builder.getCommand();
	}

	public CmdResult analyzeQacli(boolean isUnix, PrintStream out) throws PrqaException {
		String finalCommand = createAnalysisCommandForQacli(isUnix);
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

	private String createCmaAnalysisCommand(boolean isUnix) throws PrqaException {

		if (StringUtils.isBlank(settings.cmaProjectName)) {
			throw new PrqaException("Perform Cross-Module analysis was selected but no CMA project was provided. The analysis project was aborted.");
		}
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("analyze");
		builder.appendArgument("-C");
		builder.appendArgument(settings.cmaProjectName);
		return builder.getCommand();
	}

	public CmdResult cmaAnalysisQacli(boolean isUnix, PrintStream out) throws PrqaException {
		CmdResult res = null;
		if (settings.qaCrossModuleAnalysis) {
			String command = createCmaAnalysisCommand(isUnix);
			out.println("Perform Cross-Module analysis command:");
			out.println(command);
			try {
				res = CommandLine.getInstance().run(command, workspace, true, false);
			} catch (AbnormalProcessTerminationException abnex) {
				throw new PrqaException(String.format("Failed to analyze, message was:\n %s", abnex.getMessage()), abnex);
			}
		}
		return res;
	}

	public List<CmdResult> reportQacli(boolean isUnix, PrintStream out) throws PrqaException {
		List<String> reportCommands = createReportCommandForQacli(isUnix);
		Map<String, String> systemVars = new HashMap<String, String>();
		systemVars.putAll(System.getenv());
		systemVars.putAll(getEnvironment());
		List<CmdResult> cmdResults = new ArrayList<CmdResult>();
		for (String finalCommand : reportCommands) {
			out.println("Report command :" + finalCommand);
			try {
				PRQAReport._logEnv("Current report generation execution environment", systemVars);
				cmdResults.add(CommandLine.getInstance().run(finalCommand, workspace, true, false, systemVars));
			} catch (AbnormalProcessTerminationException abnex) {
				log.severe(String.format("Failed to execute report generation command: %s%n%s", finalCommand, abnex.getMessage()));
				log.logp(Level.SEVERE, this.getClass().getName(), "report()", "Failed to execute report generation command", abnex);
				out.println(String.format("Failed to execute report generation command: %s%n%s", finalCommand, abnex.getMessage()));
				// throw new
				// PrqaException(String.format("Failed to execute report generation command: %s%n%s",
				// finalCommand, abnex.getMessage()), abnex);
			}
		}
		return cmdResults;
	}

	private List<String> createReportCommandForQacli(boolean isUnix) throws PrqaException {
		String projectLocation;
		if (!StringUtils.isBlank(settings.qaProject)) {
			projectLocation = PRQACommandBuilder.getProjectFile(resolveAbsOrRelativePath(workspace, settings.qaProject));
		} else {
			throw new PrqaException("Report source not configured (Project File/File List)");
		}
		removeOldReports(projectLocation.replace(QUOTE, "").trim());
		List<String> reportCommands = new ArrayList<String>();
		List<String> reportTypes = new ArrayList<String>();
		if (settings.generateReport) {
			reportTypes.add("C");
			reportTypes.add("CPP");
		}
		for (String reportType : reportTypes) {
			PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
			builder.appendArgument("report -P");
			builder.appendArgument(projectLocation);
			builder.appendArgument("-l");
			builder.appendArgument(reportType);
			builder.appendArgument("-t RCR");
			reportCommands.add(builder.getCommand());
		}
		return reportCommands;
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

	private String createUploadCommandQacli() throws PrqaException {
		String projectLocation;
		if (!StringUtils.isBlank(settings.qaProject)) {
			projectLocation = PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace, settings.qaProject));
		} else {
			throw new PrqaException("Neither filelist or project file has been set, this should not be happening");
		}
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("upload -P");
		builder.appendArgument(projectLocation);
		builder.appendArgument("--qav-upload");
		return builder.getCommand();
	}

	private String createAddUploadConfigurationFilesToProjectCommand() throws PrqaException {
		PRQACommandBuilder builder = new PRQACommandBuilder(formatQacliPath());
		builder.appendArgument("admin --qaf-project-config -P");
		builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace, settings.qaProject)));
		builder.appendArgument("-Q");
		builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace, settings.qaVerifyConfigFile)));
		builder.appendArgument("-V");
		builder.appendArgument(PRQACommandBuilder.wrapInQuotationMarks(resolveAbsOrRelativePath(workspace, settings.vcsConfigXml)));
		return builder.getCommand();
	}

	public CmdResult uploadQacli(PrintStream out) throws PrqaUploadException, PrqaException {
		CmdResult res = null;
		if (!settings.publishToQAV) {
			return res;
		}
		String finalCommand = createUploadCommandQacli();
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

	public CmdResult addUploadConfigurationFilesToProject(PrintStream out) throws PrqaException, JDOMException, IOException {
		CmdResult res = null;
		if (!checkIfCanUpload()) {
			return res;
		}
		setQaVerifyServerSettings();
		String configProjectCommand = createAddUploadConfigurationFilesToProjectCommand();
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
		if (!settings.publishToQAV) {
			return canUploadProject;
		}
		if (StringUtils.isBlank(settings.qaProject) || StringUtils.isBlank(settings.qaVerifyConfigFile) || StringUtils.isBlank(settings.vcsConfigXml)) {
			return canUploadProject;
		}
		return true;
	}

	private void setQaVerifyServerSettings() throws PrqaException, JDOMException, IOException {
		QaVerifyConfigurationFileParser qaVFileParser = new QaVerifyConfigurationFileParser(settings.qaVerifyConfigFile);
		String projectWithOsFilePaths = FilenameUtils.separatorsToSystem(settings.qaProject);
		qaVFileParser
				.changeQaVerifyConfiFileSettings(
						qaVerifySettings,
						projectWithOsFilePaths.substring(projectWithOsFilePaths.lastIndexOf(System.getProperty("file.separator")) + 1,
								projectWithOsFilePaths.length()));
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
	private String resolveAbsOrRelativePath(File workspaceRoot, String projectFilePath) throws PrqaException {
		File pFile = new File(projectFilePath);
		if (pFile.isAbsolute()) {
			if (!pFile.exists()) {
				throw new PrqaException(String.format("The project file %s does not exist.", projectFilePath));
			} else {
				return projectFilePath;
			}
		} else {
			File relative = new File(workspaceRoot, projectFilePath);
			if (relative.exists()) {
				return relative.getPath();
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

	public PRQAComplianceStatus getComplianceStatus() throws PrqaException, Exception {
		PRQAComplianceStatus status = new PRQAComplianceStatus();

		File reportFolder = new File(settings.qaProject + "/prqa/reports/");
		File resultsDataFile = new File(settings.qaProject + "/prqa/output/results_data.xml");

		if (!reportFolder.exists() || !reportFolder.isDirectory() || !resultsDataFile.exists() || !resultsDataFile.isFile()) {
			return status;
		}

		File[] listOfReports = reportFolder.listFiles();
		if(listOfReports.length <1){
			return status;
		}
		
		Double fileCompliance = 0.0;
		Double projectCompliance = 0.0;
		int messages = 0;
		
		String fileExtension = FilenameUtils.getExtension(listOfReports[0].getPath().toString());
		if (fileExtension.equals(XHTML) || fileExtension.equals(HTML)) {
			ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser(listOfReports[0].getAbsolutePath());
			fileCompliance += Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFfileCompliancePattern));
			projectCompliance += Double.parseDouble(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFprojectCompliancePattern));
			messages += Integer.parseInt(parser.getParseFirstResult(ComplianceReportHtmlParser.QAFtotalMessagesPattern));
		}

		ResultsDataParser resultsDataParser = new ResultsDataParser(resultsDataFile.getAbsolutePath());
		List<MessageGroup> messagesGroups = resultsDataParser.parseResultsData();
		sortViolatedRulesByRuleID(messagesGroups);

		status.setFileCompliance(fileCompliance);
		status.setProjectCompliance(projectCompliance);
		status.setMessages(messages);
		status.setMessagesGroups(messagesGroups);
		return status;
	}

	private void sortViolatedRulesByRuleID(List<MessageGroup> messagesGroups) {
		for (MessageGroup messageGroup : messagesGroups) {
			Collections.sort(messageGroup.getViolatedRules(), new Comparator<Rule>() {
				@Override
				public int compare(Rule o1, Rule o2) {
					if (o1.getRuleID() > o2.getRuleID()) {
						return 1;
					}
					if (o1.getRuleID() < o2.getRuleID()) {
						return -1;
					}
					return 0;
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
}
