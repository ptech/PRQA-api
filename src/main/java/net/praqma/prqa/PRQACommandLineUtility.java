/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import java.util.logging.Logger;
import net.praqma.prqa.logging.Config;
import net.praqma.util.execute.*;

/**
 * PRQA command line utility. Makes use Praqma jutils. Specifically the command line interface. 
 * 
 * @author jes
 */
 
public class PRQACommandLineUtility {

    private static CommandLineInterface cli = null;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final static Logger logger;
    
    static {
        cli = CommandLine.getInstance();         
        logger = Logger.getLogger( Config.GLOBAL_LOGGER_NAME );
    }

    public static CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException {
        logger.finest("Starting execution of method - run(String cmd)");
        logger.finest(String.format("Parameter cmd value: %s;", cmd));
        
        return cli.run( cmd, null, true, false );
    }

    public static CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException {
        logger.finest("Starting execution of method - run(String cmd, File dir)");
        logger.finest(String.format("Parameter cmd value: %s; dir value: %s;", cmd, dir.getAbsolutePath()));
        
        return cli.run( cmd, dir, true, false );
    }

    public static CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException {
        logger.finest("Starting execution of method - run(String cmd, File dir, boolean merge)");
        logger.finest(String.format("Parameter cmd value: %s; dir value: %s; merge value: %s;", cmd, dir.getAbsolutePath(), merge));
        
        return cli.run( cmd, dir, merge, false );
    }

    public static CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException {
        logger.finest("Starting execution of method - run(String cmd, File dir, boolean merge, boolean ignore)");
        logger.finest(String.format("Parameter cmd value: %s; dir value: %s; merge value: %s; ignore value: %s;", cmd, dir.getAbsolutePath(), merge, ignore));
        
        return cli.run( cmd, dir, merge, ignore );
    }
}
