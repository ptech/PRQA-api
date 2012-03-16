/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.Serializable;
import net.praqma.prqa.PRQAStatus.ComplianceCategory;

/**
 *
 * @author Praqma
 */
public interface PRQAReading extends Serializable {
    public Number getReadout(ComplianceCategory category);
    public void setReadout(ComplianceCategory category, Number value);
    public void addNotification(String notificaction);
    public void disable(ComplianceCategory category);
}
