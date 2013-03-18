/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.File;
import net.praqma.prqa.exceptions.PrqaSetupException;
import net.praqma.prqa.products.QAC;
import net.praqma.prqa.products.QACpp;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.products.QAV;
import net.praqma.prqa.products.QAW;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class ProductTest {
    
    @Test public void testGetQacVersion() throws PrqaSetupException {
        QAC qac = new QAC();
        String version = qac.getProductVersion(null, new File(System.getProperty("java.io.tempdir")), !System.getProperty("os.name").startsWith("Windows"));        
        assertNotNull(version);    
    }
    
    @Test public void testGetQacppVersion() throws PrqaSetupException {
        QACpp qacpp = new QACpp();
        String version = qacpp.getProductVersion(null, new File(System.getProperty("java.io.tempdir")), !System.getProperty("os.name").startsWith("Windows"));        
        assertNotNull(version);        
    }
    
    @Test public void testGetQarVersion() throws PrqaSetupException {
        QAR qar = new QAR("unknown", "unknown", PRQAContext.QARReportType.Compliance);       
        String version = qar.getProductVersion(null, new File(System.getProperty("java.io.tempdir")), !System.getProperty("os.name").startsWith("Windows"));        
        assertNotNull(version);
    }
    
    @Test public void testGetQawVersion() throws PrqaSetupException {
        QAW qaw = new QAW();
        String version = qaw.getProductVersion(null, new File(System.getProperty("java.io.tempdir")), !System.getProperty("os.name").startsWith("Windows"));        
        assertNotNull(version);
    }
    
    @Test public void testQAVVersion() throws PrqaSetupException {
        QAV qav = new QAV();
        String version = qav.getProductVersion(null, new File(System.getProperty("java.io.tempdir")), !System.getProperty("os.name").startsWith("Windows"));        
        assertNotNull(version);
    }
    
}
