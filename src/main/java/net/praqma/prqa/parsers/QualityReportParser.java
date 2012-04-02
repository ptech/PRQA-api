/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.parsers;

import java.util.regex.Pattern;

/**
 *
 * @author Praqma
 */
public class QualityReportParser extends ReportHtmlParser {
    public static Pattern totalNumberOfFilesPattern;
    public static Pattern linesOfCodePattern;
    public static Pattern numberOfSourceFilesPattern;
    public static Pattern numberOfFileMetricsPattern;
    public static Pattern numberOfFunctionsPattern;
    public static Pattern numberOfFunctionsMetricPattern;
    public static Pattern numberOfClassMetrics;
    public static Pattern numberOfClasses;
    
    static {       
        totalNumberOfFilesPattern = Pattern.compile("<td align=\"left\">Total Number of Files</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        linesOfCodePattern = Pattern.compile("<td align=\"left\">Lines of Code</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        numberOfSourceFilesPattern = Pattern.compile("<td align=\"left\">Number of Source Files</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        numberOfFileMetricsPattern = Pattern.compile("<td align=\"left\">Number of File Metrics</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        numberOfFunctionsPattern = Pattern.compile("<td align=\"left\">Number of Functions</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        numberOfFunctionsMetricPattern = Pattern.compile("<td align=\"left\">Number of Function Metrics</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        
        //Cpp/Java analysis:
        numberOfClassMetrics = Pattern.compile("<td align=\"left\">Number of Class Metrics</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        numberOfClasses = Pattern.compile("<td align=\"left\">Number of Classes</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        
    }
}
