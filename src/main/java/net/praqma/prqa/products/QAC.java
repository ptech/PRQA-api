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
 * @author jes, man
 */
public class QAC extends PRQA {
        
    public QAC() {}
    public QAC(String command) {
        this.command = command;
    }
    
    public QAC(String commandBase, String command) {
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
        String productVersion = "Unknown version";
       
        //CmdResult res = PRQACommandLineUtility.run(this.toString()+" -version", new File(commandBase));
       
        return productVersion;
    }

    @Override
    public String toString() {
        return "qac";
    }
    
 }
