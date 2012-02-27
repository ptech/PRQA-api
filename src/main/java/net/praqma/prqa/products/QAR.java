/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.prqa.Cmd;
import net.praqma.prqa.PRQA;
import net.praqma.util.execute.CmdResult;

/**
 * Reporting class. 
 * 
 * @author jes
 */
public class QAR extends PRQA {
    private String reportOutputPath;
    private String command;
    
    public QAR(String homedir) {
        this.productHomeDir = homedir;
    }
    
    public QAR(String homedir, String command) {
        this.command = command;
        this.productHomeDir = homedir;
    }
    
    @Override
    public CmdResult execute(String command) {
        return Cmd.run(command,new File(productHomeDir));
    }
        
    public CmdResult execute() {
        return Cmd.run(command, new File(productHomeDir));
    }

    public String getProductHomeDir() {
        return this.productHomeDir;
    }

    public void setProductHomeDir(String productHomeDir) {
        this.productHomeDir = productHomeDir;
    }
    
    public void setReportOutputPath(String reportOutputPath) {
        this.reportOutputPath = reportOutputPath;
    }
    
    public String getReportOutputPath() {
        return this.reportOutputPath;
    }

}
