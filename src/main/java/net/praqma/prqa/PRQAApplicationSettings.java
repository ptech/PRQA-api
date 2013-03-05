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
public class PRQAApplicationSettings implements Serializable {
    public final String qarHome;
    public final String qavClientHome;
    public final String qawHome;

    public PRQAApplicationSettings(final String qarHome, final String qavClientHome, final String qawHome) {
        this.qarHome = qarHome;
        this.qavClientHome = qavClientHome;
        this.qawHome = qawHome;
    }
    
    public String resolveQarExe(boolean isUnix) {
        if(isUnix) {
            return "qar.pl";
        } else {
            return "qar";
        }
    }

}
