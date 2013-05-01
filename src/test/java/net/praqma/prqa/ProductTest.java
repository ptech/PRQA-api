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
        String version = null;
        try {
            QAC qac = new QAC();
            version = qac.getProductVersion(null, new File(System.getProperty("java.io.tmpdir")), !System.getProperty("os.name").startsWith("Windows"));        
            assertNotNull(version);    
        } catch (PrqaSetupException ex) {
            assertNull(version);
        }
    }
    
    
    
    /**
     * Our testbed does not have QACpp installed
     * @throws PrqaSetupException 
     */
    @Test(expected=PrqaSetupException.class) public void testGetQacppVersionWithoutInjection() throws PrqaSetupException {
        QACpp qacpp = new QACpp();
        String version = null;
        try {
            version = qacpp.getProductVersion(null, new File(System.getProperty("java.io.tmpdir")), !System.getProperty("os.name").startsWith("Windows"));
            assertNotNull(version);        
        } catch (PrqaSetupException ex) {
            assertNull(version);
            throw ex;
        }        
    }        
    
    
    @Test public void testGetQarVersion() throws PrqaSetupException {
        String version = null;
        try {
            QAR qar = new QAR("unknown", "unknown", PRQAContext.QARReportType.Compliance);       
            version = qar.getProductVersion(null, new File(System.getProperty("java.io.tmpdir")), !System.getProperty("os.name").startsWith("Windows"));
            assertNotNull(version);
        } catch (PrqaSetupException ex) {
            assertNull(version);
        }        
    }
    
    
   
    @Test public void testGetQawVersion() throws PrqaSetupException {
        String version = null;
        try {
            QAW qaw = new QAW();
            version = qaw.getProductVersion(null, new File(System.getProperty("java.io.tmpdir")), !System.getProperty("os.name").startsWith("Windows"));
            assertNotNull(version);
        } catch (PrqaSetupException ex) {
            assertNull(version);
        }
        
    } 
    
    
    @Test public void testQAVVersion() throws PrqaSetupException {
        String version = null;
        try {
            QAV qav = new QAV();        
            version = qav.getProductVersion(null, new File(System.getProperty("java.io.tmpdir")), !System.getProperty("os.name").startsWith("Windows"));        
            assertNotNull(version);
        } catch (PrqaSetupException ex) {
            assertNull(version);
        }
    }    
}
