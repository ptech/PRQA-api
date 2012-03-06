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
        
    public QAC(String productExecutable) {
        this.productExecutable = productExecutable;
    }
    
    public QAC(String productExecutable, String command) {
        this.command = command;
        this.productExecutable = productExecutable;
    }
    
    public QAC(String productExecutable, String command, String commandBase) {
        this.productExecutable = productExecutable;
        this.command = command;
        this.commandBase = commandBase;
    }
    
    public CmdResult execute(String command, File dir) {
        return PRQACommandLineUtility.run(command, dir);
    }
}
