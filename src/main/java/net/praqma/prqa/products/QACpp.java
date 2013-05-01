/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import net.praqma.prqa.exceptions.PrqaSetupException;
import net.praqma.prqa.reports.PRQAReport;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author jes
 */
public class QACpp implements Product {

    private static final Logger logger = Logger.getLogger(QACpp.class.getName());
    public static String[] envVarsForTool = {"QACPPBIN", "QACPPPATH", "QACPPOUTPATH", "QACPPHELPFILES", "QACPPTEMP"};

    public QACpp() {
    }

    @Override
    public final String getProductVersion(HashMap<String, String> environment, File workspace, boolean isUnix) throws PrqaSetupException {
        logger.finest(String.format("Starting execution of method - getProductVersion"));
        String version = null;

        CmdResult res = null;
        File f = null;
        try {
            f = File.createTempFile("test_prqa_file", ".c", workspace);
            res = CommandLine.getInstance().run(String.format("qacpp -version \"%s\"", f.getAbsolutePath()), workspace, true, false, environment);
        } catch (AbnormalProcessTerminationException ex) {
            logger.warning(String.format("Failed to detect QA·C++ version with command %s returned code %s%nMessage was:%n%s", ex.getCommand(), ex.getExitValue(), ex.getMessage()));
            Map<String, String> systemVars = new HashMap<String, String>();
            systemVars.putAll(System.getenv());

            if (environment != null) {
                systemVars.putAll(environment);
            }
            PRQAReport._logEnv("Error in QACpp.getProductVersion() - Printing environment", systemVars);
            throw new PrqaSetupException(String.format("Failed to detect QA·C++ version%n%s", ex.getMessage()));
        } catch (IOException ex) {
            logger.warning("Failed to create file");
        } finally {
            if (f != null) {
                try {
                    logger.finest(String.format("Setting up filter for files to delete"));
                    FileFilter ff = new WildcardFileFilter("test_prqa_file*");

                    for (File deleteme : workspace.listFiles(ff)) {
                        logger.finest(String.format("Starting to delete file: %s", deleteme.getAbsolutePath()));
                        if (deleteme.delete()) {
                            logger.finest(String.format("Succesfully deleted file: %s", deleteme.getAbsolutePath()));
                        } else {
                            logger.warning(String.format("Failed to delete: %s", deleteme.getAbsolutePath()));
                        }
                    }

                    /**
                     * Delete temporary metric files created in the process of
                     * determining product versions.
                     */
                    String qacppTemp = null;

                    if (environment != null && environment.containsKey("QACPPTEMP")) {
                        qacppTemp = environment.get("QACPPTEMP");
                    } else if (System.getenv("QACPPTEMP") != null) {
                        qacppTemp = System.getenv("QACPPTEMP");
                    }

                    logger.finest(String.format("Cleaning up stale analysis files in %s", qacppTemp));
                    File tempDir = new File(qacppTemp);
                    for (File deleteme : tempDir.listFiles(ff)) {
                        logger.finest(String.format("Starting to delete file: %s", deleteme.getAbsolutePath()));
                        if (deleteme.delete()) {
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
        if (res != null && !res.stdoutList.isEmpty()) {
            version = res.stdoutList.get(0);
        }
        logger.finest(String.format("Returning value %s", version));
        return version;
    }

    @Override
    public String toString() {
        return "qacpp";
    }
}
