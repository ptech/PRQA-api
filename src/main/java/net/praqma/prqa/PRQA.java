package net.praqma.prqa;

import java.io.Serializable;
import net.praqma.prqa.products.QAC;
import net.praqma.prqa.products.QACpp;
import net.praqma.util.execute.CmdResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Class wrapping a Programming research product. We pass this on to our remote method class, hence the need for serialization. 
 * 
 * We wrap multiple products. QAR is a PRQA product.
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
