package net.prqma.prqa.qaframework;

import net.praqma.prqa.ReportSettings;
import net.praqma.prqa.QAVerifyServerSettings;

public class QaFrameworkReportSettings implements ReportSettings {

    private String qaInstallation;
    private String qaProject;
    private String uniProjectName;
    private boolean pullUnifiedProject;
    private boolean qaEnableDependencyMode;
    private boolean qaEnableProjectCma;
    private boolean qaCrossModuleAnalysis;
    private String cmaProjectName;
    private boolean generateReport;
    private boolean publishToQAV;
    private String product;
    private String qaVerifyProjectName;
    private String uploadSnapshotName;
    private String buildNumber;
    private String uploadSourceCode;

    public QaFrameworkReportSettings(String qaInstallation, String qaProject, boolean pullUnifiedProject, String uniProjectName, 
            boolean qaEnableProjectCma, boolean qaEnableDependencyMode, boolean qaCrossModuleAnalysis, String cmaProjectName, 
            boolean generateReport, boolean publishToQAV, String product, String qaVerifyProjectName, String uploadSnapshotName, 
            String buildNumber, String uploadSourceCode) {

        this.qaInstallation = qaInstallation;
        this.uniProjectName = uniProjectName;
        this.pullUnifiedProject = pullUnifiedProject;
        this.qaCrossModuleAnalysis = qaCrossModuleAnalysis;
        this.cmaProjectName = cmaProjectName;
        this.publishToQAV = publishToQAV;
        this.qaEnableProjectCma = qaEnableProjectCma;
        this.qaEnableDependencyMode = qaEnableDependencyMode;
        this.generateReport = generateReport;
        this.qaProject = qaProject;
        this.product = product;
        this.qaVerifyProjectName = qaVerifyProjectName;
        this.uploadSnapshotName = uploadSnapshotName;
        this.buildNumber = buildNumber;
        this.uploadSourceCode = uploadSourceCode;
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
    
    public boolean isQaEnableProjectCma() {
        return qaEnableProjectCma;
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
    
    
}
