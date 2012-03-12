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
import net.praqma.jenkins.plugin.prqa.PrqaException.PrqaParserException;

/**
 *
 * @author Praqma
 */
public abstract class ReportHtmlParser {
    public static Pattern iconLinkPattern = Pattern.compile("<img(.+)src=\"[^\"]+\"(.*)>");
    
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
            throw new PrqaException.PrqaParserException("Could not find file "+file.getPath(),ex);
        }

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader source = new BufferedReader(isr);
        String sourceLine = "";
        String report = "";
        try {
            while ((sourceLine = source.readLine()) != null) {
                report += sourceLine+System.getProperty("line.separator");
            }
            source.close();
        } catch (IOException ex) {
            throw new PrqaException.PrqaParserException("Could not read the line after :\n" + sourceLine,ex);
        }
        Matcher match = pattern.matcher(report);
        while (match.find()) {
            result.add(match.group(1));
        }
            

        return result;
    }
    
    public int replace(String path, Pattern pattern, String replacement) throws PrqaParserException {
        int numberOfReplacements = 0;
        File file = new File(path);
        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new PrqaException.PrqaParserException("Could not find file "+file.getPath(),ex);
        } 
        //First read in the entire file. One line at a time. Append this
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader source = new BufferedReader(isr);
        
        
        
        String sourceLine = "";
        String result = "";
        try {
            while ((sourceLine = source.readLine()) != null) {
                Matcher m = pattern.matcher(sourceLine);
                if(m.matches()) {
                    sourceLine = m.replaceFirst(replacement);
                }
                result += sourceLine + System.getProperty("line.separator");
            }
            source.close();
        } catch (IOException ex) {
            throw new PrqaException.PrqaParserException("Could not read the line after :\n" + sourceLine,ex);
        }
        
        
        //Delete original: 
        file.delete();
        
        //Write substituted string to file:
        File fileNew = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(fileNew);
            PrintWriter pw = new PrintWriter(fos);
            pw.print(result);
            pw.close();
        } catch (FileNotFoundException ex) {
            throw new PrqaException.PrqaParserException("Could not file:" +file.getPath(),ex);
        }
         
        return numberOfReplacements;
    }
    
    public int replaceIcon(String path, String iconaddress) throws PrqaParserException {
        return replace(path,ReportHtmlParser.iconLinkPattern,ReportHtmlParser.createComplianceLogoLocation(iconaddress));
    }
    
    public static String createComplianceLogoLocation(String logopath)  {
        return String.format("<img src=\"%s\" alt=\"logo\"/>", logopath);
    }
}
