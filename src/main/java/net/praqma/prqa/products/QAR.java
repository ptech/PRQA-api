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
    private PRQACommandBuilder builder;
    private QARReportType type;
    
    /**
     * QAR is invoked using QAW where this is taken as parameter in the QAW command.
     * 
     */
    
    public QAR() {
        this.productExecutable = "qaw";
        builder = new PRQACommandBuilder(this.productExecutable);
    }
    
    public QAR(String productExecutable) {
        this.productExecutable = productExecutable;
        builder = new PRQACommandBuilder(productExecutable);
    }
    
    public PRQACommandBuilder getBuilder() {
        return builder;
    }
    
    public QAR(String productExecutable, String command) {
        this.command = command;
        this.productExecutable = productExecutable;
    }
    
    public CmdResult execute() {
        return PRQACommandLineUtility.run(getBuilder().getCommand(),new File(commandBase));
    }
    
    public void setReportOutputPath(String reportOutputPath) {
        this.reportOutputPath = reportOutputPath;
    }
    
    public String getReportOutputPath() {
        return this.reportOutputPath;
    }

    @Override
    public String toString() {
        return String.format("QAR Command: exe = %s, cmd = %s specified ouput path = %s", this.productExecutable, this.command, this.reportOutputPath);
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
