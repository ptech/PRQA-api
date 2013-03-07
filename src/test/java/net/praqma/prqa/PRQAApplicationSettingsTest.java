/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import org.junit.Test;
import static org.junit.Assert.*;

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
        PRQAApplicationSettings settings = new PRQAApplicationSettings(null, null, null);
        assertEquals(qarPl, settings.resolveQarExe(true));
        assertEquals(qar, settings.resolveQarExe(false));
        assertEquals(qaw, settings.resolveQawExe(true));
        assertEquals(qaw, settings.resolveQawExe(false));
        assertEquals(slashedValue, PRQAApplicationSettings.addSlash(slashedValue, System.getProperty("file.separator")));
        assertEquals(slashedValue, PRQAApplicationSettings.addSlash(nonSlashedValue, System.getProperty("file.separator")));
                
    }
    
}
