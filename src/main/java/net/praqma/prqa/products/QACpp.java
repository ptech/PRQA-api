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
 * @author jes
 */
public class QACpp implements Product{

    private static final Logger logger = Logger.getLogger(QACpp.class.getName());
    public static String[] envVarsForTool = { "QACPPBIN" , "QACPPPATH" , "QACPPOUTPATH" , "QACPPHELPFILES" , "QACPPTEMP" };
    
    @Override
    public String getProductVersion(HashMap<String,String> environment, File workspace, boolean isUnix) throws PrqaSetupException {
        logger.finest(String.format("Starting execution of method - getProductVersion"));
        
        String version = "Unknown";
        
        CmdResult res = null;
        File f = null;
        try {
            f = File.createTempFile("test_prqa_file", ".c");
            res = CommandLine.getInstance().run(String.format("qacpp -version \"%s\"", f.getAbsolutePath()), workspace, true, false, environment);
        
        } catch (AbnormalProcessTerminationException ex) {
            logger.warning("Failed to get qacpp-version");
            throw new PrqaSetupException(String.format( "Failed to detect QAcpp version with command %s returned code %s\nMessage was:\n%s", ex.getCommand(), ex.getExitValue(), ex.getMessage()), ex);
        } catch (IOException ex) {
            logger.warning("Failed to create file");
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
            if(res.stdoutList.size() > 0) {
                version = res.stdoutList.get(0);
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