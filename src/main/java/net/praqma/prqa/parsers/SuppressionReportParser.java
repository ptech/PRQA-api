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
public class SuppressionReportParser extends ReportHtmlParser {
    public static Pattern numberOfFilesPattern;
    public static Pattern linesOfCodePattern;
    public static Pattern uniqueMessagesSuppressedPattern;
    public static Pattern numberOfMessagesSuppressedPattern;
    public static Pattern percentageOfMsgSuppressedPattern;
        
    static {
        numberOfFilesPattern = Pattern.compile("<td align=\"left\">Number of Files</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        linesOfCodePattern = Pattern.compile("<td align=\"left\">Lines of Code</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        uniqueMessagesSuppressedPattern = Pattern.compile("<td align=\"left\">Number of Unique Messages Suppressed</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        numberOfMessagesSuppressedPattern = Pattern.compile("<td align=\"left\">Number of Messages Suppressed</td>\\r?\\n<td align=\"right\">(\\d+)</td>");
        percentageOfMsgSuppressedPattern= Pattern.compile("<td align=\"left\">Percentage of Messages Suppressed</td>\\r?\\n<td align=\"right\">(\\S*)%</td>");
    }
}
