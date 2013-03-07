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
public class PRQAUploadSettingsTest {
    @Test public void testInitialization() {
        PRQAUploadSettings settings = new PRQAUploadSettings("vcs.xml", false, CodeUploadSetting.AllCode, ".", "MyProjectName");
    }
}
