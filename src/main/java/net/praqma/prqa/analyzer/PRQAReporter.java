/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.analyzer;

import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.util.execute.CmdResult;

/**
 * @author Praqma
 */

public interface PRQAReporter extends PRQAExcutable {
    public CmdResult report() throws PrqaException;
    public PRQAanalyzer getAnalysisTool();
    public void setAnalysisTool(PRQAanalyzer tool);
}
