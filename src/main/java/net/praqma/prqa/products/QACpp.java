/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.prqa.PRQA;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes
 */
public class QACpp extends PRQA {
    
    public QACpp(String homeDir) {
        this.productHomeDir = homeDir;
    }

    @Override
    public CmdResult execute(String cmd) {
        return super.execute(cmd);
    }
       
    public String getProductHomeDir() {
        return this.productHomeDir;
    }

    public void setProductHomeDir(String productHomeDir) {
        this.productHomeDir = productHomeDir;
    }
}
