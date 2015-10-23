package net.prqma.prqa.qaframework;

import net.praqma.prqa.ReportSettings;
import net.praqma.prqa.QAVerifyServerSettings;

public class QaFrameworkReportSettings implements ReportSettings {

    private String qaInstallation;
    private String qaProject;
    private String uniProjectName;
    private boolean pullUnifiedProject;
    private boolean qaEnableDependencyMode;
    private boolean qaEnableMtr;
    private boolean qaEnableProjectCma;
    private boolean qaCrossModuleAnalysis;
    private String cmaProjectName;
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
     * @param cmaProjectName
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
     *  TODO - We should create a builder for this object to make the creation more readable.
     */

    public QaFrameworkReportSettings(String qaInstallation, String qaProject, boolean pullUnifiedProject, String uniProjectName,
            boolean qaEnableMtr, boolean qaEnableProjectCma, boolean qaEnableDependencyMode, boolean qaCrossModuleAnalysis, String cmaProjectName,
            boolean generateReport, boolean publishToQAV, boolean loginToQAV, String product, boolean qaUploadWhenStable, String qaVerifyProjectName,
            String uploadSnapshotName, String buildNumber, String uploadSourceCode, boolean genCrReport, boolean genMdReport, boolean genSupReport) {

        this.qaInstallation = qaInstallation;
        this.uniProjectName = uniProjectName;
        this.pullUnifiedProject = pullUnifiedProject;
        this.qaCrossModuleAnalysis = qaCrossModuleAnalysis;
        this.cmaProjectName = cmaProjectName;
        this.publishToQAV = publishToQAV;
        this.loginToQAV = loginToQAV;
        this.qaEnableMtr = qaEnableMtr;
        this.qaEnableProjectCma = qaEnableProjectCma;
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

    public String getCmaProjectName() {
        return cmaProjectName;
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
}
