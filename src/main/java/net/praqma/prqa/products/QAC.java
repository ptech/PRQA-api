/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import net.praqma.prqa.exceptions.PrqaSetupException;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author jes, man
 */
public class QAC implements Product {
    
    private static final Logger logger = Logger.getLogger(QAC.class.getName());
    public static String[] envVarsForTool = { "QACBIN" , "QACPATH" , "QACOUTPATH" , "QACHELPFILES" , "QACTEMP" };
    
    public QAC() {
        
    }
    
    public QAC(String installationDir) {
        
    }
       
    @Override
    public String getProductVersion(HashMap<String,String> environment, File workspace, boolean isUnix) throws PrqaSetupException {
        logger.finest(String.format("Starting execution of method - getProductVersion()"));
            
        String productVersion = "Unknown";
        CmdResult res = null;
        File f = null;
        
        try {
            f = File.createTempFile("test_prqa_file", ".c", workspace);            
            //Check if QACBIN points to a real directory            
            res = CommandLine.getInstance().run(String.format("qac -version \"%s\"", f.getAbsolutePath()), workspace, true, false, environment);
  
        } catch (AbnormalProcessTerminationException abnex) {
             logger.warning("Failed to get qac version");             
             throw new PrqaSetupException(String.format( "Failed to detect QA·C version with command %s returned code %s\nMessage was:\n%s", abnex.getCommand(),abnex.getExitValue(),abnex.getMessage()), abnex);
        } catch (IOException ioex) {
            logger.warning("IOException...failed to delete");
        } finally {
            if(f != null) {
                try {
                    FileFilter  ff = new WildcardFileFilter("test_prqa_file*");                
                    for(File deleteme : workspace.listFiles(ff)) {
                        logger.finest(String.format("Starting to delete file: %s", deleteme.getAbsolutePath()));
                        if(deleteme.delete()) {
                            logger.finest(String.format("Succesfully deleted file: %s", deleteme.getAbsolutePath()));
                        } else {
                            logger.warning(String.format("Failed to delete: %s", deleteme.getAbsolutePath()));
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
}
