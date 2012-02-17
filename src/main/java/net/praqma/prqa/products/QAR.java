/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import net.praqma.prqa.Cmd;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.ReportInterface;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes
 */
public class QAR extends PRQA implements ReportInterface {

    public CmdResult executed(String product ) {
        return Cmd.run("QAR-1.2/bin/qar.exe" + product);
    }

}
