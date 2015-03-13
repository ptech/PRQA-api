/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.praqma.prqa.exceptions.PrqaSetupException;
import net.praqma.prqa.reports.PRQAReport;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;

/**
 * 
 * @author Alexandru Ion
 */
public class QACli implements Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(QACli.class.getName());
	public static final String QAF_BIN_PATH = "QAFBINPATH";
	public static final String WORKSPACE_PATH = "WORKSPACEPATH";
	public static final String QAF_INSTALL_PATH = "QAFINSTALLPATH";

	@Override
	public final String getProductVersion(Map<String, String> environment, File workspace, boolean isUnix) throws PrqaSetupException {
		logger.finest(String.format("Starting execution of method - getProductVersion()"));

		String productVersion = null;
		CmdResult res = null;
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		if (environment.containsKey(QAF_BIN_PATH)) {
			sb.append(environment.get(QAF_BIN_PATH));
			sb.append(System.getProperty("file.separator"));
		}
		sb.append("qacli\" --version");

		try {
			res = CommandLine.getInstance().run(sb.toString(), workspace, true, false, environment);
			StringBuffer strBuffer = res.stdoutBuffer;
			productVersion = strBuffer.substring(strBuffer.indexOf(":") + 1, strBuffer.length()).trim();

		} catch (AbnormalProcessTerminationException abnex) {
			logger.warning(String.format("Failed to detect QA·CLI version with command %s returned code %s%nMessage was:%n%s", abnex.getCommand(),
					abnex.getExitValue(), abnex.getMessage()));
			Map<String, String> systemVars = new HashMap<String, String>();
			systemVars.putAll(System.getenv());

			if (environment != null) {
				systemVars.putAll(environment);
			}
			PRQAReport._logEnv("Error in QACLI.getProductVersion() - Printing environment", systemVars);
			throw new PrqaSetupException(String.format("Failed to detect QA·CLI version%n%s", abnex.getMessage()));

		}
		logger.finest(String.format("Returning value %s", productVersion));
		return productVersion;
	}
}