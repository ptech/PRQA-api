/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import org.junit.Test;

/**
 *
 * @author Praqma
 */
public class PRQAReportSettingsTest {
    @Test public void testPRQAReportSettingsInitializationTest() {
        PRQAReportSettings settings = new PRQAReportSettings("Myserver", "Myproject.prj", true, false, true, true, null, null);
        PRQAReportSettings settings2 = new PRQAReportSettings("Myserver", "Myproject.prj", true, false, true, true, null, null,"via.settings");
    }
}
