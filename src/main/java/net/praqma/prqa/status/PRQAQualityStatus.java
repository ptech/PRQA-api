/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 *
 * @author Praqma
 */
public class PRQAQualityStatus extends PRQAStatus {
    
    private int totalNumberOfFiles;
    private int linesOfCode;
    private int numberOfSourceFiles;
    private int numberOfFileMetrics;
    private int numberOfFunctions;
    private int numberOfFunctionMetrics;
    
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Number getReadout(StatusCategory category) throws PrqaException.PrqaReadingException {
        switch(category) {
            case NumberOfFunctionMetrics:
                return getNumberOfFunctionMetrics();
            case NumberOfFunctions:
                return getNumberOfFunctions();
            case NumberOfSourceFiles:
                return getNumberOfSourceFiles();
            case TotalNumberOfFiles:
                return getTotalNumberOfFiles();
            case LinesOfCode:
                return getLinesOfCode();
            case NumberOfFileMetrics:
                return getNumberOfFileMetrics();
            default:
                throw new PrqaException.PrqaReadingException(String.format("Dident find category %s for class %s", category, this.getClass()));
        }
    }

    @Override
    public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
        switch(category) {
            case NumberOfFunctionMetrics:
                setNumberOfFunctionMetrics(value.intValue());
                break;
            case NumberOfFunctions:
                setNumberOfFunctions(value.intValue());
                break;
            case NumberOfSourceFiles:
                setNumberOfSourceFiles(value.intValue());
                break;
            case TotalNumberOfFiles:
                setTotalNumberOfFiles(value.intValue());
                break;
            case LinesOfCode:
                setLinesOfCode(value.intValue());
                break;
            case NumberOfFileMetrics:
                setNumberOfFileMetrics(value.intValue());
                break;
            default:
                throw new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s",value,category,this.getClass()));
        }
    }

    /**
     * @return the totalNumberOfFiles
     */
    public int getTotalNumberOfFiles() {
        return totalNumberOfFiles;
    }

    /**
     * @param totalNumberOfFiles the totalNumberOfFiles to set
     */
    public void setTotalNumberOfFiles(int totalNumberOfFiles) {
        this.totalNumberOfFiles = totalNumberOfFiles;
    }

    /**
     * @return the linesOfCode
     */
    public int getLinesOfCode() {
        return linesOfCode;
    }

    /**
     * @param linesOfCode the linesOfCode to set
     */
    public void setLinesOfCode(int linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

    /**
     * @return the numberOfSourceFiles
     */
    public int getNumberOfSourceFiles() {
        return numberOfSourceFiles;
    }

    /**
     * @param numberOfSourceFiles the numberOfSourceFiles to set
     */
    public void setNumberOfSourceFiles(int numberOfSourceFiles) {
        this.numberOfSourceFiles = numberOfSourceFiles;
    }

    /**
     * @return the numberOfFileMetrics
     */
    public int getNumberOfFileMetrics() {
        return numberOfFileMetrics;
    }

    /**
     * @param numberOfFileMetrics the numberOfFileMetrics to set
     */
    public void setNumberOfFileMetrics(int numberOfFileMetrics) {
        this.numberOfFileMetrics = numberOfFileMetrics;
    }

    /**
     * @return the numberOfFunctions
     */
    public int getNumberOfFunctions() {
        return numberOfFunctions;
    }

    /**
     * @param numberOfFunctions the numberOfFunctions to set
     */
    public void setNumberOfFunctions(int numberOfFunctions) {
        this.numberOfFunctions = numberOfFunctions;
    }

    /**
     * @return the numberOfFunctionMetrics
     */
    public int getNumberOfFunctionMetrics() {
        return numberOfFunctionMetrics;
    }

    /**
     * @param numberOfFunctionMetrics the numberOfFunctionMetrics to set
     */
    public void setNumberOfFunctionMetrics(int numberOfFunctionMetrics) {
        this.numberOfFunctionMetrics = numberOfFunctionMetrics;
    }

    @Override
    public String toString() {
        String res = "";
        res += "Total Number of Files: " + totalNumberOfFiles + "\n";
        res += "Lines of Code: " + linesOfCode + "\n";
        res += "Number of Source Files: " + numberOfSourceFiles + "\n";
        res += "Number of File Metrics: " + numberOfFileMetrics + "\n";
        res += "Number of Functions: " + numberOfFunctions + "\n";
        res += "Number of Function Metrics: "+ numberOfFunctionMetrics + "\n";        
        return res;
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style=\"border:solid;border-width:1px;padding:15px;margin:10px;\">");
        sb.append("<h2>Quality Summary</h2>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Total Number of Files</th>");
        sb.append("<th>Lines of Code</th>");
        sb.append("<th>Number of Source Files</th>");
        sb.append("<th>Number of File Metrics</th>");
        sb.append("<th>Number of Functions</th>");
        sb.append("<th>Number of Function Metrics</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>").append(getTotalNumberOfFiles()).append("</td>");
        sb.append("<td>").append(getLinesOfCode()).append("</td>");
        sb.append("<td>").append(getNumberOfSourceFiles()).append("</td>");
        sb.append("<td>").append(getNumberOfFileMetrics()).append("</td>");
        sb.append("<td>").append(getNumberOfFunctions()).append("</td>");
        sb.append("<td>").append(getNumberOfFunctionMetrics()).append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }
}
