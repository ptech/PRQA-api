/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import net.praqma.util.debug.Logger;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;
import net.praqma.util.execute.CommandLineException;
import net.praqma.util.execute.CommandLineInterface;

/**
 *
 * @author jes
 */
public class Cmd extends PRQA {

    	private static CommandLineInterface cli = null;
	private static Logger logger = Logger.getLogger();

	static {
		logger.debug( "Cleartool environment: " + System.getProperty( "cleartool" ) );
//		if( System.getProperty( "cleartool" ) != null && System.getProperty( "cleartool" ).equalsIgnoreCase( "mock" ) ) {
//			cli = CommandLineMock.getInstance();
//		} else {
			cli = CommandLine.getInstance();
//		}
	}

	public static CmdResult run( String cmd ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( "cleartool " + cmd, null, true, false );
	}

	public static CmdResult run( String cmd, File dir ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( "cleartool " + cmd, dir, true, false );
	}

	public static CmdResult run( String cmd, File dir, boolean merge ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( "cleartool " + cmd, dir, merge, false );
	}

	public static CmdResult run( String cmd, File dir, boolean merge, boolean ignore ) throws CommandLineException, AbnormalProcessTerminationException {
		return cli.run( "cleartool " + cmd, dir, merge, ignore );
	}
    

}
