/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import net.praqma.util.debug.Logger;
import net.praqma.util.execute.*;

/**
 * PRQA command line utility. Makes use Praqma jutils. Specifically the command line interface. 
 * 
 * @author jes
 */
public class PRQACommandLineUtility {

    	private static CommandLineInterface cli = null;
	private static Logger logger = Logger.getLogger();

	static {
            cli = CommandLine.getInstance();            
	}

	public static CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( cmd, null, true, false );
	}

	public static CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( cmd, dir, true, false );
	}

	public static CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( cmd, dir, merge, false );
	}

	public static CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( cmd, dir, merge, ignore );
	}
   
}
