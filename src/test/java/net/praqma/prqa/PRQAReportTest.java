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
import org.junit.AfterClass;
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
    static File tmpDir;
    static boolean isUnix;
    static String mockProjectFile;
    
    @BeforeClass public static void setup() {
        mockProjectFile = new File(tmpDir,"mock.prj").getAbsolutePath();
        repSettings = new PRQAReportSettings(null, mockProjectFile, true, false, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
        serverSettings = new QAVerifyServerSettings("localhost", 8080, "http", "admin", "admin");
        appSettings = new PRQAApplicationSettings(null, null, null, null);
        uploadSettings = new PRQAUploadSettings(null, false, CodeUploadSetting.AllCode, null, "projectName");
        tmpDir = new File(System.getProperty("java.io.tmpdir"));
        isUnix = System.getProperty("os.name").startsWith("Windows");
        
    }
    
    @Test public void testPrqaReport() {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
    }
    
    @Test public void testProjectFilePathResolution() {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
    }
    
    @Test(expected=PrqaException.class) public void testProjectFileNotFoundResolution() throws PrqaException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        report.resolveAbsOrRelativePath(tmpDir, "notFound.prj");                
    }
    
    @Test(expected=PrqaException.class) public void testProjectFileNotFoundAbsResolution() throws PrqaException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        File absFile = new File(tmpDir,"notFound.prj");
        report.resolveAbsOrRelativePath(tmpDir, absFile.getAbsolutePath());                
    }
    
    @Test public void testProjectFileFound() throws PrqaException, IOException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        File tmpFileToCreate = new File(tmpDir,"found.prj");
        tmpFileToCreate.createNewFile();
        
        report.resolveAbsOrRelativePath(tmpDir, "found.prj");
        report.resolveAbsOrRelativePath(null, tmpFileToCreate.getPath());
        tmpFileToCreate.deleteOnExit();
    }
    /*
    @Test(expected=PrqaException.class) public void testProjectCommandNoProjectFileGenerator() throws PrqaException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        String command = report.createAnalysisCommand(isUnix);                
    }
    */ 
    
    @Test public void testProjectCommandGenerator() throws PrqaException, IOException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);        
        new File(mockProjectFile).createNewFile();
        String command = report.createAnalysisCommand(isUnix);                
        assertNotNull(command);
        new File(mockProjectFile).deleteOnExit();        
    }
    
    @Test public void testReportCommandGenerator() throws IOException, PrqaException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);        
        report.setWorkspace(tmpDir);
        new File(mockProjectFile).createNewFile();
        String command = report.createReportCommand(isUnix);                
        assertNotNull(command);
        new File(mockProjectFile).deleteOnExit();  
    }
    
    @Test public void testUploadCommandGenerator() throws IOException, PrqaException {
        PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
        report.setWorkspace(tmpDir);
        new File(mockProjectFile).createNewFile();
        String command = report.createUploadCommand();
        assertNull(command);
        PRQAReportSettings enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
        report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
        report.setWorkspace(tmpDir);
        String command2 = report.createUploadCommand();
        assertNotNull(command2);
        new File(mockProjectFile).deleteOnExit();  
    }
}
