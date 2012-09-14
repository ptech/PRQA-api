/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import net.praqma.prqa.products.PRQACommandBuilder;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class PRQACommandBuilderTest {
    public String emptyRes = "";
    
    @Test public void testDataFlowAnalysis() {
        String res = "-ed+";
        
        
        assertEquals(res,PRQACommandBuilder.getDataFlowAnanlysisParameter(true));
        assertEquals(emptyRes, PRQACommandBuilder.getDataFlowAnanlysisParameter(false));
        
    }
    
    @Test public void testEnableDependencyMode() {
        String res = "-mode depend";
        
        assertEquals(res,PRQACommandBuilder.getDependencyModeParameter(true));
        assertEquals(emptyRes, PRQACommandBuilder.getDataFlowAnanlysisParameter(false));
        
    }
    
    @Test public void testGetProdParameter() {
        String res = "-prod %Q ";
        String resSingle = PRQACommandBuilder.getSingle(true);
        
        assertEquals(res+resSingle+" ",PRQACommandBuilder.getProd(true));
        assertEquals(res, PRQACommandBuilder.getProd(false));
        
    }
    
    @Test public void getNumberOfThreads() {
        String res = String.format("-po qav::thread=%s", Runtime.getRuntime().availableProcessors());
        int numThread = Runtime.getRuntime().availableProcessors();
        
        assertEquals(res, PRQACommandBuilder.getNumberOfThreads(numThread));
        
    }
    
}
