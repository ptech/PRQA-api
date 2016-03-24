package net.prqma.prqa.qaframework;
import java.io.Serializable;

import net.praqma.prqa.ReportSettings;
import net.praqma.prqa.QAVerifyServerSettings;

public class QaFrameworkReportSettings implements ReportSettings, Serializable {

    private String qaInstallation;
    private String qaProject;
    private String uniProjectName;
    private boolean pullUnifiedProject;
    private boolean qaEnableDependencyMode;
    private boolean qaEnableMtr;
    private boolean qaEnableProjectCma;
    private boolean qaCrossModuleAnalysis;
    private boolean generateReport;
    private boolean publishToQAV;
    private boolean loginToQAV;
    private String product;
    private boolean qaUploadWhenStable;
    private String qaVerifyProjectName;
    private String uploadSnapshotName;
    private String buildNumber;
    private String uploadSourceCode;
    private boolean genCrReport;
    private boolean genMdReport;
    private boolean genSupReport;
    private boolean analysisSettings;
    private boolean stopWhenFail;
    private boolean generatePreprocess;
    private boolean assembleSupportAnalytics;

    /**
     *
     * @param qaInstallation
     * @param qaProject
     * @param pullUnifiedProject
     * @param uniProjectName
     * @param qaEnableMtr
     * @param qaEnableProjectCma
     * @param qaEnableDependencyMode
     * @param qaCrossModuleAnalysis
     * @param generateReport
     * @param publishToQAV
     * @param loginToQAV
     * @param product
     * @param qaUploadWhenStable
     * @param qaVerifyProjectName
     * @param uploadSnapshotName
     * @param buildNumber
     * @param uploadSourceCode
     * @param genCrReport
     * @param genMdReport
     * @param genSupReport
     *
     * TODO - We should create a builder for this object to make the creation
     * more readable.
     */
    public QaFrameworkReportSettings(String qaInstallation, String qaProject, boolean pullUnifiedProject, String uniProjectName,
            boolean qaEnableMtr, boolean qaEnableDependencyMode, boolean qaCrossModuleAnalysis,
            boolean generateReport, boolean publishToQAV, boolean loginToQAV, String product, boolean qaUploadWhenStable, String qaVerifyProjectName,
            String uploadSnapshotName, String buildNumber, String uploadSourceCode, boolean genCrReport, boolean genMdReport, boolean genSupReport,
            boolean analysisSettings, boolean stopWhenFail, boolean generatePreprocess, boolean assembleSupportAnalytics) {

        this.qaInstallation = qaInstallation;
        this.uniProjectName = uniProjectName;
        this.pullUnifiedProject = pullUnifiedProject;
        this.qaCrossModuleAnalysis = qaCrossModuleAnalysis;
        this.publishToQAV = publishToQAV;
        this.loginToQAV = loginToQAV;
        this.qaEnableMtr = qaEnableMtr;
        this.qaEnableDependencyMode = qaEnableDependencyMode;
        this.generateReport = generateReport;
        this.qaProject = qaProject;
        this.product = product;
        this.qaUploadWhenStable = qaUploadWhenStable;
        this.qaVerifyProjectName = qaVerifyProjectName;
        this.uploadSnapshotName = uploadSnapshotName;
        this.buildNumber = buildNumber;
        this.uploadSourceCode = uploadSourceCode;
        this.genMdReport = genMdReport;
        this.genCrReport = genCrReport;
        this.genSupReport = genSupReport;
        this.analysisSettings = analysisSettings;
        this.stopWhenFail = stopWhenFail;
        this.generatePreprocess = generatePreprocess;
        this.assembleSupportAnalytics = assembleSupportAnalytics;

    }

    @Override
    public String getProduct() {
        return "";
    }

    @Override
    public boolean publishToQAV() {
        return publishToQAV;
    }

    public boolean loginToQAV() {
        return loginToQAV;
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

    public boolean isQaEnableProjectCma() {
        return qaEnableProjectCma;
    }

    public boolean isQaEnableMtr() {
        return qaEnableMtr;
    }

    public boolean isQaCrossModuleAnalysis() {
        return qaCrossModuleAnalysis;
    }

    public boolean isPullUnifiedProject() {
        return pullUnifiedProject;
    }

    public String getUniProjectName() {
        return uniProjectName;
    }

    public boolean isGenerateReport() {
        return generateReport;
    }

    public boolean isPublishToQAV() {
        return publishToQAV;
    }

    public boolean isLoginToQAV() {
        return loginToQAV;
    }

    public boolean isQaUploadWhenStable() {
        return qaUploadWhenStable;
    }

    public String getQaVerifyProjectName() {
        return qaVerifyProjectName;
    }

    public void setQaVerifyProjectName(String qaVerifyProjectName) {
        this.qaVerifyProjectName = qaVerifyProjectName;
    }

    public String getUploadSnapshotName() {
        return uploadSnapshotName;
    }

    public void setUploadSnapshotName(String uploadSnapshotName) {
        this.uploadSnapshotName = uploadSnapshotName;
    }

    public String getbuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getUploadSourceCode() {
        return uploadSourceCode;
    }

    public void setUploadSourceCode(String uploadSourceCode) {
        this.uploadSourceCode = uploadSourceCode;
    }

    public boolean isGenCrReport() {
        return genCrReport;
    }

    public boolean isGenMdReport() {
        return genMdReport;
    }

    public boolean isGenSupReport() {
        return genSupReport;
    }

    public void setGenCrReport(boolean genCrReport) {
        this.genCrReport = genCrReport;
    }

    public void setGenMdReport(boolean genMdReport) {
        this.genMdReport = genMdReport;
    }

    public void setGenSupReport(boolean genSupReport) {
        this.genSupReport = genSupReport;
    }

    public boolean isAnalysisSettings() {
        return analysisSettings;
    }

    public boolean isStopWhenFail() {
        return stopWhenFail;
    }

    public boolean isGeneratePreprocess() {
        return generatePreprocess;
    }

    public boolean isAssembleSupportAnalytics() {
        return assembleSupportAnalytics;
    }

    public void setAnalysisSettings(boolean analysisSettings) {
        this.analysisSettings = analysisSettings;
    }

    public void setStopWhenFail(boolean stopWhenFail) {
        this.stopWhenFail = stopWhenFail;
    }

    public void setGeneratePreprocess(boolean generatePreprocess) {
        this.generatePreprocess = generatePreprocess;
    }

    public void setAssembleSupportAnalytics(boolean assembleSupportAnalytics) {
        this.assembleSupportAnalytics = assembleSupportAnalytics;
    }
}
