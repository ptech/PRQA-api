/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

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
    
    @Override
    public CmdResult execute(String cmd) {
       return PRQACommandLineUtility.run(getProductExecutable() +" "+command);       
    }
}
