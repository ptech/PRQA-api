/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

/**
 *
 * @author Praqma
 */
public class PRQACodeReviewStatus extends PRQAStatus {

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
        res += "Scanned the following CodeReview values"+"\n";
        return res;
    }
    
    
    
}
