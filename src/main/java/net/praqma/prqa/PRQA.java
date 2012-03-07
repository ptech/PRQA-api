package net.praqma.prqa;

import java.io.Serializable;
import net.praqma.util.execute.CmdResult;

/**
 * Class wrapping a Programming research product. We pass this on to our remote method class, hence the need for serialization. 
 * 
 * The CLI is static and is therefore by default not serialized. 
 * 
 * @author jes, man
 */
public abstract class PRQA implements PRQAProduct,Serializable {
    
    protected String commandBase;
    protected String productExecutable;
    protected String command;
    
    public String getCommandBase() {
        return this.commandBase;
    }
    
    public void setCommandBase(String commandBase) {
        this.commandBase = commandBase;
    }
    
    public String getProductExecutable() {
        return this.productExecutable;
    }

    public void setProductExecutable(String productExecutable) {
        this.productExecutable = productExecutable;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public abstract CmdResult execute();
}
