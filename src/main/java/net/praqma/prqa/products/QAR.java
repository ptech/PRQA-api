/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.PRQA;
import net.praqma.util.execute.CmdResult;

/**
 * Reporting class. 
 * 
 * @author jes
 */
public class QAR extends PRQA {
    private String reportOutputPath;
    
    public QAR(String productExecutable) {
        this.productExecutable = productExecutable;
    }
    
    public QAR(String productExecutable, String command) {
        this.command = command;
        this.productExecutable = productExecutable;
    }
    
    @Override
    public CmdResult execute(String command) {
        return PRQACommandLineUtility.run(getProductExecutable() + " " + command);
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
}
