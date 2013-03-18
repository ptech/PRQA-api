/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.reports.PRQAReport;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Praqma
 */
public class PRQAReportTest {
    
    static PRQAReportSettings repSettings;
    static QAVerifyServerSettings serverSettings;
    static PRQAUploadSettings uploadSettings;
    static PRQAApplicationSettings appSettings;
    
    @BeforeClass public static void setup() {
        repSettings = new PRQAReportSettings(null, null, true, false, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
        serverSettings = new QAVerifyServerSettings("localhost", 8080, "http", "admin", "admin");
        appSettings = new PRQAApplicationSettings(null, null, null);
        uploadSettings = new PRQAUploadSettings(null, false, CodeUploadSetting.AllCode, null, "projectName");
    }
    
    @Test public void testPrqaReport() {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
    }
    
    @Test public void testProjectFilePathResolution() {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
    }
    
    @Test(expected=PrqaException.class) public void testProjectFileNotFoundResolution() throws PrqaException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        report.resolveAbsOrRelativePath(new File(System.getProperty("java.io.tmpdir")), "notFound.prj");
    }
    
    @Test public void testProjectFileFound() throws PrqaException, IOException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        File tmpFileToCreate = new File(new File(System.getProperty("java.io.tmpdir")),"found.prj");
        tmpFileToCreate.createNewFile();
        
        report.resolveAbsOrRelativePath(new File(System.getProperty("java.io.tmpdir")), "found.prj");
        report.resolveAbsOrRelativePath(null, tmpFileToCreate.getPath());
        tmpFileToCreate.deleteOnExit();
    }
    
    //TODO: Implement me!
    @Test public void testProjectCommandGenerator() {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);        
    }
}
