package net.praqma.prqa;

import java.io.Serializable;
import java.util.logging.Logger;
import net.praqma.util.execute.CmdResult;
import net.praqma.prqa.logging.Config;

/**
 * Class wrapping a Programming research product. We pass this on to our remote method class, hence the need for serialization. 
 * 
 * The CLI is static and is therefore by default not serialized. 
 * 
 * @author jes, man
 */
public abstract class PRQA implements Serializable {
    
    protected String commandBase;
    protected String productExecutable;
    protected String command;
    private final Logger logger;
    
    public PRQA() {
        logger = Logger.getLogger( Config.GLOBAL_LOGGER_NAME );
    }
    
    public String getCommandBase() {
        logger.finest("Starting execution of method - getCommandBase");
        logger.finest(String.format("Returning value: %s", this.commandBase));
        
        return this.commandBase;
    }
    
    public void setCommandBase(String commandBase) {
        logger.finest("Starting execution of method - setCommandBase");
        logger.finest(String.format("Input parameter commandBase type: %s; value: %s", commandBase.getClass(), commandBase));
        
        this.commandBase = commandBase;
        
        logger.finest("Ending execution of method - setProductExecutable");
    }
    
    public String getProductExecutable() {
        logger.finest("Starting execution of method - getProductExecutable");
        logger.finest(String.format("Returning value: %s", this.productExecutable));
        
        return this.productExecutable;
    }

    public void setProductExecutable(String productExecutable) {
        logger.finest("Starting execution of method - setProductExecutable");
        logger.finest(String.format("Input parameter productExecutable type: %s; value: %s", productExecutable.getClass(), productExecutable));
        
        this.productExecutable = productExecutable;
        
        logger.finest("Ending execution of method - setProductExecutable");
    }
    
    public String getCommand() {
        logger.finest("Starting execution of method - getProductExecutable");
        logger.finest(String.format("Returning value: %s", this.productExecutable));
        
        return this.command;
    }
    
    public void setCommand(String command) {
        logger.finest("Starting execution of method - setCommand");
        logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));
        
        this.command = command;
        
        logger.finest("Ending execution of method - setCommand");
    }
    
    public abstract CmdResult execute();
}
