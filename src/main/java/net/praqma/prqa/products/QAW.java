/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.HashMap;
import net.praqma.prqa.exceptions.PrqaSetupException;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;

/**
 *
 * @author Praqma
 */
public class QAW implements Product {

    @Override
    public final String getProductVersion(HashMap<String,String> environment, File currentDirectory, boolean isUnix) throws PrqaSetupException {
        CmdResult res = null;
        try {
            res = CommandLine.getInstance().run("qaw -version", currentDirectory, true, false, environment);
        } catch (AbnormalProcessTerminationException abnex) {
            throw new PrqaSetupException(String.format("Failed to obtain QAW version running this command %s, return code was %s\nMessage was:\n%s",abnex.getCommand(), abnex.getExitValue(), abnex.getMessage()), abnex);
        }
        if(res.stdoutList != null && !res.stdoutList.isEmpty()) {
            return res.stdoutList.get(0);
        } else {
            throw new PrqaSetupException("The command to resolve qaw was succesful, yet no version number present in shell output");
        }
    }
    
}
