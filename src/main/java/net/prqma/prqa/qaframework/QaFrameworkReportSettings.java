package net.prqma.prqa.qaframework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.praqma.prqa.ReportSettings;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class QaFrameworkReportSettings implements ReportSettings, Serializable {

    private String qaInstallation;
    private boolean useCustomLicenseServer;
    private String customLicenseServerAddress;
    private String qaProject;
    private boolean pullUnifiedProject;
    private String uniProjectName;
    private boolean qaEnableDependencyMode;
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
    private boolean generateReportOnAnalysisError;

    @Override
    public String getProduct() {
        return "";
    }

    @Override
    public boolean publishToQAV() {
        return publishToQAV;
    }

}
