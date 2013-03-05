/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.analyzer;

import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.products.PRQACommandBuilder;

/**
 *
 * @author Praqma
 */
public interface PRQAExcutable {
    public PRQACommandBuilder getBuilder();
    
    //Method to set the 'working directory' when the command is executed via command line utility.
    public void setCommandBase(String base);
    public void setCommand(String cmd);
    public String getProductVersion() throws PrqaException;
    
}
