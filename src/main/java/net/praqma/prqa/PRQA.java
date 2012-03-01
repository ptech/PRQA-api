package net.praqma.prqa;

import java.io.File;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes, man
 */
public abstract class PRQA extends Cmd implements PRQAProduct {
    
    protected String productHomeDir;
    protected String command;
    
    public String getProductHomeDir() {
        return this.productHomeDir;
    }

    public void setProductHomeDir(String productHomeDir) {
        this.productHomeDir = productHomeDir;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public CmdResult execute(String cmd) {
       return Cmd.run(cmd,new File(productHomeDir));
    }
}
