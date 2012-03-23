/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.Serializable;
import net.praqma.prqa.status.PRQAStatus.StatusCategory;

/**
 * Abstracting readings. This means that we can now have a single object for each build. 
 * 
 * @author Praqma
 */
public interface PRQAReading extends Serializable {
    public Number getReadout(StatusCategory category);
    public void setReadout(StatusCategory category, Number value);
    public void addNotification(String notificaction);
    public void disable(StatusCategory category);
}
