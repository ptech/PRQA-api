/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import java.util.logging.Level;
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
	private transient final static Logger logger;

	static {
		cli = CommandLine.getInstance();         
		logger = Logger.getLogger( Config.GLOBAL_LOGGER_NAME );
	}

	public static CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest("Starting execution of method - run(String cmd)");
		logger.log(Level.FINEST, "Input parameter cmd type: {0}; value: {1}", new Object[]{cmd.getClass(), cmd});

		return cli.run( cmd, null, true, false );
	}

	public static CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest("Starting execution of method - run(String cmd, File dir)");
		logger.log(Level.FINEST, "Input parameter cmd type: {0}; value: {1}", new Object[]{cmd.getClass(), cmd});
		logger.log(Level.FINEST, "Input parameter dir type: {0}; value: {1}", new Object[]{dir.getClass(), dir});

		return cli.run( cmd, dir, true, false );
	}

	public static CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest("Starting execution of method - run(String cmd, File dir, boolean merge)");
		logger.log(Level.FINEST, "Input parameter cmd type: {0}; value: {1}", new Object[]{cmd.getClass(), cmd});
		logger.log(Level.FINEST, "Input parameter dir type: {0}; value: {1}", new Object[]{dir.getClass(), dir});
		logger.log(Level.FINEST, "Input parameter merge type: {0}; value: {1}", new Object[]{"boolean", merge});

		return cli.run( cmd, dir, merge, false );
	}

	public static CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest("Starting execution of method - run(String cmd, File dir, boolean merge, boolean ignore)");
		logger.log(Level.FINEST, "Input parameter cmd type: {0}; value: {1}", new Object[]{cmd.getClass(), cmd});
		logger.log(Level.FINEST, "Input parameter dir type: {0}; value: {1}", new Object[]{dir.getClass(), dir});
		logger.log(Level.FINEST, "Input parameter merge type: {0}; value: {1}", new Object[]{"boolean", merge});
		logger.log(Level.FINEST, "Input parameter ignore type: {0}; value: {1}", new Object[]{"boolean", ignore});

		return cli.run( cmd, dir, merge, ignore );
	}
}
