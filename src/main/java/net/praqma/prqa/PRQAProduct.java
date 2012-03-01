/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

/**
 *
 * @author jes,man
 * 
 * Use this interface to set the place where the binaries for your executable are located. 
 */
public interface PRQAProduct {
    public String getProductHomeDir();
    public void setProductHomeDir(String productHomeDir);
}
