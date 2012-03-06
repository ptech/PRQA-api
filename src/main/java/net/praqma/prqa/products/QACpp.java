/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import net.praqma.prqa.PRQA;

/**
 *
 * @author jes
 */
public class QACpp extends PRQA {
    
    public QACpp(String productExecutable) {
        this.productExecutable = productExecutable;
    }
    
    public QACpp(String productExecutable, String command) {
        this.command = command;
        this.productExecutable = productExecutable;
    }       
}
