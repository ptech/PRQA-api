/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.util.execute.CmdResult;

/**
 * Reporting class. 
 * 
 * @author jes
 */
public class QAR extends PRQA {
    private String reportOutputPath;
    private PRQACommandBuilder builder;
    
    public QAR() {
        this.productExecutable = "qar";
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
    
    public static String getStandardReportsDir(String base) {
        return base + "\\" + "standard_reports";
    }
    
    public static String getExecutable(String base) {
        return base + "\\bin\\qar.exe";
    }
    
}
