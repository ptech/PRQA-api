package net.praqma.prqa;

import java.io.File;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes, man
 */
public abstract class PRQA extends Cmd implements ProductInterface {
    
    protected String productHomeDir;
    
    public CmdResult execute(String cmd) {
       return Cmd.run(cmd,new File(productHomeDir));
    }
}
