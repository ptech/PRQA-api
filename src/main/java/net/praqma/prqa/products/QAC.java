/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.io.FileFilter;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.analyzer.PRQAanalyzer;
import net.praqma.util.execute.CmdResult;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author jes, man
 */
public class QAC extends PRQA implements PRQAanalyzer {

    public QAC() {
        builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
    }
    
    public QAC(String commandBase, String command) {
        super();
    	logger.finest(String.format("Constructor called for class QAC(String commandBase, String command)"));
    	logger.finest(String.format("Input parameter commandBase type: %s; value: %s", commandBase.getClass(), commandBase));
    	logger.finest(String.format("Input parameter command type: %s; value: %s", command.getClass(), command));
    	
        this.command = command;
        this.commandBase = commandBase;
        
        logger.finest(String.format("Ending execution of constructor - QAC"));
    }
    
    @Override
    public String getProductVersion() {
        logger.finest(String.format("Starting execution of method - getProductVersion()"));
        
        String productVersion = "Unknown";
        CmdResult res = null;
        File f = null;
        
        try {
            f = File.createTempFile("test_prqa_file", ".c");
            
            res = PRQACommandLineUtility.run(String.format("qac -version \"%s\"", f.getAbsolutePath()), new File(commandBase));
  
        } catch (Exception ex) {
             logger.warning("Failed to get qac version");
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
                productVersion = s;
                break;
            }
        }
       
        logger.finest(String.format("Returning value %s", productVersion));
        
        return productVersion;
    }

    @Override
    public String toString() {
        return "qac";
    }

    @Override
    public CmdResult analyze() throws PrqaException {
        return PRQACommandLineUtility.run(getBuilder().getCommand(), new File(commandBase)); 
    }

    @Override
    public PRQACommandBuilder getBuilder() {
        return builder;
    }
    
}
