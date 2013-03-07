/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import net.praqma.util.execute.*;

/**
 * PRQA command line utility. Makes use Praqma jutils. Specifically the command line interface. 
 * 
 * @author jes
 */

public class PRQACommandLineUtility {
    
    private static PRQACommandLineUtility instance;
    private HashMap<String,String> environment;
    
    public static PRQACommandLineUtility getInstance(HashMap<String,String> environment) {
        if(instance == null) {
            
            instance = new PRQACommandLineUtility();
            instance.setEnvironment(environment);
        }
        return instance;
    }
    
	private static CommandLineInterface cli = null;
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
	private final static Logger logger;

	static {
		cli = CommandLine.getInstance();         
		logger = Logger.getLogger( PRQACommandLineUtility.class.getName() );
	}

	public CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest(String.format("Starting execution of method - run(String cmd)"));
		logger.finest(String.format("Input parameter cmd type: %s; value: %s", cmd.getClass(), cmd));

		return cli.run( cmd, null, true, false, environment );
	}

	public CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest(String.format("Starting execution of method - run(String cmd, File dir)"));
		logger.finest(String.format("Input parameter cmd type: %s; value: %s", cmd.getClass(), cmd));
		logger.finest(String.format("Input parameter dir type: %s; value: %s", dir.getClass(), dir));

		return cli.run( cmd, dir, true, false, environment );
	}
    
    //NEW ONE
    public CmdResult run( String cmd, File dir, HashMap<String,String> environment) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest(String.format("Starting execution of method - run(String cmd, File dir)"));
		logger.finest(String.format("Input parameter cmd type: %s; value: %s", cmd.getClass(), cmd));
		logger.finest(String.format("Input parameter dir type: %s; value: %s", dir.getClass(), dir));

		return cli.run( cmd, dir, true, false, environment );
	}

	public CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest(String.format("Starting execution of method - run(String cmd, File dir, boolean merge)"));
		logger.finest(String.format("Input parameter cmd type: %s; value: %s", cmd.getClass(), cmd));
		logger.finest(String.format("Input parameter dir type: %s; value: %s", dir.getClass(), dir));
		logger.finest(String.format("Input parameter merge type: %s; value: %s", "boolean", merge));

		return cli.run( cmd, dir, merge, false, environment );
	}

	public CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException {
		logger.finest(String.format("Starting execution of method - run(String cmd, File dir, boolean merge, boolean ignore)"));
		logger.finest(String.format("Input parameter cmd type: %s; value: %s", cmd.getClass(), cmd));
		logger.finest(String.format("Input parameter dir type: %s; value: %s", dir.getClass(), dir));
		logger.finest(String.format("Input parameter merge type: %s; value: %s", "boolean", merge));
		logger.finest(String.format("Input parameter ignore type: %s; value: %s", "boolean", ignore));

		return cli.run( cmd, dir, merge, ignore, environment );
	}

    /**
     * @return the environment
     */
    public HashMap<String,String> getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(HashMap<String,String> environment) {
        this.environment = environment;
    }
}
