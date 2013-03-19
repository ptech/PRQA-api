/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Praqma
 */
public class PRQAApplicationSettingsTest {
    @Test public void testExeResolvers() {
        String qaw = "qaw";
        String qar = "qar";
        String qarPl = "qar.pl";
        String slashedValue = "Slash"+System.getProperty("file.separator");
        String nonSlashedValue = "Slash";
        PRQAApplicationSettings settings = new PRQAApplicationSettings(null, null, null, null);
        assertEquals(qarPl, PRQAApplicationSettings.resolveQarExe(true));
        assertEquals(qar, PRQAApplicationSettings.resolveQarExe(false));
        assertEquals(qaw, PRQAApplicationSettings.resolveQawExe(true));
        assertEquals(qaw, PRQAApplicationSettings.resolveQawExe(false));
        assertEquals(slashedValue, PRQAApplicationSettings.addSlash(slashedValue, System.getProperty("file.separator")));
        assertEquals(slashedValue, PRQAApplicationSettings.addSlash(nonSlashedValue, System.getProperty("file.separator")));
                
    }
    
}
