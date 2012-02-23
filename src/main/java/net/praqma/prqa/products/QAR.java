/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import net.praqma.prqa.Cmd;
import net.praqma.prqa.PRQA;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes
 */
public class QAR extends PRQA {

    public CmdResult executed(String command) {
        return Cmd.run(productHomeDir +" "+command);
    }

    public String getProductHomeDir() {
        return this.productHomeDir;
    }

    public void setProductHomeDir(String productHomeDir) {
        this.productHomeDir = productHomeDir;
    }

}
