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
public class PRQASuppressionStatus extends PRQAStatus {
    
    private int numberOfFiles;
    private int linesOfCode;
    private int uniqueMsgsSuppressed;
    private int msgsSuppressed;
    private double pctMsgsSuppressed;

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Number getReadout(StatusCategory category) throws PrqaException.PrqaReadingException {
        switch(category) {
            case TotalNumberOfFiles:
                return getNumberOfFiles();
            case LinesOfCode:
                return getLinesOfCode();
            case UniqueMessagesSupperessed:
                return getUniqueMsgsSuppressed();
            case MessagesSuppressed:
                return getMsgsSuppressed();
            case PercentageMessagesSuppressed:
                return getMsgsSuppressed();
            default:
                throw new PrqaException.PrqaReadingException(String.format("Dident find category %s for class %s", category, this.getClass()));
        }
    }

    @Override
    public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
        switch(category) {
            case TotalNumberOfFiles:
                setNumberOfFiles(value.intValue());
                break;
            case LinesOfCode:
                setLinesOfCode(value.intValue());
                break;
            case UniqueMessagesSupperessed:
                setUniqueMsgsSuppressed(value.intValue());
                break;
            case MessagesSuppressed:
                setMsgsSuppressed(value.intValue());
                break;
            case PercentageMessagesSuppressed:
                setPctMsgsSuppressed(value.doubleValue());
                break;
            default:
                throw new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s",value,category,this.getClass()));
        }
    }

    /**
     * @return the numberOfFiles
     */
    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    /**
     * @param numberOfFiles the numberOfFiles to set
     */
    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
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
     * @return the uniqueMsgsSuppressed
     */
    public int getUniqueMsgsSuppressed() {
        return uniqueMsgsSuppressed;
    }

    /**
     * @param uniqueMsgsSuppressed the uniqueMsgsSuppressed to set
     */
    public void setUniqueMsgsSuppressed(int uniqueMsgsSuppressed) {
        this.uniqueMsgsSuppressed = uniqueMsgsSuppressed;
    }

    /**
     * @return the msgsSuppressed
     */
    public int getMsgsSuppressed() {
        return msgsSuppressed;
    }

    /**
     * @param msgsSuppressed the msgsSuppressed to set
     */
    public void setMsgsSuppressed(int msgsSuppressed) {
        this.msgsSuppressed = msgsSuppressed;
    }

    /**
     * @return the pctMsgsSuppressed
     */
    public double getPctMsgsSuppressed() {
        return pctMsgsSuppressed;
    }

    /**
     * @param pctMsgsSuppressed the pctMsgsSuppressed to set
     */
    public void setPctMsgsSuppressed(double pctMsgsSuppressed) {
        this.pctMsgsSuppressed = pctMsgsSuppressed;
    }
    
    @Override
    public String toString() {
        String res = "";
        res += "Scanned the following supression report values: \n";
        res += "Number of Files: "+numberOfFiles + "\n";
        res += "Lines of Code: "+linesOfCode + "\n";
        res += "Unique Messages Suppressed: " + uniqueMsgsSuppressed + "\n";
        res += "Number of Messages Suppressed: " + msgsSuppressed + "\n";
        res += "Percentage of Messages Suppressed: "+pctMsgsSuppressed + "\n";        
        return res;
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style=\"border:solid;border-width:1px;padding:15px;margin:10px;\">");
        sb.append("<h2>Quality Summary</h2>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Number of Files</th>");
        sb.append("<th>Lines of Code</th>");
        sb.append("<th>Unique Messages Suppressed</th>");
        sb.append("<th>Number of Messages Suppressed</th>");
        sb.append("<th>Percentage of Messages Suppressed</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>").append(getNumberOfFiles()).append("</td>");
        sb.append("<td>").append(getLinesOfCode()).append("</td>");
        sb.append("<td>").append(getUniqueMsgsSuppressed()).append("</td>");
        sb.append("<td>").append(getMsgsSuppressed()).append("</td>");
        sb.append("<td>").append(getPctMsgsSuppressed()).append("%</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }
       
}
