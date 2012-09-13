/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.io.FileFilter;
import java.util.logging.Logger;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.logging.Config;
import net.praqma.util.execute.CmdResult;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author jes
 */
public class QACpp extends PRQA {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    public QACpp() {}
    
    public QACpp(String command) {
    	logger.finest(String.format("Constructor called for class QACpp(String command)"));
    	logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));
    	
        this.command = command;
        
        logger.finest(String.format("Ending execution of constructor - QACpp(String command)"));
    }

    public QACpp(String commandBase, String command) {
    	logger.finest(String.format("Constructor called for class QACpp(String commandBase, String command)"));
    	logger.finest(String.format("Input parameter commandBase type: %s; value: %s", commandBase.getClass(), commandBase));
    	logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));
    	
        this.command = command;
        this.commandBase = commandBase;
        
        logger.finest(String.format("Ending execution of constructor - QACpp(String commandBase, String command)"));
    }

    public CmdResult execute(String command, File dir) {
        logger.finest(String.format("Starting execution of method - execute(String command, File dir)"));
        logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));
        logger.finest(String.format("Input parameter dir type: %s; value: %s", dir.getClass(), dir));

        CmdResult output = PRQACommandLineUtility.run(command, dir);
        
        logger.finest(String.format("Returning value: %s", output));
        
        return output;
    }

    public CmdResult execute(String command) {
        logger.finest(String.format("Starting execution of method - execute(String command)"));
        logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));

        CmdResult output = PRQACommandLineUtility.run(command, new File(commandBase));
        
        logger.finest(String.format("Returning value: %s", output));
        
        return output;
    }

    public CmdResult generateReportFiles() {
        logger.finest(String.format("Starting execution of method - execute()"));

        CmdResult output = PRQACommandLineUtility.run(command, new File(commandBase));
        
        logger.finest(String.format("Returning value: %s", output));
        
        return output;
    }
    
    @Override
    public String getProductVersion() {
        logger.finest(String.format("Starting execution of method - getProductVersion"));
        
        String version = "Unknown";
        
        CmdResult res = null;
        File f = null;
        try {
            f = File.createTempFile("test_prqa_file", ".c");
            
            res = PRQACommandLineUtility.run(String.format("qacpp -version \"%s\"", f.getAbsolutePath()), new File(commandBase));
  
        } catch (Exception ex) {
            logger.warning("Failed to get qacpp-version");
        } finally {
            if(f != null) {
                try {
                    logger.finest(String.format("Setting up filter for files to delete"));
                    String tempDir = f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf(File.separator));
                    logger.finest(String.format("Found temp dir: %s",tempDir));
                    File tempFolder = new File(tempDir);

                    FileFilter  ff = new WildcardFileFilter("test_prqa_file*");                

                    for(File deleteme : tempFolder.listFiles(ff)) {
                        logger.finest(String.format("Starting to delete file: %s",deleteme.getAbsolutePath()));
                        if(deleteme.delete()) {
                            logger.finest(String.format("Succesfully deleted file: %s",deleteme.getAbsolutePath()));
                        } else {
                            logger.warning(String.format("Failed to delete: %s",deleteme.getAbsolutePath()));
                        }
                    }
                } catch (Exception ex) {
                    logger.warning("Something went wrong in getProductVersion() when attempting to delete created files");
                }
            }
        }
        
        if(res != null) {
            for(String s: res.stdoutList) {
                    version = s;
                    break;
                }
        }
        
        logger.finest(String.format("Returning value %s", version));
        
        return version;
    }

    @Override
    public String toString() {
        return "qacpp";
    }
}