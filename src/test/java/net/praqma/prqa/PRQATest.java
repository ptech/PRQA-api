package net.praqma.prqa;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import net.praqma.prqa.exceptions.PrqaException;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QAV;
import net.praqma.prqa.status.PRQAComplianceStatus;
import net.praqma.prqa.status.StatusCategory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 *
 * @author Praqma
 */
public class PRQATest extends TestCase {
    private String dummyTestProjectFile = "C:/MyProject/project.prj";
    private String dummyTestOutputFoldder = "C:/Workspace/myproject/jobs/test";
    
    
    private static PRQAStatusCollection collection = null;
    //private static InputStream stream;
    private static File copyResourceToTestFile(Class clazz, String file) throws IOException {
        InputStream is = clazz.getResourceAsStream("Suppression_Report.xhtml");
        assertNotNull(is);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        
        File f = File.createTempFile("testParse", ".xhtml");
        FileWriter fw = new FileWriter(f);
        
        String line;
        while((line = br.readLine()) != null ) {
            fw.write(line+System.getProperty("line.separator"));
        }
        
        fw.close();
        return f;
    }
    
    @Before
    public void initialize() {
        
    }
    
    @BeforeClass 
    public static void testCreateMockCollection () {
        collection = new PRQAStatusCollection();
        PRQAComplianceStatus status = new PRQAComplianceStatus();
        status.setFileCompliance(new Double(10.0));
        status.setProjectCompliance(new Double(20.22));
        status.setMessages(1000);
        
        PRQAComplianceStatus statusTwo = new PRQAComplianceStatus();
        statusTwo.setFileCompliance(new Double(67.45));
        statusTwo.setMessages(20000);
        statusTwo.setProjectCompliance(new Double(56.09));
        
        collection.add(statusTwo);
        collection.add(status);
    }
         
    @Test
    public void testClearOverridesVerification() {
        assertNotNull(collection);
        collection.clearOverrides();
        try {
            assertEquals(collection.getMin(StatusCategory.Messages),new Integer(1000));
            assertEquals(collection.getMax(StatusCategory.Messages),new Integer(20000));        
        } catch (PrqaException ex) {
            fail();
        }
    }
    
    @Test 
    public void testComplianceStatusOverride() {
        try {
            collection.overrideMax(StatusCategory.Messages, 100);
            collection.overrideMin(StatusCategory.Messages, 0);
            
            assertEquals(collection.getMax(StatusCategory.Messages), 100);
            assertEquals(collection.getMin(StatusCategory.Messages), 0);
        } catch (PrqaException ex) {
            fail();
        }
        
    }
    /*
    @Test
    public void testCollectionsWithOtherList() {
        assertNotNull(collection);
        collection.clearOverrides();

        List<PRQAComplianceStatus> list = Arrays.asList(new PRQAComplianceStatus(1,new Double(22),new Double(23)),new PRQAComplianceStatus(2, new Double(23), new Double(67)));       
        collection.addAll(list);
        assertEquals(collection.size(), 4);
        collection.removeAll(list);
        assertEquals(collection.size(), 2);

    }
    */ 
    
    @Test
    public void testResultComparison() {
        PRQAComplianceStatus stat = PRQAComplianceStatus.createEmptyResult();
        PRQAComplianceStatus statTwo = PRQAComplianceStatus.createEmptyResult();
        assertEquals(0, stat.compareTo(statTwo));
        
        statTwo.setProjectCompliance(new Double(50));
        assertEquals(-1, stat.compareTo(statTwo));
        assertEquals(1, statTwo.compareTo(stat));
    }
    
    @Test
    public void testParseComplianceReport() throws IOException, PrqaException {
        InputStream is = this.getClass().getResourceAsStream("Compliance_Report.xhtml");
        assertNotNull(is);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        
        File f = File.createTempFile("testParse", ".xhtml");
        FileWriter fw = new FileWriter(f);
        
        String line;

        
        while((line = br.readLine()) != null ) {
            fw.write(line+System.getProperty("line.separator"));
        }
        
        fw.close();
       
        ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser();
        parser.setFullReportPath(f.getPath());
        
        List<String> listFileC = parser.parse(f.getPath(), ComplianceReportHtmlParser.fileCompliancePattern);
        List<String> listProjC = parser.parse(f.getPath(), ComplianceReportHtmlParser.projectCompliancePattern);
        List<String> listMsg = parser.parse(f.getPath(), ComplianceReportHtmlParser.totalMessagesPattern);
        
        String dman = parser.getResult(ComplianceReportHtmlParser.totalMessagesPattern);
        
        System.out.println("Result was: "+dman);
        
        
        //Assert Not null.
        
        assertNotNull(listFileC);
        assertNotNull(listProjC);
        assertNotNull(listMsg);
        
        //Assert that each list contains EXACTLY 1 element. That is the requirement for the compliance report.
        
        assertEquals(1, listFileC.size());
        assertEquals(1, listProjC.size());
        assertEquals(1, listMsg.size());
         
        String fileName = f.getAbsolutePath();
        System.out.println(String.format("Deleted file %s : %s", fileName, f.delete()));           
    }    
        
    @Test
    public void testParserMethods() {
        ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser();
    }
    
    @Test
    public void testGetReadings() {
        PRQAComplianceStatus status = new PRQAComplianceStatus(100, 100d, 34.5d);
        try {
            for (Number num : status.getReadouts(StatusCategory.FileCompliance,StatusCategory.ProjectCompliance,StatusCategory.Messages).values()){
                assertNotNull(num);                     
            }
        } catch (PrqaException ex) {
            fail();
        }
    }
    
    @Test 
    public void testGetGetWrongReadings() {
        PRQAComplianceStatus status = new PRQAComplianceStatus(100, 100d, 34.5d);
        boolean caught = false;
        try {
            for (Number num : status.getReadouts(StatusCategory.FileCompliance,StatusCategory.ProjectCompliance,StatusCategory.Messages,StatusCategory.NumberOfFunctions).values()){
                assertNotNull(num);                     
            }
        } catch (PrqaException ex) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    @Test
    public void testQav() {
        QAV qav = new QAV();
    }
    
    @Test
    public void testPrqaCommandBuilder() {
        String codeAll = "-po qav::code=all";
        String projectDatabase ="-db mydb";
        
        assertEquals(codeAll, PRQACommandBuilder.getCodeAll(CodeUploadSetting.AllCode));
        
        assertEquals("", PRQACommandBuilder.getSingle(false));
        assertEquals("-single", PRQACommandBuilder.getSingle(true));
        assertEquals(projectDatabase, PRQACommandBuilder.getProjectDatabase("mydb"));
        
        
    }
    
    @Test
    public void testSopRemoveTrailingSlash() {
        String stringWithSlash = "C:\\slashed\\";
        String stringUnSlashed = "-sop \\\"C:\\slashed\\\"";
        String result = PRQACommandBuilder.getSop(stringWithSlash);
        
        String withOutSlash = "C:\\slashed";
        String result2 = PRQACommandBuilder.getSop(withOutSlash);
        
        assertEquals(stringUnSlashed, result);
        assertEquals(result, result2);
    }
    
    
    /**
     * Testing specific requirements specifications
     */
    @Test
    public void testCommandConstruction() {
        
    }
    
    @Test public void testPrqaContext() {
        int expectedCount = PRQAContext.QARReportType.OPTIONAL_TYPES.size();        
        assertEquals(2, expectedCount);
    }
}
