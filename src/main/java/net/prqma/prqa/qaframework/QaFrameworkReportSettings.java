package net.prqma.prqa.qaframework;

import net.praqma.prqa.ReportSettings;

public class QaFrameworkReportSettings implements ReportSettings {

	private String qaInstallation;
	private String qaProject;
	private boolean qaEnableDependencyMode;
	private boolean qaCrossModuleAnalysis;
	private String cmaProjectName;
	private boolean generateReport;
	private boolean publishToQAV;
	private String qaVerifyConfigFile;
	private String vcsConfigXml;
	private String product;
    private String qaVerifyProjectName;

	public QaFrameworkReportSettings(String qaInstallation, String qaProject, boolean qaEnableDependencyMode, boolean qaCrossModuleAnalysis,
			String cmaProjectName, boolean generateReport, boolean publishToQAV, String qaVerifyConfigFile, String vcsConfigXml, String product, String qaVerifyProjectName) {

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
                this.qaVerifyProjectName = qaVerifyProjectName;
	}

	@Override
	public String getProduct() {
		return "";
	}

	@Override
	public boolean publishToQAV() {
		return publishToQAV;
	}

	public String getQaInstallation() {
		return qaInstallation;
	}

	public String getQaProject() {
		return qaProject;
	}

	public boolean isQaEnableDependencyMode() {
		return qaEnableDependencyMode;
	}

	public boolean isQaCrossModuleAnalysis() {
		return qaCrossModuleAnalysis;
	}

	public String getCmaProjectName() {
		return cmaProjectName;
	}

	public boolean isGenerateReport() {
		return generateReport;
	}

	public boolean isPublishToQAV() {
		return publishToQAV;
	}

	public String getQaVerifyConfigFile() {
		return qaVerifyConfigFile;
	}

	public String getVcsConfigXml() {
		return vcsConfigXml;
	}
        
	public String getQaVerifyProjectName() {
		return qaVerifyProjectName;
	}
        
	public void setQaVerifyProjectName(String qaVerifyProjectName) {
		this.qaVerifyProjectName = qaVerifyProjectName;
	}
        
}