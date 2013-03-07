/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import net.praqma.prqa.products.QAC;
import net.praqma.prqa.products.QACpp;
import net.praqma.prqa.products.QAR;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class ProductTest {
    @Test public void testGracefulVersionFailures() {
        String unknown = "Unknown";
        
        
        QAR qar = new QAR("unknow", "unknown", PRQAContext.QARReportType.Compliance);
        assertEquals(qar.getProductVersion(), unknown);
        
        QAC qac = new QAC();
        assertEquals(qac.getProductVersion(), unknown);
        
        QACpp qacpp = new QACpp();
        assertEquals(qacpp.getProductVersion(), unknown);
        
    }
    
}
