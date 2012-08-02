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
 *
 * @author jes
 */
public class QACpp extends PRQA {
    
    public QACpp() {}
    
    public QACpp(String command) {
        this.command = command;
    }
    
    public QACpp(String commandBase, String command) {
        this.command = command;
        this.commandBase = commandBase;
    }
    
    public CmdResult execute(String command, File dir) {
        return PRQACommandLineUtility.run(command, dir);
    }
    
    public CmdResult execute(String command) {
        return PRQACommandLineUtility.run(command,new File(commandBase));
    }
    
    public CmdResult generateReportFiles() {
        return PRQACommandLineUtility.run(command,new File(commandBase));
    }

    @Override
    public String getProductVersion() {
        String version = "Unknown";
        
        return version;
    }

    @Override
    public String toString() {
        return "qacpp";
    }
    
    
}
