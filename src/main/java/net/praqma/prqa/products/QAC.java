/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import net.praqma.prqa.AnalysisInterface;
import net.praqma.prqa.Cmd;
import net.praqma.prqa.PRQA;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes
 */
public class QAC extends PRQA implements AnalysisInterface  {

    

    public CmdResult execute(String cmd) {
       return Cmd.run(cmd);
    }

}
