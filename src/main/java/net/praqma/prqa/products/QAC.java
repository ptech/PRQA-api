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
 * @author jes, man
 */
public class QAC implements Product {

    private static final Logger logger = Logger.getLogger(QAC.class.getName());
    public static String[] envVarsForTool = {"QACBIN", "QACPATH", "QACOUTPATH", "QACHELPFILES", "QACTEMP"};

    public QAC() {
    }

    @Override
    public final String getProductVersion(HashMap<String, String> environment, File workspace, boolean isUnix) throws PrqaSetupException {
        logger.finest(String.format("Starting execution of method - getProductVersion()"));

        String productVersion = null;
        CmdResult res = null;
        File f = null;

        try {
            f = File.createTempFile("test_prqa_file", ".c", workspace);
            logger.fine(String.format("Created dummy test file for version detection: %s", f.getAbsolutePath()));
            res = CommandLine.getInstance().run(String.format("qac -version \"%s\"", f.getAbsolutePath()), workspace, true, false, environment);

        } catch (AbnormalProcessTerminationException abnex) {
            logger.warning(String.format("Failed to detect QA·C version with command %s returned code %s%nMessage was:%n%s", abnex.getCommand(), abnex.getExitValue(), abnex.getMessage()));
            Map<String, String> systemVars = new HashMap<String, String>();
            systemVars.putAll(System.getenv());

            if (environment != null) {
                systemVars.putAll(environment);
            }
            PRQAReport._logEnv("Error in QAC.getProductVersion() - Printing environment", systemVars);
            throw new PrqaSetupException(String.format("Failed to detect QA·C version%n%s", abnex.getMessage()));

        } catch (IOException ioex) {
            logger.warning("IOException...failed to delete");
        } finally {
            if (f != null) {
                try {
                    //TODO: Consider refactoring this by using an abstract class with a base method instead of an interface
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
                    String qacTemp = null;

                    if (environment != null && environment.containsKey("QACTEMP")) {
                        qacTemp = environment.get("QACTEMP");
                    } else if (System.getenv("QACTEMP") != null) {
                        qacTemp = System.getenv("QACTEMP");
                    }

                    logger.finest(String.format("Cleaning up stale analysis files in %s", qacTemp));
                    File tempDir = new File(qacTemp);
                    for (File deleteme : tempDir.listFiles(ff)) {
                        logger.finest(String.format("Starting to delete file: %s", deleteme.getAbsolutePath()));
                        if (deleteme.delete()) {
                            logger.finest(String.format("Succesfully deleted file: %s", deleteme.getAbsolutePath()));
                        } else {
                            logger.warning(String.format("Failed to delete: %s", deleteme.getAbsolutePath()));
                        }
                    }
                    logger.finest("Done cleaning up stale analysis files");
                } catch (Exception ex) {
                    logger.warning("Something went wrong in getProductVersion() when attempting to delete created files");
                }
            }
        }

        if (res != null && !res.stdoutList.isEmpty()) {
            productVersion = res.stdoutList.get(0);
        }
        logger.finest(String.format("Returning value %s", productVersion));
        return productVersion;
    }
}
