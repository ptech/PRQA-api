/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.io.FileFilter;
import java.util.logging.Logger;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.util.execute.CmdResult;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author jes
 */
public class QACpp implements Product{

    private static final Logger logger = Logger.getLogger(QACpp.class.getName());
    
    @Override
    public String getProductVersion() {
        logger.finest(String.format("Starting execution of method - getProductVersion"));
        
        String version = "Unknown";
        
        CmdResult res = null;
        File f = null;
        try {
            f = File.createTempFile("test_prqa_file", ".c");
            
            res = null;
            
            //TODO: Implement
            //res = PRQACommandLineUtility.getInstance(getEnvironment()).run(String.format("qacpp -version \"%s\"", f.getAbsolutePath()), new File(commandBase));
  
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