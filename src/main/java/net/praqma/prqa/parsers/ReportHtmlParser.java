/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.parsers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.jenkins.plugin.prqa.PrqaException.PrqaParserException;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.logging.Config;

/**
 *
 * @author Praqma
 */
public abstract class ReportHtmlParser implements Serializable {

    protected String fullReportPath;
    private static final transient Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    /**
     * *
     * Parse method. Takes a path to a file, and a pattern for which to scan for.
     *
     * @param path
     * @param pattern
     * @return
     * @throws PrqaException
     */

    public String getFullReportPath() {
        logger.finest("Starting execution of method - getFullReportPath");
        logger.finest(String.format("Returning value: %s", this.fullReportPath));

        return this.fullReportPath;
    }

    public void setFullReportPath(String fullReportPath) {
        logger.finest("Starting execution of method - setFullReportPath");
        logger.finest(String.format("Input parameter fullReportPath type: %s; value: %s", fullReportPath.getClass(), fullReportPath));

        this.fullReportPath = fullReportPath;

        logger.finest("Ending execution of method - setFullReportPath");
    }

    public String getResult(Pattern pattern) throws PrqaException {
        logger.finest("Starting execution of method - getResult");
        logger.finest(String.format("Input parameter pattern type: %s; value: %s", pattern.getClass(), pattern));

        String output = getFirstResult(parse(this.fullReportPath, pattern));

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public List<String> parse(String path, Pattern pattern) throws PrqaException {
        logger.finest("Starting execution of method - parse");
        logger.finest(String.format("Input parameter path type: %s; value: %s", path.getClass(), path));
        logger.finest(String.format("Input parameter pattern type: %s; value: %s", pattern.getClass(), pattern));

        List<String> result = new ArrayList<String>();
        File file = new File(path);
        FileInputStream fis;

        logger.finest("Attempting to open filepath: " + file.getAbsolutePath());
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            PrqaException.PrqaParserException exception = new PrqaException.PrqaParserException("Could not find file " + file.getPath(), ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        }
        logger.finest("File opened successfully!");

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader source = new BufferedReader(isr);
        String sourceLine = "";
        String report = "";
        Matcher match = null;

        logger.finest("Attempting to read the file...");

        try {
            while ((sourceLine = source.readLine()) != null) {
                report += sourceLine + PRQACommandLineUtility.LINE_SEPARATOR;
                match = pattern.matcher(report);

                // TODO: Should this not be an if statement instead?
                while (match.find()) {
                    logger.finest("Match found!");

                    result.add(match.group(1));

                    logger.finest(String.format("Returning result:"));
                    // TODO figure out a way to make it so that we only loop through them if logging is enabled
                    for (String s : result) {
                        logger.log(Level.FINEST, "    {0}", s);
                    }

                    return result;
                }
            }
        } catch (IOException ex) {
            PrqaException.PrqaParserException exception = new PrqaException.PrqaParserException("Could not read the line after :\n" + sourceLine, ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        } finally {

            logger.finest("Atempting to close the file");

            try {
                source.close();
            } catch (IOException ex) {
                PrqaException.PrqaParserException exception = new PrqaException.PrqaParserException("Failed to close file after parse!", ex);

                logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

                throw exception;
            }

            logger.finest("File closed successfully");

        }

        logger.finest("File read successfully!");


        // TODO figure out a way to make it so that we only loop through them if logging is enabled
        logger.finest(String.format("Returning result:"));
        for (String s : result) {
            logger.log(Level.FINEST, "    {0}", s);
        }

        return result;
    }

    public String getFirstResult(List<String> results) {
        logger.log(Level.FINEST, "Starting execution of method - getFirstResult");

        if (results.size() > 0) {
            String output = results.get(0);

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        logger.log(Level.FINEST, "Collection is empty, returning null.");

        return null;
    }

    public int replace(String path, Pattern pattern, String replacement) throws PrqaParserException {
        logger.log(Level.FINEST, "Starting execution of method - replace");
        logger.log(Level.FINEST, "Input parameter path type: {0}; value: {1}", new Object[]{path.getClass(), path});
        logger.log(Level.FINEST, "Input parameter pattern type: {0}; value: {1}", new Object[]{pattern.getClass(), pattern.toString()});
        logger.log(Level.FINEST, "Input parameter pattern type: {0}; value: {1}", new Object[]{replacement.getClass(), replacement});

        int numberOfReplacements = 0;
        File file = new File(path);
        FileInputStream fis;

        logger.log(Level.FINEST, "Atempting to open filepath {0}.", file.getAbsolutePath());

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            PrqaException.PrqaParserException exception = new PrqaException.PrqaParserException("Could not find file " + file.getPath(), ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        }

        logger.log(Level.FINEST, "File opened succssfully.");

        //First read in the entire file. One line at a time. Append this
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader source = new BufferedReader(isr);

        String sourceLine = "";
        String result = "";

        logger.log(Level.FINEST, "Atempting to read the file...");
        try {
            while ((sourceLine = source.readLine()) != null) {
                Matcher m = pattern.matcher(sourceLine);

                if (m.matches()) {
                    sourceLine = m.replaceFirst(replacement);
                }

                result += sourceLine + System.getProperty("line.separator");
            }
            source.close();
        } catch (IOException ex) {
            PrqaException.PrqaParserException exception = new PrqaException.PrqaParserException("Could not read the line after :\n" + sourceLine, ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        }
        logger.log(Level.FINEST, "File read successfully!");

        logger.log(Level.FINEST, "Deleting file.");
        //Delete original: 
        file.delete();

        logger.log(Level.FINEST, "Creating substitute file.");
        //Write substituted string to file:
        File fileNew = new File(path);

        logger.log(Level.FINEST, "Attempting to write to substitute file");
        try {
            FileOutputStream fos = new FileOutputStream(fileNew);
            PrintWriter pw = new PrintWriter(fos);
            pw.print(result);
            pw.close();
        } catch (FileNotFoundException ex) {
            PrqaException.PrqaParserException exception = new PrqaException.PrqaParserException("Could not file:" + file.getPath(), ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        }
        logger.log(Level.FINEST, "Successfully wrote to substitute file!");

        logger.log(Level.FINEST, "Returning numberOfReplacements: {0}", numberOfReplacements);

        return numberOfReplacements;
    }
}
