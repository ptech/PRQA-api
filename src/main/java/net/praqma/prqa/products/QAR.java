/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.PRQAContext.QARReportType;
import net.praqma.prqa.logging.Config;
import net.praqma.util.execute.CmdResult;

/**
 * Reporting class.
 *
 * @author jes
 */
public class QAR extends PRQA {

	private String reportOutputPath;
	private String projectFile;
	private String product;
	private PRQACommandBuilder builder;
	private QARReportType type;
	public static final String QAW_WRAPPER = "qaw";
	private transient static final Logger logger;

	/**
	 * QAR is invoked using QAW where this is taken as parameter in the QAW command.
	 *
	 */
	static {
		logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
	}

	public QAR() {
		logger.log(Level.FINEST, "Constructor called for class QAR()");

		builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);

		logger.log(Level.FINEST, "Ending execution of constructor - QAR()");
	}

	public QAR(String product, String projectFile, QARReportType type) {
		logger.log(Level.FINEST, "Constructor called for class QAR(String product, String projectFile, QARReportType type)");
		logger.log(Level.FINEST, "Input parameter product type: {0}; value: {1}", new Object[]{product.getClass(), product});
		logger.log(Level.FINEST, "Input parameter projectFile type: {0}; value: {1}", new Object[]{projectFile.getClass(), projectFile});
		logger.log(Level.FINEST, "Input parameter type type: {0}; value: {1}", new Object[]{type.getClass(), type});

		this.builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
		this.product = product;
		this.projectFile = projectFile;
		this.type = type;

		logger.log(Level.FINEST, "Ending execution of constructor - QAR(String product, String projectFile, QARReportType type)");
	}

	public PRQACommandBuilder getBuilder() {
		logger.log(Level.FINEST, "Starting execution of method - getBuilder");
		logger.log(Level.FINEST, "Returning value: {0}", builder);

		return builder;
	}

	@Override
	public CmdResult execute() {
		logger.log(Level.FINEST, "Starting execution of method - execute()");

		CmdResult output = PRQACommandLineUtility.run(getBuilder().getCommand(), new File(commandBase));
		
		logger.log(Level.FINEST, "Returning value: {0}", output);
		
		return output;
	}

	public void setReportOutputPath(String reportOutputPath) {
		logger.log(Level.FINEST, "Starting execution of method - setReportOutputPath");
		logger.log(Level.FINEST, "Input parameter reportOutputPath type: {0}; value: {1}", new Object[]{reportOutputPath.getClass(), reportOutputPath});

		this.reportOutputPath = reportOutputPath;

		logger.log(Level.FINEST, "Ending execution of method - setReportOutputPath");
	}

	public String getReportOutputPath() {
		logger.log(Level.FINEST, "Starting execution of method - getReportOutputPath");
		logger.log(Level.FINEST, "Returning value: {0}", this.reportOutputPath);

		return this.reportOutputPath;
	}

	public String getProduct() {
		logger.log(Level.FINEST, "Starting execution of method - getProduct");
		logger.log(Level.FINEST, "Returning value: {0}", this.product);

		return this.product;
	}

	public void setProduct(String product) {
		logger.log(Level.FINEST, "Starting execution of method - setProduct");
		logger.log(Level.FINEST, "Input parameter product type: {0}; value: {1}", new Object[]{product.getClass(), product});

		this.product = product;

		logger.log(Level.FINEST, "Ending execution of method - setProduct");
	}

	public String getProjectFile() {
		logger.log(Level.FINEST, "Starting execution of method - getProjectFile");
		logger.log(Level.FINEST, "Returning value: {0}", this.projectFile);

		return this.projectFile;
	}

	public void setProjectFile(String projectFile) {
		logger.log(Level.FINEST, "Starting execution of method - setProjectFile");
		logger.log(Level.FINEST, "Input parameter projectFile type: {0}; value: {1}", new Object[]{projectFile.getClass(), projectFile});

		this.projectFile = projectFile;

		logger.log(Level.FINEST, "Ending execution of method - setProjectFile");
	}

	@Override
	public String toString() {
		String out = "";
		out += "QAR selected project file:\t" + this.projectFile + System.getProperty("line.separator");
		out += "QAR selected product:\t\t" + this.product + System.getProperty("line.separator");
		out += "QAR selected report type:\t" + this.type + System.getProperty("line.separator");

		return out;
	}

	/**
	 * @return the type
	 */
	public QARReportType getType() {
		logger.log(Level.FINEST, "Starting execution of method - getType");
		logger.log(Level.FINEST, "Returning value: {0}", type);

		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(QARReportType type) {
		logger.log(Level.FINEST, "Starting execution of method - setType");
		logger.log(Level.FINEST, "Input parameter type type: {0}; value: {1}", new Object[]{type.getClass(), type});

		this.type = type;

		logger.log(Level.FINEST, "Ending execution of method - setType");
	}
}
