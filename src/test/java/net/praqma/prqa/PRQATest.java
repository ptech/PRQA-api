/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Praqma
 */
public class PRQATest extends TestCase {
    
     
    @Test public void testComplianceStatusCollection() {
        PRQAComplianceStatusCollection collection = new PRQAComplianceStatusCollection();
        
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
        
        assertEquals(collection.getMin(PRQAComplianceStatusCollection.ComplianceCategory.Messages),new Integer(1000));
        assertEquals(collection.getMax(PRQAComplianceStatusCollection.ComplianceCategory.Messages),new Integer(20000));        
    }
    
    @Test public void testComplianceStatusOverride() {
        assertTrue(true);
    }
}
