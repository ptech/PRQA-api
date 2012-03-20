/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.Serializable;

/**
 *
 * @author Praqma
 */
public abstract class PRQAStatus implements PRQAReading,Serializable {
    public enum StatusCategory {
        Messages,
        ProjectCompliance,
        FileCompliance;
    

        @Override
        public String toString() {
            switch(this) {
                case ProjectCompliance:
                    return "ProjectCompliance";
                case Messages:
                    return "Messages";
                case FileCompliance:
                    return "FileCompliance";
                default:
                    throw new IllegalArgumentException("Invalid complianace category");
            }
        }
    }
       
    /**
     * Tests whether the result contains valid values. in some cases we could end up in situation where an "empty" result would be created.
     * @return 
     */
    public abstract boolean isValid();
}
