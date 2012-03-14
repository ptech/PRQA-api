/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.PRQAStatus.ComplianceCategory;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.parsers.ReportHtmlParser;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QAR;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 *
 * @author Praqma
 */
public class PRQATest extends TestCase {
    private static PRQAComplianceStatusCollection collection = null;
    
    @BeforeClass 
    public static void testCreateMockCollection () {
        collection = new PRQAComplianceStatusCollection();
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
        assertEquals(collection.getMin(ComplianceCategory.Messages),new Integer(1000));
        assertEquals(collection.getMax(ComplianceCategory.Messages),new Integer(20000));        
    }
    
    @Test 
    public void testComplianceStatusOverride() {
        collection.overrideMax(ComplianceCategory.Messages, 100);
        collection.overrideMin(ComplianceCategory.Messages, 0);
        
        assertEquals(collection.getMax(ComplianceCategory.Messages), 100);
        assertEquals(collection.getMin(ComplianceCategory.Messages), 0);
        
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
        List<String> listFileC = parser.parse(f.getPath(), ComplianceReportHtmlParser.fileCompliancePattern);
        List<String> listProjC = parser.parse(f.getPath(), ComplianceReportHtmlParser.projectCompliancePattern);
        List<String> listMsg = parser.parse(f.getPath(), ComplianceReportHtmlParser.totalMessagesPattern);
        List<String> listIconAddress = parser.parse(f.getPath(), ReportHtmlParser.iconLinkPattern);
        System.out.println(f.getPath().toString());
        int replace = parser.replaceIcon(f.getPath(),  "Hello there!");
        System.out.println("Replaced: "+replace);
        
        //Assert Not null.
        
        assertNotNull(listFileC);
        assertNotNull(listProjC);
        assertNotNull(listMsg);
        assertNotNull(listIconAddress);
        //Assert that each list contains EXACTLY 1 element. That is the requirement for the compliance report.
        
        assertEquals(1, listFileC.size());
        assertEquals(1, listProjC.size());
        assertEquals(1, listMsg.size());
        assertEquals(1, listIconAddress.size());
        System.out.println(listIconAddress.get(0));
          
        System.out.println(f.getPath().toString());
        //f.delete();                
    }  
}
