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
    private String product;
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
    
    public QAR(String product, String projectFile, QARReportType type) {
        this.builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
        this.product = product;
        this.projectFile = projectFile;
        this.type = type;
    }
    
    public PRQACommandBuilder getBuilder() {
        return builder;
    }
    
    @Override
    public CmdResult execute() {
        return PRQACommandLineUtility.run(getBuilder().getCommand(),new File(commandBase));
    }
    
    public void setReportOutputPath(String reportOutputPath) {
        this.reportOutputPath = reportOutputPath;
    }
    
    public String getReportOutputPath() {
        return this.reportOutputPath;
    }
    
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(String product) {
        this.product = product;
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
        out += "QAR selected product:\t\t" + this.product + "\n";
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
}
