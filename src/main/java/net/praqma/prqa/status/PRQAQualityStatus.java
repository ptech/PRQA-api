/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

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
    public Number getReadout(StatusCategory category) {
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
        }
        return null;
    }

    @Override
    public void setReadout(StatusCategory category, Number value) {
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
    
     
   
}
