/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import java.util.logging.Level;
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
        logger.log(Level.FINEST, "Starting execution of method - isValid");

        UnsupportedOperationException exception;
        exception = new UnsupportedOperationException("Not supported yet.");

        logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});

        throw exception;
    }

    @Override
    public Number getReadout(StatusCategory category) throws PrqaException.PrqaReadingException {
        logger.log(Level.FINEST, "Starting execution of method - getReadout");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{category.getClass(), category});

        switch (category) {
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
                PrqaException.PrqaReadingException exception;
                exception = new PrqaException.PrqaReadingException(String.format("Dident find category %s for class %s", category, this.getClass()));

                logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});

                throw exception;
        }
    }

    @Override
    public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
        logger.log(Level.FINEST, "Starting execution of method - setReadout");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{category.getClass(), category});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{value.getClass(), value});

        switch (category) {
            case TotalNumberOfFiles:
                setNumberOfFiles(value.intValue());
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            case LinesOfCode:
                setLinesOfCode(value.intValue());
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            case UniqueMessagesSupperessed:
                setUniqueMsgsSuppressed(value.intValue());
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            case MessagesSuppressed:
                setMsgsSuppressed(value.intValue());
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            case PercentageMessagesSuppressed:
                setPctMsgsSuppressed(value.doubleValue());
                
                logger.log(Level.FINEST, "Ending execution of method - setReadout");
                
                break;
            default:
                PrqaException.PrqaReadingException exception;
                exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s", value, category, this.getClass()));

                logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});

                throw exception;
                
                
        }
    }

    /**
     * @return the numberOfFiles
     */
    public int getNumberOfFiles() {
        logger.log(Level.FINEST, "Starting execution of method - getNumberOfFiles");
        logger.log(Level.FINEST, "Returning value: {0}", numberOfFiles);
        
        return numberOfFiles;
    }

    /**
     * @param numberOfFiles the numberOfFiles to set
     */
    public void setNumberOfFiles(int numberOfFiles) {
        logger.log(Level.FINEST, "Starting execution of method - setNumberOfFiles");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfFiles});

        this.numberOfFiles = numberOfFiles;

        logger.log(Level.FINEST, "Ending execution of method - setNumberOfFiles");
    }

    /**
     * @return the linesOfCode
     */
    public int getLinesOfCode() {
        logger.log(Level.FINEST, "Starting execution of method - getLinesOfCode");
        logger.log(Level.FINEST, "Returning value: {0}", linesOfCode);
        
        return linesOfCode;
    }

    /**
     * @param linesOfCode the linesOfCode to set
     */
    public void setLinesOfCode(int linesOfCode) {
        logger.log(Level.FINEST, "Starting execution of method - setLinesOfCode");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", linesOfCode});
        
        this.linesOfCode = linesOfCode;
        
        logger.log(Level.FINEST, "Ending execution of method - setLinesOfCode");
    }

    /**
     * @return the uniqueMsgsSuppressed
     */
    public int getUniqueMsgsSuppressed() {
        logger.log(Level.FINEST, "Starting execution of method - getUniqueMsgsSuppressed");
        logger.log(Level.FINEST, "Returning value: {0}", uniqueMsgsSuppressed);
        
        return uniqueMsgsSuppressed;
    }

    /**
     * @param uniqueMsgsSuppressed the uniqueMsgsSuppressed to set
     */
    public void setUniqueMsgsSuppressed(int uniqueMsgsSuppressed) {
        logger.log(Level.FINEST, "Starting execution of method - setUniqueMsgsSuppressed");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", uniqueMsgsSuppressed});
        
        this.uniqueMsgsSuppressed = uniqueMsgsSuppressed;
        
        logger.log(Level.FINEST, "Ending execution of method - setUniqueMsgsSuppressed");
    }

    /**
     * @return the msgsSuppressed
     */
    public int getMsgsSuppressed() {
        logger.log(Level.FINEST, "Starting execution of method - getMsgsSuppressed");
        logger.log(Level.FINEST, "Returning value: {0}", msgsSuppressed);
        
        return msgsSuppressed;
    }

    /**
     * @param msgsSuppressed the msgsSuppressed to set
     */
    public void setMsgsSuppressed(int msgsSuppressed) {
        logger.log(Level.FINEST, "Starting execution of method - setMsgsSuppressed");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", msgsSuppressed});
        
        this.msgsSuppressed = msgsSuppressed;
        
        logger.log(Level.FINEST, "Ending execution of method - setMsgsSuppressed");
    }

    /**
     * @return the pctMsgsSuppressed
     */
    public double getPctMsgsSuppressed() {
        logger.log(Level.FINEST, "Starting execution of method - getPctMsgsSuppressed");
        logger.log(Level.FINEST, "Returning value: {0}", pctMsgsSuppressed);
        
        return pctMsgsSuppressed;
    }

    /**
     * @param pctMsgsSuppressed the pctMsgsSuppressed to set
     */
    public void setPctMsgsSuppressed(double pctMsgsSuppressed) {
        logger.log(Level.FINEST, "Starting execution of method - setPctMsgsSuppressed");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"double", pctMsgsSuppressed});
        
        this.pctMsgsSuppressed = pctMsgsSuppressed;
        
        logger.log(Level.FINEST, "Ending execution of method - setPctMsgsSuppressed");
    }

    @Override
    public String toString() {
        String res = "";
        res += "Scanned the following supression report values: " + System.getProperty("line.separator");
        res += "Number of Files: " + numberOfFiles + "\n" + System.getProperty("line.separator");
        res += "Lines of Code: " + linesOfCode + System.getProperty("line.separator");
        res += "Unique Messages Suppressed: " + uniqueMsgsSuppressed + System.getProperty("line.separator");
        res += "Number of Messages Suppressed: " + msgsSuppressed + System.getProperty("line.separator");
        res += "Percentage of Messages Suppressed: " + pctMsgsSuppressed + System.getProperty("line.separator");
        
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
