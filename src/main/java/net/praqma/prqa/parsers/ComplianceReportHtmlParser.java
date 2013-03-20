package net.praqma.prqa.parsers;

import java.util.regex.Pattern;

/**
 *
 * @author jes,
 * 
 * Refactored the method. Now the parser is self contained.
 */
public class ComplianceReportHtmlParser extends ReportHtmlParser {
    
    public static final Pattern totalMessagesPattern;
    public static final Pattern fileCompliancePattern;
    public static final Pattern projectCompliancePattern;    
    public static final Pattern[] levelNMessages;  

    
    public ComplianceReportHtmlParser() { }
           
    public ComplianceReportHtmlParser(String fullReportPath) {
        super(fullReportPath);
    }
    
    static {
        levelNMessages = new Pattern[10];
        for(int i=0; i<10; i++) {
            levelNMessages[i] = Pattern.compile(String.format("Level %s \\(([0-9]+)\\)",i));
        }        
        totalMessagesPattern = Pattern.compile("<td align=\"left\">Total Number of Messages</td>\\r?\\n<td align=\"right\">(\\d*)</td>");
        fileCompliancePattern = Pattern.compile("<td align=\"left\">File Compliance Index</td>\\r?\\n<td align=\"right\">(\\S*)%</td>");
        projectCompliancePattern = Pattern.compile("<td align=\"left\">Project Compliance Index</td>\\r?\\n<td align=\"right\">(\\S*)%</td>");      
    }
}

