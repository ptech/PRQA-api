/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.analyzer;

import net.praqma.prga.excetions.PrqaException;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author Praqma
 */
public interface PRQAanalyzer extends PRQAExcutable {
    public CmdResult analyze() throws PrqaException;
}
