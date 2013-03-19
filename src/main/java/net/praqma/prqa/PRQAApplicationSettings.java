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
    public final String productHome;

    public PRQAApplicationSettings(final String qarHome, final String qavClientHome, final String qawHome, final String productHome) {
        this.qarHome = qarHome;
        this.qavClientHome = qavClientHome;
        this.qawHome = qawHome;
        this.productHome = productHome;
    }
    
    public static String resolveQarExe(boolean isUnix) {
        if(isUnix) {
            return "qar.pl";
        } else {
            return "qar";
        }
    }
    
    public static String resolveQawExe(boolean isUnix) {
        if(isUnix) {
            return "qaw";
        } else {
            return "qaw";
        }
    }
    
    public static String addSlash(String value, String pathSeperator) {
        if(value.endsWith(pathSeperator)) {
            return value;
        } else {
            return value+pathSeperator;
        }
    }

}
