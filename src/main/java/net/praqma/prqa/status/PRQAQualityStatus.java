/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import net.praqma.prqa.PRQAStatus;

/**
 *
 * @author Praqma
 */
public class PRQAQualityStatus extends PRQAStatus {

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
    public void addNotification(String notificaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disable(StatusCategory category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
