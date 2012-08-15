/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.logging.Config;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author jes, man
 */
public class QAC extends PRQA {

    private transient static final Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    public QAC(String command) {
    	logger.log(Level.FINEST, "Constructor called for class QAC(String command)");
    	logger.log(Level.FINEST, "Input parameter command type: {0}; value: {1}", new Object[]{command.getClass(), command});
    	
        this.command = command;
        
        logger.log(Level.FINEST, "Ending execution of constructor - QAC");
    }

    public QAC(String commandBase, String command) {
    	logger.log(Level.FINEST, "Constructor called for class QAC(String commandBase, String command)");
    	logger.log(Level.FINEST, "Input parameter commandBase type: {0}; value: {1}", new Object[]{commandBase.getClass(), commandBase});
    	logger.log(Level.FINEST, "Input parameter command type: {0}; value: {1}", new Object[]{command.getClass(), command});
    	
        this.command = command;
        this.commandBase = commandBase;
        
        logger.log(Level.FINEST, "Ending execution of constructor - QAC");
    }

    public CmdResult execute(String command, File dir) {
        logger.log(Level.FINEST, "Starting execution of method - execute(String command, File dir)");
        logger.log(Level.FINEST, "Input parameter command type: {0}; value: {1}", new Object[]{command.getClass(), command});
        logger.log(Level.FINEST, "Input parameter dir type: {0}; value: {1}", new Object[]{dir.getClass(), dir});

        CmdResult output = PRQACommandLineUtility.run(command, dir);
        
        logger.log(Level.FINEST, "Returning value: {0}", output);
        
        return output;
    }

    public CmdResult execute(String command) {
        logger.log(Level.FINEST, "Starting execution of method - execute(String command)");
        logger.log(Level.FINEST, "Input parameter command type: {0}; value: {1}", new Object[]{command.getClass(), command});

        CmdResult output = PRQACommandLineUtility.run(command, new File(commandBase));
        
        logger.log(Level.FINEST, "Returning value: {0}", output);
        
        return output;
    }

    @Override
    public CmdResult execute() {
        logger.log(Level.FINEST, "Starting execution of method - execute()");

        CmdResult output = PRQACommandLineUtility.run(command, new File(commandBase));
        
        logger.log(Level.FINEST, "Returning value: {0}", output);
        
        return output; 
    }
}
