/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.PRQAContext.QARReportType;
import net.praqma.util.execute.CmdResult;

/**
 * Reporting class. 
 * 
 * @author jes
 */
public class QAR extends PRQA {
    private String reportOutputPath;
    private String projectFile;
    private PRQA analysisTool;
    private PRQACommandBuilder builder;
    private QARReportType type;
    public static final String QAW_WRAPPER = "qaw";
    
    
    /**
     * QAR is invoked using QAW where this is taken as parameter in the QAW command.
     * 
     */
    public QAR() {
        builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
    }
    
    public QAR(PRQA analysisTool, String projectFile, QARReportType type) {
        this.analysisTool = analysisTool;
        this.builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
        this.projectFile = projectFile;
        this.type = type;
    }
    
    public PRQACommandBuilder getBuilder() {
        return builder;
    }
    
    public CmdResult generateReportFiles() {
        return PRQACommandLineUtility.run(getBuilder().getCommand(),new File(commandBase));
    }
    
    public void setReportOutputPath(String reportOutputPath) {
        this.reportOutputPath = reportOutputPath;
    }
    
    /**
     * This returns the report output path. In our case it will always be %WORKSPACE%.
     * 
     */ 
    public String getReportOutputPath() {
        return this.reportOutputPath;
    }
    
    public PRQA getAnalysisTool() {
        return this.analysisTool;
    }
    
    public void setAnalysisTool(PRQA analysisTool) {
        this.analysisTool = analysisTool;
    }
    
    public String getProjectFile() {
        return this.projectFile;
    }
    
    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
    }

    @Override
    public String toString() {
        String out = "";
        out += "QAR selected project file:\t" + this.projectFile + "\n";
        out += "QAR selected product:\t\t" + this.analysisTool + "\n";
        out += "QAR selected report type:\t" + this.type + "\n";
        return out;
    }

    /**
     * @return the type
     */
    public QARReportType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(QARReportType type) {
        this.type = type;
    }

    @Override
    public String getProductVersion() {
        String version = "Unknown"; 
        //CmdResult res = PRQACommandLineUtility.run("qar -version", new File(commandBase));
        //TODO; Need to parse the result. 
        
        return version;
    }
}
