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
 *
 * @author jes, man
 */
public class QAC extends PRQA {
        
    public QAC(String homeDir) {
        productHomeDir = homeDir;
    }
    
    public QAC(String homeDir, String command) {
        this.productHomeDir = homeDir;
        this.command = command;
    }
    
    @Override
    public CmdResult execute(String cmd) {
       return Cmd.run(cmd, new File(productHomeDir));       
    }
}
