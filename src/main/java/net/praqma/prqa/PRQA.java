package net.praqma.prqa;

import java.io.Serializable;
import java.util.logging.Logger;
import net.praqma.prqa.logging.Config;
import net.praqma.prqa.products.QAC;
import net.praqma.prqa.products.QACpp;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**

 * Class wrapping a Programming research product. We pass this on to our remote method class, hence the need for serialization. 
 * 
 * We wrap multiple products. QAR is a PRQA product.
 * 
 * The CLI is static and is therefore by default not serialized. 
 * 
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

        
    public abstract String getProductVersion();
    

    protected static final Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    public String getCommandBase() {
        logger.finest(String.format("Starting execution of method - getCommandBase"));
        logger.finest(String.format("Returning value: %s", this.commandBase));

        return this.commandBase;
    }

    public void setCommandBase(String commandBase) {
        logger.finest(String.format("Starting execution of method - setCommandBase"));
        logger.finest(String.format("Input parameter commandBase type: %s; value: %s", commandBase.getClass(), commandBase));

        this.commandBase = commandBase;

        logger.finest(String.format("Ending execution of method - setProductExecutable"));
    }

    public String getProductExecutable() {
        logger.finest(String.format("Starting execution of method - getProductExecutable"));
        logger.finest(String.format("Returning value: %s", this.productExecutable));

        return this.productExecutable;
    }

    public void setProductExecutable(String productExecutable) {
        logger.finest(String.format("Starting execution of method - setProductExecutable"));
        logger.finest(String.format("Input parameter productExecutable type: %s; value: %s", productExecutable.getClass(), productExecutable));

        this.productExecutable = productExecutable;

        logger.finest(String.format("Ending execution of method - setProductExecutable"));
    }

    public String getCommand() {
        logger.finest(String.format("Starting execution of method - getCommand"));
        logger.finest(String.format("Returning value: %s", this.command));

        return this.command;
    }


    public void setCommand(String command) {
        logger.finest(String.format("Starting execution of method - setCommand"));
        logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));

        this.command = command;
        logger.finest(String.format("Ending execution of method - setCommand"));

    } 
    /**
     * Factory methods. Takes a string input parameter fromt the Gui and converts it to a concrete PRQA product.
     * @param productname
     * @return 
     */
    public static PRQA create(String productname) {
        PRQA product = null;
        if(productname.equalsIgnoreCase("qac")) {
            product = new QAC();
        } else if (productname.equalsIgnoreCase("qacpp")) {
            product = new QACpp();
        } else if (productname.equalsIgnoreCase("java")) {
            throw new NotImplementedException();
        }
        return product;
    } 
}
