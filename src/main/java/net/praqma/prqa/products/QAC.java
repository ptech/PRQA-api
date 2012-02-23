/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.prqa.AnalysisInterface;
import net.praqma.prqa.Cmd;
import net.praqma.prqa.PRQA;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes, man
 */
public class QAC extends PRQA implements AnalysisInterface  {

    public QAC(String homeDir) {
        productHomeDir = homeDir;
    }
    
    @Override
    public CmdResult execute(String cmd) {
       return Cmd.run(cmd, new File(productHomeDir));
    }

    public String getProductHomeDir() {
        return this.productHomeDir;
    }

    public void setProductHomeDir(String productHomeDir) {
        this.productHomeDir = productHomeDir;
    }
}
