/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.parsers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 *
 * @author Praqma
 */
public abstract class ReportHtmlParser {
    
    /***
     * Parse method. Takes a path to a file, and a pattern for which to scan for. 
     * @param path
     * @param pattern
     * @return
     * @throws PrqaException 
     */
    public List<String> parse(String path, Pattern pattern) throws PrqaException {
               List<String> result = new ArrayList<String>();
        File file = new File(path);
        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new PrqaException("Could not find file " + file.getPath());
        }

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader source = new BufferedReader(isr);
        String sourceLine = "";
        String report = "";
        try {
            while ((sourceLine = source.readLine()) != null) {
                report += sourceLine + "\n";
            }
        } catch (IOException ex) {
            throw new PrqaException("Could not read the line after :\n" + sourceLine);
        }
        Matcher match = pattern.matcher(report);
        while (match.find()) {
            result.add(match.group(1));
        }


        return result;
    }        
}
