/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

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
    public Number getReadout(StatusCategory category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setReadout(StatusCategory category, Number value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        String res = "";
        res += "Scanned the following supression report values: \n";
        return res;
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
    
    
    
}
