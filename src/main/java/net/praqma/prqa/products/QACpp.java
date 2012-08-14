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
 * @author jes
 */
public class QACpp extends PRQA {

    private transient static final Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    public QACpp(String command) {
        this.command = command;
    }

    public QACpp(String commandBase, String command) {
        this.command = command;
        this.commandBase = commandBase;
    }

    public CmdResult execute(String command, File dir) {
        logger.log(Level.FINEST, "Starting execution of method - execute(String command, File dir)");
        logger.log(Level.FINEST, "Parameter cmd value: {0}; dir value: {1};", new Object[]{command, dir.getAbsolutePath()});

        return PRQACommandLineUtility.run(command, dir);
    }

    public CmdResult execute(String command) {
        logger.log(Level.FINEST, "Starting execution of method - execute(String command)");
        logger.log(Level.FINEST, "Parameter cmd value: {0};", command);

        return PRQACommandLineUtility.run(command, new File(commandBase));
    }

    @Override
    public CmdResult execute() {
        logger.log(Level.FINEST, "Starting execution of method - execute()");

        return PRQACommandLineUtility.run(command, new File(commandBase));
    }
}
