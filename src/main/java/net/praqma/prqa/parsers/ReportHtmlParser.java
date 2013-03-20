/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.parsers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.exceptions.PrqaParserException;

/**
 *
 * @author Praqma
 */
public abstract class ReportHtmlParser implements Serializable {

    
    
    protected String fullReportPath;
    private static final Logger logger;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    static {
        logger = Logger.getLogger(ReportHtmlParser.class.getName());
    }
    
    public ReportHtmlParser() {
        
    }
    
    public ReportHtmlParser(String fullReportPath) {
        this.fullReportPath = fullReportPath;
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
        logger.finest(String.format("Returning value: %s", this.fullReportPath));
        return this.fullReportPath;
    }

    public void setFullReportPath(String fullReportPath) {
        logger.finest(String.format("Starting execution of method - setFullReportPath"));
        this.fullReportPath = fullReportPath;
        logger.finest(String.format("Ending execution of method - setFullReportPath"));
    }

    public String getResult(Pattern pattern) throws PrqaException {
        logger.finest(String.format("Starting execution of method - getResult"));
        String output = getFirstResult(parse(this.fullReportPath, pattern));
        logger.finest(String.format("Returning value: %s", output));
        return output;
    }

    public List<String> parse(String path, Pattern pattern) throws PrqaParserException {
        logger.finest(String.format("Starting execution of method - parse"));

        List<String> result = new ArrayList<String>();
        File file = new File(path);
        FileInputStream fis;

        logger.finest(String.format("Attempting to open filepath: " + file.getAbsolutePath()));
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            PrqaParserException exception = new PrqaParserException(ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        }
        logger.finest(String.format("File opened successfully!"));

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader source = new BufferedReader(isr);
        String sourceLine = "";
        String report = "";
        Matcher match = null;

        logger.finest(String.format("Attempting to read the file..."));

        try {
            while ((sourceLine = source.readLine()) != null) {
                report += sourceLine + ReportHtmlParser.LINE_SEPARATOR;
                match = pattern.matcher(report);

                while (match.find()) {
                    logger.finest(String.format("Match found!"));

                    result.add(match.group(1));

                    logger.finest(String.format("Returning result:"));
                    for (String s : result) {
                        logger.finest(String.format("    %s", s));
                    }

                    return result;
                }
            }
        } catch (IOException ex) {
            PrqaParserException exception = new PrqaParserException(ex);

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
        } finally {

            logger.finest(String.format("Atempting to close the file"));

            try {
                source.close();
            } catch (IOException ex) {
                PrqaParserException exception = new PrqaParserException(ex);
                logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));
                throw exception;
            }

            logger.finest(String.format("File closed successfully"));

        }

        logger.finest(String.format("File read successfully!"));
        
        logger.finest(String.format("Returning result:"));
        for (String s : result) {
            logger.finest(String.format("    %s", s));
        }

        return result;
    }

    public String getFirstResult(List<String> results) {
        logger.finest(String.format("Starting execution of method - getFirstResult"));
        if (results.size() > 0) {
            String output = results.get(0);
            logger.finest(String.format("Returning value: %s", output));
            return output;
        }

        logger.finest(String.format("Collection is empty, returning null."));
        return null;
    }
}

