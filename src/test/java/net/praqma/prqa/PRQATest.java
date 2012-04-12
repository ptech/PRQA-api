package net.praqma.prqa;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.jenkins.plugin.prqa.PrqaException.PrqaReadingException;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.parsers.QualityReportParser;
import net.praqma.prqa.parsers.SuppressionReportParser;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.status.PRQAComplianceStatus;
import net.praqma.prqa.status.PRQAQualityStatus;
import net.praqma.prqa.status.StatusCategory;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 *
 * @author Praqma
 */
public class PRQATest extends TestCase {
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
        } catch (PrqaException.PrqaReadingException ex) {
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
        } catch (PrqaReadingException ex) {
            fail();
        }
        
    }
    
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
    
    @Test 
    public void testCreateQARCommand() {
        QAR qar = new QAR();
        qar.getBuilder().prependArgument(PRQACommandBuilder.getProduct("qacpp"));
        String qarString = "qar %Q %P+ %L+ "+ PRQACommandBuilder.getReportTypeParameter("Compliance") + " " +
                PRQACommandBuilder.getProjectName() + " " + PRQACommandBuilder.getOutputPathParameter("C:\\Program\\ Files\\") + " " + PRQACommandBuilder.getViewingProgram("dummy");
        qar.getBuilder().appendArgument(PRQACommandBuilder.getMaseq(qarString));
        System.out.println(qar.getBuilder().getCommand());
        assertTrue(true);
        
    }
    
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
    public void testParseQualityReport() throws IOException, PrqaException {
        InputStream is = this.getClass().getResourceAsStream("Quality_Report_LARGE.xhtml");
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
        
        List<String> totalNumFiles = parser.parse(f.getPath(), QualityReportParser.totalNumberOfFilesPattern);
        List<String> linesOfCode = parser.parse(f.getPath(), QualityReportParser.linesOfCodePattern);
        List<String> sourceFiles = parser.parse(f.getPath(), QualityReportParser.numberOfSourceFilesPattern);
        List<String> numFunctions = parser.parse(f.getPath(), QualityReportParser.numberOfFunctionsPattern);
        List<String> numFileMetrics = parser.parse(f.getPath(), QualityReportParser.numberOfFileMetricsPattern);
        List<String> numFunctionMetrics = parser.parse(f.getPath(), QualityReportParser.numberOfFunctionsMetricPattern);
        System.out.println(f.getPath().toString());
        
        assertNotNull(totalNumFiles);
        assertNotNull(linesOfCode);
        assertNotNull(sourceFiles);
        assertNotNull(numFunctions);
        assertNotNull(numFileMetrics);
        assertNotNull(numFunctionMetrics);
       
                
        assertEquals(1, totalNumFiles.size());
        assertEquals(1, linesOfCode.size());
        assertEquals(1, sourceFiles.size());
        assertEquals(1, numFunctions.size());
        assertEquals(1, numFileMetrics.size());
        assertEquals(1, numFunctionMetrics.size());
        
        String res1 = parser.getResult(QualityReportParser.totalNumberOfFilesPattern);
        String res2 = parser.getResult(QualityReportParser.linesOfCodePattern);
        String res3 = parser.getResult(QualityReportParser.numberOfSourceFilesPattern);
        String res4 = parser.getResult(QualityReportParser.numberOfFunctionsPattern);
        
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);
        
        assertNotNull(res1);
        assertNotNull(res2);
        assertNotNull(res3);
        assertNotNull(res4);
        
        System.out.println(f.getPath().toString());
        String fileName = f.getAbsolutePath();
        System.out.println(String.format("Deleted file %s : %s", fileName, f.delete()));
    }
    
    @Test
    public void testParseSupressionReport() throws IOException, PrqaException {
        InputStream is = this.getClass().getResourceAsStream("Suppression_Report.xhtml");
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
       
        SuppressionReportParser parser = new SuppressionReportParser();
        parser.setFullReportPath(f.getPath());
        
        String totalNumFiles = parser.getResult(SuppressionReportParser.numberOfFilesPattern);
        String linesOfCode = parser.getResult(SuppressionReportParser.linesOfCodePattern);
        String numSupMsg = parser.getResult(SuppressionReportParser.numberOfMessagesSuppressedPattern);
        String pctSupMsg = parser.getResult(SuppressionReportParser.percentageOfMsgSuppressedPattern);
        String uniSupMsg = parser.getResult(SuppressionReportParser.uniqueMessagesSuppressedPattern);
        
        
        
        assertNotNull(totalNumFiles);
        assertNotNull(linesOfCode);
        assertNotNull(numSupMsg);
        assertNotNull(pctSupMsg);
        assertNotNull(uniSupMsg);
       
        int restNumFiles = Integer.parseInt(totalNumFiles);
        int resLinesCode = Integer.parseInt(linesOfCode);
        int resNumSupMsg = Integer.parseInt(numSupMsg);
        double resPctSupMsg = Double.parseDouble(pctSupMsg);
        int resUniSupMsg = Integer.parseInt(uniSupMsg);
        
        assertTrue(restNumFiles >= 0);
        assertTrue(resLinesCode >= 0);
        assertTrue(resNumSupMsg >= 0);
        assertTrue(resPctSupMsg >= 0);
        assertTrue(resUniSupMsg >= 0);
        
        System.out.println(f.getPath().toString());
        String fileName = f.getAbsolutePath();
        System.out.println(String.format("Deleted file %s : %s", fileName, f.delete()));
    }
    
        
    @Test
    public void testParserMethods() {
        ComplianceReportHtmlParser parser = new ComplianceReportHtmlParser();
    }
    
    @Test
    public void testSetReading() {
        PRQAQualityStatus stat = new PRQAQualityStatus();
        try {
            stat.setReadout(StatusCategory.Messages, new Integer(40));
        } catch (PrqaReadingException ex) {
            assertTrue("Test succeeded, quality status does not have a field with label Messages", true);
        }        
    }
    
    @Test
    public void testGetReadings() {
        PRQAComplianceStatus status = new PRQAComplianceStatus(100, 100d, 34.5d);
        try {
            for (Number num : status.getReadouts(StatusCategory.FileCompliance,StatusCategory.ProjectCompliance,StatusCategory.Messages).values()){
                assertNotNull(num);                     
            }
        } catch (PrqaReadingException ex) {
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
        } catch (PrqaReadingException ex) {
            caught = true;
        }
        assertTrue(caught);
    }
    
//    @Test
//    public void testParseVeryLargeReport() {
//        try {
//            File f = copyResourceToTestFile(this.getClass(), "Quality_Report_LARGE.xhtml");
//            QualityReportParser parser = new QualityReportParser();
//            System.out.println(f.getPath());
//            parser.setFullReportPath(f.getPath());
//            
//            String res1 = parser.getResult(QualityReportParser.totalNumberOfFilesPattern);
//            String res2 = parser.getResult(QualityReportParser.linesOfCodePattern);
//            String res3 = parser.getResult(QualityReportParser.numberOfSourceFilesPattern);
//            String res4 = parser.getResult(QualityReportParser.numberOfFunctionsPattern);
//
//            assertNotNull(res1);
//            assertNotNull(res2);
//            assertNotNull(res3);
//            assertNotNull(res4);
//
//        } catch (PrqaException ex) {
//            fail();
//        } catch (IOException ex) {
//            fail();
//        }
//    }
}
