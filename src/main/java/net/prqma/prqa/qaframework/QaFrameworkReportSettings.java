package net.prqma.prqa.qaframework;

import net.praqma.prqa.ReportSettings;

public class QaFrameworkReportSettings implements ReportSettings {

	// public final String chosenServer;
	public final String qaInstallation;
	public final String qaProject;
	// public final boolean qaVerify;

	// Analysis options
	public final boolean qaEnableDependencyMode;
	public final boolean qaCrossModuleAnalysis;
	public final String cmaProjectName;

	public final boolean generateReport;

	public final boolean publishToQAV;
	public String qaVerifyConfigFile;
	public String vcsConfigXml;
	public final String product;

	public QaFrameworkReportSettings(String qaInstallation, String qaProject, boolean qaEnableDependencyMode, boolean qaCrossModuleAnalysis,
			String cmaProjectName, boolean generateReport, boolean publishToQAV, String qaVerifyConfigFile, String vcsConfigXml, String product) {

		this.qaInstallation = qaInstallation;
		this.qaCrossModuleAnalysis = qaCrossModuleAnalysis;
		this.cmaProjectName = cmaProjectName;
		this.publishToQAV = publishToQAV;
		this.qaEnableDependencyMode = qaEnableDependencyMode;

		this.generateReport = generateReport;

		this.qaProject = qaProject;
		this.product = product;
		this.qaVerifyConfigFile = qaVerifyConfigFile;
		this.vcsConfigXml = vcsConfigXml;
	}

	@Override
	public String getProduct() {
		return "";
		// return product;
	}

	@Override
	public boolean publishToQAV() {
		return publishToQAV;
	}
}
