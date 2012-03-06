/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
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
        assertEquals(collection.getMin(PRQAComplianceStatusCollection.ComplianceCategory.Messages),new Integer(1000));
        assertEquals(collection.getMax(PRQAComplianceStatusCollection.ComplianceCategory.Messages),new Integer(20000));        
    }
    
    @Test 
    public void testComplianceStatusOverride() {
        collection.overrideMax(PRQAComplianceStatusCollection.ComplianceCategory.Messages, 100);
        collection.overrideMin(PRQAComplianceStatusCollection.ComplianceCategory.Messages, 0);
        
        assertEquals(collection.getMax(PRQAComplianceStatusCollection.ComplianceCategory.Messages), 100);
        assertEquals(collection.getMin(PRQAComplianceStatusCollection.ComplianceCategory.Messages), 0);
        
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
    public void testParsableCommand() {
        //String testSource = "C:\\Users\\Praqma";
        //String testString2 = "\"C:\\Program Files\\git\\bin\\git.exe\" --version";
        //String testString  = "\"C:\\Program Files (x86)\\PRQA\\QAR-1.2\\bin\\qar.exe\"";// QAC -po qar::report_type=\"Compliance Report\" -list \"C:\\j\\workspace\\PRQA Test\\examples\\examples.prj\" -po qar::output_path=\"C:\\Projects\\PRQA-plugin\\work\\jobs\\PRQA\\workspace\" -cmaf \"C:\\Projects\\PRQA-plugin\\work\\jobs\\PRQA\\workspace\\qar_out\"";
        //System.out.println("TEST: "+testString2   );
        //PRQACommandLineUtility.run(testString2, new File(testSource));
        //assertFalse(false);
    }
}
