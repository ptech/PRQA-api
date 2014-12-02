/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.reports.PRQAReport;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Praqma
 */
public class PRQAReportTest {

	static PRQAReportSettings repSettings;
	static QAVerifyServerSettings serverSettings;
	static PRQAToolUploadSettings uploadSettings;
	static PRQAApplicationSettings appSettings;
	static File tmpDir;
	static boolean isUnix;
	static String mockProjectFile;

	@BeforeClass
	public static void setup() {
		mockProjectFile = new File(tmpDir, "mock.prj").getAbsolutePath();
		repSettings = new PRQAReportSettings(null, mockProjectFile, true, false, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
		serverSettings = new QAVerifyServerSettings("localhost", 8080, "http", "admin", "admin");
		appSettings = new PRQAApplicationSettings(null, null, null, null);
		uploadSettings = new PRQAToolUploadSettings(null, false, CodeUploadSetting.AllCode, null, "projectName");
		tmpDir = new File(System.getProperty("java.io.tmpdir"));
		isUnix = System.getProperty("os.name").startsWith("Windows");

	}

	@Test
	public void testPrqaReport() {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
	}

	@Test
	public void testProjectFilePathResolution() {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
	}

	@Test(expected = PrqaException.class)
	public void testProjectFileNotFoundResolution() throws PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		report.resolveAbsOrRelativePath(tmpDir, "notFound.prj");
	}

	@Test(expected = PrqaException.class)
	public void testProjectFileNotFoundAbsResolution() throws PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		File absFile = new File(tmpDir, "notFound.prj");
		report.resolveAbsOrRelativePath(tmpDir, absFile.getAbsolutePath());
	}

	@Test
	public void testProjectFileFound() throws PrqaException, IOException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		File tmpFileToCreate = new File(tmpDir, "found.prj");
		tmpFileToCreate.createNewFile();

		report.resolveAbsOrRelativePath(tmpDir, "found.prj");
		report.resolveAbsOrRelativePath(null, tmpFileToCreate.getPath());
		tmpFileToCreate.deleteOnExit();
	}

	/*
	 * @Test(expected=PrqaException.class) public void
	 * testProjectCommandNoProjectFileGenerator() throws PrqaException {
	 * PRQAReport report = new PRQAReport(repSettings, serverSettings,
	 * uploadSettings, appSettings); String command =
	 * report.createAnalysisCommand(isUnix); }
	 */

	@Test
	public void testProjectCommandGenerator() throws PrqaException, IOException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		new File(mockProjectFile).createNewFile();
		String command = report.createAnalysisCommand(isUnix);
		assertNotNull(command);
		new File(mockProjectFile).deleteOnExit();
	}

	@Test
	public void testReportCommandGenerator() throws IOException, PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		new File(mockProjectFile).createNewFile();
		String command = report.createReportCommand(isUnix);
		assertNotNull(command);
		new File(mockProjectFile).deleteOnExit();
	}
/*
	@Test
	public void testReportCommandGeneratorQacli() throws IOException, PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		new File(mockProjectFile).createNewFile();
		String command = report.createReportCommandForQacli(isUnix);
		assertNotNull(command);
		new File(mockProjectFile).deleteOnExit();
	}
*/
	@Test
	public void testUploadCommandGenerator() throws IOException, PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		new File(mockProjectFile).createNewFile();
		String command = report.createUploadCommand();
		assertNull(command);
		PRQAReportSettings enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES,
				"qac");
		report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		String command2 = report.createUploadCommand();
		assertNotNull(command2);
		new File(mockProjectFile).deleteOnExit();
	}

/*	@Test
	public void testUploadCommandGeneratorQacli() throws IOException, PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		new File(mockProjectFile).createNewFile();
		String command = report.createUploadCommandQacli();
		assertNull(command);
		PRQAReportSettings enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES,
				"qac");
		report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		String command2 = report.createUploadCommandQacli();
		assertNotNull(command2);
		new File(mockProjectFile).deleteOnExit();
	}

	@Test
	public void testAnalyzeCommandGeneratorQacli() throws IOException, PrqaException {
		PRQAReport report = new PRQAReport(repSettings, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		new File(mockProjectFile).createNewFile();
		String command = report.createAnalysisCommandForQacli(isUnix);
		PRQAReportSettings enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES,
				"qac");
		report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		String command2 = report.createAnalysisCommandForQacli(isUnix);
		assertNotNull(command2);
		enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
		report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		String command3 = report.createAnalysisCommandForQacli(isUnix);
		assertNotNull(command3);
		enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
		report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		String command4 = report.createAnalysisCommandForQacli(isUnix);
		assertNotNull(command4);
		enableUpload = new PRQAReportSettings(null, mockProjectFile, true, true, true, true, PRQAContext.QARReportType.REQUIRED_TYPES, "qac");
		report = new PRQAReport(enableUpload, serverSettings, uploadSettings, appSettings);
		report.setWorkspace(tmpDir);
		String command5 = report.createAnalysisCommandForQacli(isUnix);
		assertNotNull(command5);
		new File(mockProjectFile).deleteOnExit();
	}

	private void writeIntoAFile(String command, String filePath) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath, "UTF-8");
			writer.println(command);
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
